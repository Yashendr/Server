import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import javax.sound.midi.Receiver;

import tut1.Message;  
public class server {
    static ServerSocket server_socket;
    static ObjectOutputStream output_stream;
    static ObjectInputStream input_stream;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
      
        server_socket = new ServerSocket(6666);
        Socket socket = server_socket.accept(); ;
         
         socket.close();
         server_socket.close();
    

        



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