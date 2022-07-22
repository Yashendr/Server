import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

import javax.sound.midi.Receiver;

import tut1.Message;  
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

        
        while(true){
            SocketChannel client_channel =
            server_channel.accept();
            list.add(client_channel);
            System.out.println("Connected user\t" +i);
            i++;
        
            //do something with socketChannel...
        }

        



    }

    public static void recieve_messages() throws ClassNotFoundException, IOException{
        Message mess = null; 
        while (true) {
            Thread thread_new = new Thread();
            thread_new.start();
            Socket socket = server_socket.accept();
            output_stream = new ObjectOutputStream(socket.getOutputStream());
            input_stream = new ObjectInputStream(socket.getInputStream());

            while (true) {
                mess=  (Message) input_stream.readObject();

                if(!mess.equals(null))
                System.out.println(mess.get_username() +": " +mess.get_message());
                System.out.println("here");
            }
        }


    }




}