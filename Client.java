import javax.swing.*;

import tut1.Message;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Client {
   static Socket socket = null;
   public static String username = "absoul";
   public static void main(String args[]) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException  {
      socket = new Socket("localhost",6666);
      Thread thread_send = new Thread();
      //Thread thread_rec = new Thread();
      thread_send.start();
      //thread_rec.start();
   
            send_message();
         

      /*    if(Thread.currentThread().equals( thread_rec)) {
            rec_message();
         } */
         //thread_rec.join();
         socket.close();

    }

    public static void send_message() throws IOException {
      ObjectOutputStream output_stream = new ObjectOutputStream(socket.getOutputStream());
      Scanner input = new Scanner(System.in);
      String mess_string ="";

      while(!mess_string.contains("stop")) {

          if(input.hasNext()){
            mess_string = input.next();
            Message mesObj = new Message(username,mess_string);
            output_stream.writeObject(mesObj);
         
         }
      }

    }
    public static void rec_message() throws IOException, ClassNotFoundException {
      while(true) {
      ObjectInputStream input_stream = new ObjectInputStream(socket.getInputStream());
      Message mes = (Message) input_stream.readObject(); 
     
      System.out.println(mes.get_username()+" :" +mes.get_message());
      }
    }


    public void set_username(){
      username = JOptionPane.showInputDialog("Enter username");
    }


}
