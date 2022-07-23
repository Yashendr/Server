import java.nio.channels.SelectionKey;
import java.util.HashMap;

public class clients_connected {
    public HashMap<String, SelectionKey> clients = new HashMap<>();
    public  clients_connected () {

    }

    public synchronized void add(String username, SelectionKey key) {
        clients.put(username,key);
    }

    public synchronized void remove(String username) {
        clients.remove(username);
    }
}
