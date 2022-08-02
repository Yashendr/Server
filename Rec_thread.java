import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.locks.ReentrantLock;

import javax.sound.midi.Receiver;

public class Rec_thread implements Runnable  {
    static ReentrantLock lock;  
   private SocketChannel sock_chan;
   Gui client_side;
    public Rec_thread(SocketChannel sock_chan, ReentrantLock lock,    Gui client_side) {
        this.sock_chan = sock_chan;
        this.lock = lock;
        this.client_side =  client_side;
    }
    @Override
    public void run(){
        while(true){
            
            
            try {
                String mess = Send_Rec.receive(sock_chan);
                if(mess.equals("")){}
                else{
                        System.out.println(mess);
                        client_side.add_messages(mess);
                }
            } catch (IOException e) {
                System.out.println("hii");
            }
        }
    }
}
