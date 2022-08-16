import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class base_rec {
    public static void main(String[] args) throws InterruptedException {

        try{
            ServerSocketChannel socketChan = ServerSocketChannel.open();
            InetSocketAddress address = new InetSocketAddress("25.68.194.114", 2202);
            socketChan.bind(address);
            SocketChannel recChan = socketChan.accept();
            int i = Integer.parseInt(Send_Rec.receive(recChan).trim());
            String fname = Send_Rec.receive(recChan);
            System.out.println(i +""+fname);
            FileReciever r = new FileReciever();
            r.fname = fname;
            r.amtBytes = i;
            Thread p = new Thread(r);
            p.start();
            int keygone = 0;
            System.out.print(Send_Rec.receive(recChan) + ": " + r.amtBytes +"\n");
            while (true) {
                p.interrupt();
                Thread.sleep(1000);
                ArrayList <Integer> k = r.toResend;
                String l = "";

                if(k.size()  == 0) {
                    l = "can";
                    r.stop = 2;
                }
               

                for (int n = 0 ; n < k.size(); n ++) {
                    System.out.println("entered");
                    System.out.println("aaaa " + k.get(n));
                    System.out.println(k.size());   
                    l = k.get(n)+"\n"+l;
                 //   System.out.println(k.get(n)+"nums");
                }
                System.out.println("left");
                keygone =    Send_Rec.send(l,recChan);
                
                if(k.size() == 0){
                    r.stop = 2;
                    System.out.println("here1");
                    break;
                } else {
                    r.stop = 1;

                }
                if(keygone == 0){
                    r.stop = 2;
                    System.out.println("2");
                    break;
                }
                //p.notify();
                Send_Rec.receive(recChan);
                System.out.println("reached");


            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
