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
      if (args[0].contains("1")){
        username = "absoul";
      } else if (args[0].contains("0")) {
        username = "maker";
      } else if (args[0].contains("2")) {
        username = "foxis";
      } 
      else {
        set_username();
        System.out.println(username.trim().length());

      }  


      try {
        socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",9999));

      } catch (Exception e) {
          socketChannel.close();
          System.out.println("Server does not exist.");
          System.exit(0);

      }
      
      //socketChannel.connect(new InetSocketAddress("25.68.194.114",4200));
     // server_channel.socket().bind(new InetSocketAddress("localhost",9999));
     Send_Rec.send(username, socketChannel);
   
      Thread.sleep(100);
      String servermes = Send_Rec.receive(socketChannel);
      if(servermes.contains("server : duplicate username retry")){
        socketChannel.close();
        System.out.println("Username taken choose another.");
        System.exit(0);
      }
      


      String send_mes = "";
      disconector disc = new disconector(socketChannel, username);
      Thread dis = new Thread(disc);
      Runtime.getRuntime().addShutdownHook(dis);
      Gui client_side = new Gui(socketChannel,username,servermes);
      client_side.setVisible(true);
      Rec_thread recer = new Rec_thread(socketChannel,lock,client_side);
      Thread Thread_recer = new Thread(recer);
      Thread_recer.start();
  
        //   while (true){
          
          
        //   Scanner util = new Scanner(System.in);
        //   String next = util.nextLine();
        //   Send_Rec.send(next, socketChannel);
        //  } 
      
    
    
    }



    public static void set_username(){
      username = JOptionPane.showInputDialog("Enter username");
    }


}
