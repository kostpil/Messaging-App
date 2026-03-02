import java.io.Serializable;
public class Message implements Serializable {
    public boolean isRead=false;
    public String sender;
    public String receiver;
    public String body;

    public Message(String sender,String receiver,String body )
    {
        this.sender=sender;
        this.receiver=receiver;
        this.body=body;
    }

}
