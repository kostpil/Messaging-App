import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Account implements Serializable{
    public String username;
    public int authToken;
    public List<Message> messageBox=new ArrayList<>();

    public Account (String username,int authToken)
    {
        this.username=username;
        this.authToken=authToken;
     }
}
