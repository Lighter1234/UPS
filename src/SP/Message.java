package SP;

public class Message {

    String message;

    int bytes;

    public Message(String message, int bytes) {
        this.message = message;
        this.bytes = bytes;
    }

    public String getMessage() {
        return message;
    }

    public int getBytes() {
        return bytes;
    }
}
