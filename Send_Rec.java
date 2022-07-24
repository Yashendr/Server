import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Send_Rec {
    public static Charset charset = Charset.forName("UTF-8");
    
    public static void send(String sent, SocketChannel sock_chan)  {
        ByteBuffer send_data = ByteBuffer.wrap(sent.getBytes(charset));
        try {
            sock_chan.write(send_data);
            System.out.println("here1");
        } catch (IOException e) {
            // TODO Auto-generated catch block
           System.out.println("key gone");
        }
    }

    public static String receive(SocketChannel sock_chan) throws IOException  {
        ByteBuffer num =  ByteBuffer.allocate(1000);
        String data = "";
        if(sock_chan.read(num) == -1){
            return "";
        }
 
            data += charset.decode(num.position(0)).toString();
        
        
        return data;
    }

    public static String receive(SocketChannel sock_chan,int i) throws IOException  {
        ByteBuffer num =  ByteBuffer.allocate(1000);
        String data = "";
        while(sock_chan.read(num) == -1){

        }
 
            data += charset.decode(num.position(0)).toString();
        
        
        return data;
    }
}
