import java.io.IOException;
import java.nio.*;
import java.util.Iterator;
import java.util.Set;
import java.nio.channels.*;

public class channel_selector implements Runnable {
    private boolean active = true;
    private Selector selector;
    private SelectionKey server_key;
    private ServerSocketChannel server_channel;
    public channel_selector(Selector selector, SelectionKey server_key,
    ServerSocketChannel server_channel) throws IOException {
        this.selector = selector;
        this.server_key = server_key;
        this.server_channel = server_channel;
    }
    
    @Override
    public void run() {
        while (active) {
            int ready = 0;

            try {
                ready = selector.selectNow();

                if(ready == 0) continue;

                Set<SelectionKey> keys = selector.selectedKeys();;
                Iterator<SelectionKey> key_checker =  keys.iterator();
                            
                while(key_checker.hasNext()) {
                    SelectionKey key = key_checker.next();
    
                    if(!key.isValid()) continue;
                    
                    if(key == server_key){
                        SocketChannel new_connection = server_channel.accept();
                        new_connection.configureBlocking(false);
                        int jobs = SelectionKey.OP_READ | SelectionKey.OP_WRITE;
                        SelectionKey new_key = new_connection.register(selector, jobs);
                    }
    
                    key_checker.remove();
                }

            } catch (Exception IOException) {
            
            }

           

        }
        
    }
    
   

   

    
    
}
