import java.nio.channels.SelectionKey;
import java.util.HashMap;

public class clients_connected {
    private HashMap<String, SelectionKey> clients = new HashMap<>();
    private clients_connected () {

    }

    public synchronized void add(String username, SelectionKey key) {
        clients.put(username,key);
    }

    public synchronized void remove(String username) {
        clients.remove(username);
    }
}