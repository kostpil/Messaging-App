import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface MessagingInterface extends Remote {
    String createAccount(String username) throws RemoteException;
    List<String> showAccounts() throws RemoteException;
    String sendMessage(int authToken,String recipient, String message) throws RemoteException;
    List<String> showInbox(int authToken) throws RemoteException;
    String readMessage(int authToken, int messageId) throws RemoteException;
    String deleteMessage(int authToken, int  messageId) throws RemoteException;
}

