import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

import javax.sound.midi.Receiver;

public class server {
    static ServerSocket server_socket;
    static ObjectOutputStream output_stream;
    static ObjectInputStream input_stream;
    public static ArrayList<SocketChannel> list;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
      
        ServerSocketChannel server_channel = ServerSocketChannel.open();
        server_channel.configureBlocking(false);
        server_channel.socket().bind(new InetSocketAddress("localhost",9999));
        list = new ArrayList<SocketChannel>();
        int i =0;
        String mes = "";
        
        while(true){
            SocketChannel client_channel = server_channel.accept() ;
            while( client_channel!= null){
                recieve_messages( client_channel);
            
              //  System.out.println("Connected user\t" +i +client_channel.toString());
          
                i++;
            }
            //do something with socketChannel...
        }

        



    }

    public static void recieve_messages(SocketChannel client_channel) throws ClassNotFoundException, IOException{
        Message mess = null; 
       System.out.println(Send_Rec.receive(client_channel) +"here"); 


    }




}