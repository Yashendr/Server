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
    }
    
    @Override
    public void run() {
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
                            String username = Send_Rec.receive(new_connection);
                            System.out.println(new_key + " " + server_key);
                            clients.add(username, new_key);
                        }
                    }

                    else if(key.isReadable() && key != server_key) {
                      String message = Send_Rec.receive((SocketChannel)key.channel());

                      if(message.equals("")){
                        key_checker.remove();

                        continue;
                      }
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
    
                    key_checker.remove();
                }

            } catch (Exception IOException) {
                System.out.println(key.equals(server_key));

            }



        }
        
    }
    
   

   

    
    
}
