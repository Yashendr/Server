import java.nio.channels.SelectionKey;
import java.util.HashMap;

public class clients_connected {
    private HashMap<String, String> clients = new HashMap<>();
    public clients_connected () {

    }

    public synchronized void add(String username, SelectionKey key) {
        clients.put(key.toString(),username);
    }

    public synchronized void remove(String username) {
        clients.remove(username);
    }
    public synchronized String username(SelectionKey key) {
        return clients.get(key.toString());

    }
}
