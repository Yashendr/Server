import java.util.*;
public class usernames {

    private final List<String> usernames = new ArrayList<>();
    public usernames () {

    }

    public synchronized int get_size() {
        return usernames.size();
    }

    public synchronized void add(String username) {
        usernames.add(username);
    }

    public synchronized String get(int i) {
        return usernames.get(i);
    }

    public synchronized void remove(String username){
        usernames.remove(username);
    }

    public synchronized boolean is_duplicate(String username){
        if(usernames.contains(username)){
            return true;
        }
        return false;
    }
}