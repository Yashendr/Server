import java.nio.channels.SocketChannel;

public class client_info {
    private SocketChannel channel;
    private String username;
    public client_info(SocketChannel channel, String username) {

        this.channel = channel;
        this.username = username;
    }

    public synchronized String get_username() {
        return username;
    }

    public synchronized SocketChannel get_channel() {
        return channel;
    }
}
