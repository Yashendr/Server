import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Send_Rec {
    public static Charset charset = Charset.forName("UTF-8");
    
    public static void send(String sent, SocketChannel sock_chan) throws IOException   {
        ByteBuffer send_data = ByteBuffer.wrap(sent.getBytes(charset));
        sock_chan.write(send_data);
    }

    public static String receive(SocketChannel sock_chan) throws IOException  {
        ByteBuffer num =  ByteBuffer.allocate(1000);
        String data = "";
        while(sock_chan.read(num) == -1){
       
        }
 
            data += charset.decode(num.position(0)).toString();
        
        
        return data;
    }
}
