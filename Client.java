import javax.swing.*;


import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Client {
   static  SocketChannel socketChannel = null;
   public static String username = "absoul";
   public static void main(String args[]) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException  {
      socketChannel = SocketChannel.open();
      socketChannel.connect(new InetSocketAddress("localhost",9999));
      String send_mes = "";
      while (true){
        Scanner send  =  new Scanner(System.in);
        send_mes = send.next();
        Send_Rec.send(send_mes, socketChannel);
        
      }
    
    
    }



    public void set_username(){
      username = JOptionPane.showInputDialog("Enter username");
    }


}
