import java.nio.channels.SocketChannel;

public class disconector implements Runnable {
    
    private SocketChannel soc_chan;
    private String username;
    public disconector(SocketChannel soc_chan, String username){
        this.soc_chan = soc_chan;
        this.username = username;
    }
    public void run() {
        Send_Rec.send("!!Disconnect" + " " + username,soc_chan);
        try {
            soc_chan.close();
        } catch (Exception IOException) {
        }
        
    }
}
