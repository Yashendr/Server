import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.locks.ReentrantLock;

import javax.sound.midi.Receiver;

public class Rec_thread implements Runnable  {
    static ReentrantLock lock;  
   private SocketChannel sock_chan;
    public Rec_thread(SocketChannel sock_chan, ReentrantLock lock) {
        this.sock_chan = sock_chan;
        this.lock = lock;
    }
    @Override
    public void run(){
        while(true){
            System.out.println("her1e");

            try {
                lock.lock();
                System.out.println(Send_Rec.receive(sock_chan));
                lock.unlock();
            } catch (IOException e) {
                System.out.println("hii");
            }
        }
    }
}
