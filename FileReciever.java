import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.imageio.ImageIO;

/**
 * FileReciever
 */
public class FileReciever extends Thread{
String message = "jhgas";
File file; 
volatile int amtBytes = 0;
volatile ArrayList<Integer> toResend;
volatile String fname = "";
volatile int stop = 0;
private final Lock lock = new ReentrantLock();

    public void run(){
        System.out.println("Thread Started");
        boolean allSent = false;
        boolean firstBatchSent = false;
        boolean batchSent = false;
        try {
            InetSocketAddress address = new InetSocketAddress("25.68.194.114", 4202);
            DatagramSocket socket = new DatagramSocket(address);

           // TCPHandelerRecieve k = new TCPHandelerRecieve(tcpSocket);
           // Thread l = new Thread(k);
           // l.start();

    
            create_file(fname);

            receive(socket);
            System.out.println("aj  ");
            
            //Write to file
                        
        } catch (Exception e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }

    public  void receive ( DatagramSocket socket) throws InterruptedException{
        ArrayList<byte[]> toWrite = new ArrayList<byte[]>(amtBytes);
        for (int i = 0; i < amtBytes; i++) {
            toWrite.add(null);
        }

        toResend = new ArrayList<Integer>();  

        
        boolean allSent = false;
        int seqNum = 0;
        boolean firstBatchSent = false;
        boolean batchSent = false;
        int setnum = 0;
        int upper = 100;
        while(stop != 2) {
            byte [] message = new byte[1024];
            DatagramPacket recPack = new DatagramPacket(message, message.length);
           // System.out.println("begin");
            try {
                socket.setSoTimeout(1000);
                socket.receive(recPack);
            } catch (Exception e) {
                if(this.interrupted()){
                    
                      for ( ;setnum < amtBytes; setnum++) {
                            if(toWrite.get(setnum) == null) {
                                toResend.add(setnum);
             //                   System.out.println("Missing :"+ setnum);
                            }
                      }
                      
                      if (amtBytes < setnum+100) {
                            setnum = amtBytes;
                      }else {
                        setnum = setnum+100;
                      }
               //       System.out.println("here");

                        while (stop == 0 ) {
                        
                        //Thread.sleep(1);
                        try {
                            Thread.sleep(100); //The sleep I changed from 3000

                        } catch (Exception a) {
                            System.out.println(toResend.size());
                            if(toResend.size() == 0) {
                                stop = 2;
                            } else {
                                stop = 1;
                            }
                        }
                        }
                        System.out.println(stop);
//                        lock.unlock();
                
                    Thread.interrupted();
                     if (stop == 2) {
                         break;
                     }
                
                }
            }
            message = recPack.getData();
            byte [] fileBytes = new byte[recPack.getLength()-4];
            System.arraycopy(message, 4, fileBytes, 0, recPack.getLength()-4);
            int sendNum = ((message[0]) + ((message[1]*100))
            + ((message[2]*10000) + ((message[3]*1000000))));
            
            //System.out.println(new String(fileBytes));
            
            if(sendNum == 0) {
                continue;
            } 
            if (sendNum == -1){
                sendNum = 0;
            }

            try {
                toWrite.set(sendNum ,fileBytes );
                //System.out.println(sendNum +"killme");
            } catch ( IndexOutOfBoundsException e ) {
                toWrite.add(sendNum, fileBytes);
                //System.out.println("here 4424" + sendNum);

    
            }

            //TODO: listen for first batch sent and batch sent
            if (sendNum != seqNum) {
                //System.out.println("here" +sendNum +" " +seqNum);
                if (seqNum <amtBytes ){
                    if(toWrite.get(seqNum) == null ) {
                        toResend.add(seqNum);
                  //   System.out.println("here \n\n\n\n\n\n\n\n\n\n\n\n\n aha " +seqNum);
                    }
                }
                 if (toResend.contains(sendNum)) {
                    toResend.remove(toResend.indexOf(sendNum));
                    if(toResend.size() == 0) {
                        allSent = true;
                    }
                }
                    
                seqNum++;
            } 
  
            if(batchSent) {
                //Send toResend back to fileSender
            }
            //TODO: if batch sent
        }
        try {
            assemblefile (toWrite );
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
    }

    public void create_file(String filename) throws IOException{
        file = new File(filename.trim());
        file.createNewFile();
        System.out.println("file :"+filename );
    }

    public byte [] dataSetter (DatagramPacket data) {
        byte []   message = data.getData();
        return null;
    } 




    public void assemblefile (ArrayList<byte[]> toWrite ) throws IOException {
        int i = 0;
        String rr = new String(toWrite.get(0));
        //System.out.println(rr);
        //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
       try {
        if (file.exists()) {
            FileOutputStream fos = new FileOutputStream(file);
            Iterator<byte[]> k = toWrite.iterator();
                        
            while(k.hasNext()) {
                byte [] no = k.next();
                    fos.write(no);
                    i++;  
            //        System.out.println("\n"+i +" " + new String(no));       
                fos.flush();
                
            }
            fos.close();
    
        } else {
        }
       } catch (Exception e) {
            System.out.println("count : oopsie" + i);
            System.exit(0);
       }

    }
     

    
}