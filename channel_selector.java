import java.io.IOException;
import java.nio.*;
import java.util.Iterator;
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
        String mes_test = "";
        while (active) {
            int ready = 0;
            SelectionKey key = null;
            try {
                ready = selector.selectNow();
               // System.out.println(ready);


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
                        SocketChannel new_connection = server_channel.accept();
                        if(new_connection != null){
                            new_connection.configureBlocking(false);
                            int jobs = SelectionKey.OP_READ | SelectionKey.OP_WRITE;
                            SelectionKey new_key = new_connection.register(selector, jobs);
                            Thread.sleep(10);
                            String username = Send_Rec.receive(new_connection,1).trim();
                            
                            clients.add(username, new_key);
                        }
                    }

                    else if(key.isReadable() && key != server_key) {
                      String message = Send_Rec.receive((SocketChannel)key.channel());
                      if(message.equals("")){
                        key_checker.remove();

                        continue;
                      }
                      if (message.trim().charAt(0) == '$')  {
                            String user = "";
                            int i = 1; 
                            String Send_user = message.substring(1,message.indexOf('@')).trim();

                            try {
                                if(!clients.key(Send_user).equals(key)) {
                                    key_checker.remove();
                                    continue;
                                }
                            } catch (Exception e) {
                                key_checker.remove();
                                continue;
                            }
     


                            SocketChannel rec_chan = null;
                            for ( i =message.indexOf('@')+1 ;message.charAt(i) != ' ';i++){
                                    user += message.charAt(i);
                            }
                            rec_chan = (SocketChannel)clients.key(user).channel();
                           
                                if(!rec_chan.equals(null) && !Send_user.equals(user)) {
                                    Send_Rec.send("Private message "+ Send_user +": "+message.substring(i), (rec_chan));
                                }
                      } else {
                      //System.out.println("GET");
                     // Set<SelectionKey> keyall = (selector.keys());
                        Iterator<SelectionKey> allkey_checker =  keyall.iterator();
                        
                        while(allkey_checker.hasNext()) {
                            SelectionKey my_key = allkey_checker.next();
                        
                            if (!my_key.equals(key) && my_key != server_key){
                                Send_Rec.send(message,(SocketChannel)my_key.channel());
            
                            }else {
                                System.out.println("here");
                            }
                      }
                  }
                    } 
    
                    key_checker.remove();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }



        }
        
    }
    
   

   

    
    
}
