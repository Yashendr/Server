

import java.io.Serializable;

public class Message implements Serializable  {
    private String message  = "";
    private String username = "";
    private String dst_whisper = "";
    
    public Message (String username,String message) {
        this.username = username;
        this.message = message;

    } 


    public Message (String username,String message,String dst_whisper) {
        this.username = username;
        this.message = message;
        this.dst_whisper = dst_whisper;

    } 

    public String get_message() {
        return message;
    }


    public String get_username() {
        return username;
    }
}
