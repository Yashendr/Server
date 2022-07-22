import java.io.IOException;
import java.nio.*;
import java.util.Iterator;
import java.util.Set;
import java.nio.channels.*;

public class channel_selector implements Runnable {
    private boolean active = true;
    private Selector selector;
    public channel_selector() throws IOException{
        selector = Selector.open();
    }
    
    @Override
    public void run() {
        while (active) {
            int ready = 0;

            try {
                ready = selector.selectNow();
            } catch (Exception e) {
            
            }

            if(ready == 0) continue;

            Set<SelectionKey> keys = selector.selectedKeys();;
            Iterator<SelectionKey> key_checker =  keys.iterator();
                        
            while(key_checker.hasNext()){
                SelectionKey key = key_checker.next();

                if(key.isReadable()){

                } else if(key.isWritable()){

                }
            }

        }
        
    }
    
   

   

    
    
}
