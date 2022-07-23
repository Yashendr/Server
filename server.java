import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;  

import javax.sound.midi.Receiver;

public class server {
    static ServerSocket server_socket;
    static ObjectOutputStream output_stream;
    static ObjectInputStream input_stream;
    public static ArrayList<SocketChannel> list;
    static Selector selector;
    static SelectionKey server_key;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        


        ServerSocketChannel server_channel = ServerSocketChannel.open();
        server_channel.configureBlocking(false);
        server_channel.socket().bind(new InetSocketAddress("localhost",9999));
        server_key = server_channel.register(selector = Selector.open(), SelectionKey.OP_ACCEPT);
        clients_connected clients = new clients_connected();
        channel_selector seltor = new channel_selector (selector,server_key,server_channel,clients);
        Thread thread_1 = new Thread(seltor);
        thread_1.start();
        while(true) {
            Set<SelectionKey> keys = lock(selector.selectedKeys());
        }
    
    
            //do something with socketChannel...
        }

        



    

    public static void recieve_messages(SocketChannel client_channel) throws ClassNotFoundException, IOException{
        Message mess = null; 
       System.out.println(Send_Rec.receive(client_channel) +"here"); 


    }




}