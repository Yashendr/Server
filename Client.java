import javax.swing.*;


import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Client {
   static  SocketChannel socketChannel = null;
   public static String username = "absoul";
   static ReentrantLock lock;  
   public static void main(String args[]) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException  {
      socketChannel = SocketChannel.open();
      socketChannel.connect(new InetSocketAddress("localhost",9999));
      if (args[0].contains("1")){
        Send_Rec.send("absoul", socketChannel);

      } else {
        Send_Rec.send("ah", socketChannel);

      }
      String send_mes = "";
      Rec_thread recer = new Rec_thread(socketChannel,lock);
      Thread Thread_recer = new Thread(recer);
      Thread_recer.start();
  
          while (true){
         
          Scanner util = new Scanner(System.in);
          String next = util.nextLine();
          Send_Rec.send(next, socketChannel);
         } 
      
    
    
    }



    public void set_username(){
      username = JOptionPane.showInputDialog("Enter username");
    }


}
