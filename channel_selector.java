import java.io.IOException;
import java.nio.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.nio.channels.*;

public class channel_selector implements Runnable {
    private boolean active = true;
    private Selector selector;
    private SelectionKey server_key;
    private ServerSocketChannel server_channel;
    private clients_connected clients;
    static ReentrantLock lock;
    private boolean server_acceptor = true;  
    public channel_selector(Selector selector, SelectionKey server_key,
    ServerSocketChannel server_channel, clients_connected clients, ReentrantLock lock  ) throws IOException {
        this.selector = selector;
        this.server_key = server_key;
        this.server_channel = server_channel;
        this.lock = lock;
        this.clients = clients;
    }
    
    @Override
    public void run() {
        while (active) {
            int ready = 0;
            SelectionKey key = null;
            try {
                ready = selector.selectNow();
                if(ready == 0) continue;
               
                Set<SelectionKey> keys = selector.selectedKeys();;
                Iterator<SelectionKey> key_checker =  keys.iterator();

                while(key_checker.hasNext()) {
                    Set<SelectionKey> keyall = (selector.keys());
                    key = key_checker.next();
                    if(!key.isValid()) {   
                        
                         continue;
               
                     }
                    if(key == server_key){
                        add_user ();
                    }

                    else if(key.isReadable() && key != server_key) {
                        send_message ( key_checker,  key,  keyall );
                        continue;
                    } 
    
                    key_checker.remove();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }



        }
        
    }
    
   public void add_user (){
            SocketChannel new_connection;
            try {
                new_connection = server_channel.accept();
                if(new_connection != null){
                    new_connection.configureBlocking(false);
                    int jobs = SelectionKey.OP_READ | SelectionKey.OP_WRITE;
                    SelectionKey new_key = new_connection.register(selector, jobs);
                    Thread.sleep(10);
                    String username = Send_Rec.receive(new_connection,1).trim();
                    try {
                        if (clients.key(username) != null) {
                            Send_Rec.send("server : duplicate username retry" ,(SocketChannel)new_key.channel());
                            new_key.cancel();
                            return;
                        } else {
                            clients.add(username, new_key);
                           
                            System.out.println(username+" joined");
                            String usernames = "";
                            for(Map.Entry entry: clients.clients.entrySet()){
                               
                                usernames += entry.getKey() +"\n";
         
                                
                            }
                            Send_Rec.send(usernames ,(SocketChannel)new_key.channel());

                        }
                    } catch (Exception e) {
                        
                    }
                    
                       
                    
                    
                    Set<SelectionKey> keyall = (selector.keys());
                    Iterator<SelectionKey> allkey_checker =  keyall.iterator();
                    while(allkey_checker.hasNext()) {
                        SelectionKey my_key = allkey_checker.next();
                        if (!my_key.equals(new_key) && my_key != server_key)  {
                            Send_Rec.send("server : " + username +" has joined.",(SocketChannel)my_key.channel());

                        } else {
                            System.out.println("here");
                            }   
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
                        



   }




   public void send_message ( Iterator<SelectionKey> key_checker, SelectionKey key, Set<SelectionKey> keyall ) throws IOException {
        String message = Send_Rec.receive((SocketChannel)key.channel());
        if(message.equals("")){
            key_checker.remove();
            return;
        }

        if (message.contains("!!Disconnect")){
            String user = message.substring(13).trim();
            System.out.println("bruh");
            clients.remove(user);
            
           
            Iterator<SelectionKey> allkey_checker =  keyall.iterator();
            while(allkey_checker.hasNext()) {
                SelectionKey my_key = allkey_checker.next();
                if (!my_key.equals(key) && my_key != server_key)  {
                    Send_Rec.send("server : " + user +" has left.",(SocketChannel)my_key.channel());

                 } else {
                    System.out.println("here");
                 }
            }
            
        if (key != null){
            key.cancel();
        }
        key_checker.remove();
        return;

        
        }  else if (message.trim().length() == 0) {
            return;
        }  else if (message.trim().charAt(0) == '$')  {
            send_message_private ( message, key,key_checker);
            return;
        }  else {
            int i = 0;
            String user = "";
           
            Iterator<SelectionKey> allkey_checker =  keyall.iterator();
            for ( i =message.indexOf('&')+1 ;message.charAt(i) != ' ';i++){
                user += message.charAt(i);
            }
            while(allkey_checker.hasNext()) {
                SelectionKey my_key = allkey_checker.next();


            
                if ( my_key != server_key)  {
                    Send_Rec.send(user+ ": "+ message.substring(i),(SocketChannel)my_key.channel());

                 } else {
                    System.out.println("here");
                 }
        }
        key_checker.remove();
      }
  }


    private void send_message_private (String message,SelectionKey key,Iterator<SelectionKey> key_checker) {
        String user = "";
        int i = 1; 
        String Send_user = message.substring(1,message.indexOf('@')).trim();

        try {
            if(!clients.key(Send_user).equals(key)) {
                key_checker.remove();
                return;
            }
        } catch (Exception e) {
            key_checker.remove();
            return;
        }

        SocketChannel rec_chan = null;
        for ( i =message.indexOf('@')+1 ;message.charAt(i) != ' ';i++){
                user += message.charAt(i);
        }
     
        try {
            rec_chan = (SocketChannel)clients.key(user).channel();
            
            if(!rec_chan.equals(null) && !Send_user.equals(user)) {
                Send_Rec.send("Private message "+ Send_user +": "+message.substring(i), (rec_chan));
                Send_Rec.send("Private message "+ Send_user +": "+message.substring(i), (SocketChannel)clients.key(Send_user).channel());
            }
        } catch (Exception e) {
            Send_Rec.send("User does not exist",(SocketChannel)clients.key(Send_user).channel());
        }
        


    }
    
    
}
