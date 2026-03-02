import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
public class MessagingServer extends UnicastRemoteObject implements MessagingInterface {
    private final HashMap<Integer,Account> allUsers= new HashMap<>();
    private final Random rand= new Random();
    public MessagingServer() throws RemoteException
    {super();}

    public String createAccount(String username) throws RemoteException {
        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            return "Invalid Username";}
        for (Account acc : allUsers.values()) {
            if (acc.username.equals(username)) {
                return "Sorry, the user already exists";}}

        int newToken = 1000+ rand.nextInt(9000);
        Account newUser= new Account(username, newToken);
        allUsers.put(newToken, newUser);
        return String.valueOf(newToken);
    }

    public List<String> showAccounts() throws RemoteException {
        List<String> results =new ArrayList<>();
        int counter = 1;
        for (Account acc: allUsers.values()) {
            results.add(counter +". "+acc.username);
            counter++;
        }
        return results;
    }

    public String sendMessage(int authToken, String recipient, String messageBody) throws RemoteException {
        if (!allUsers.containsKey(authToken)) {
            return "Invalid Auth Token";
        }
        Account targetUser = null;
        for (Account acc :allUsers.values()) {
            if (acc.username.equals(recipient)) {
                targetUser = acc;
                break;
            }
        }

        if (targetUser==null) {
            return "User does not exist";
        }
        String myName = allUsers.get(authToken).username;
        Message msg = new Message(myName, recipient, messageBody);
        targetUser.messageBox.add(msg);
        return "OK";
    }

    public List<String> showInbox (int authToken) throws RemoteException {
        if (!allUsers.containsKey(authToken)) {
            List<String> error = new ArrayList<>();
            error.add("Invalid Auth Token");
            return error;
        }
        List<String> output =new ArrayList<>();
        List<Message> userMsgs =allUsers.get(authToken).messageBox;

        for (int i=0; i<userMsgs.size(); i++) {
            Message m=userMsgs.get(i);
            String mark ="";
            if (!m.isRead) {
                mark="*";
            }
            output.add(i+". from: "+ m.sender+mark);
        } return output;
    }

    public String readMessage(int authToken, int messageId) throws RemoteException {
        if (!allUsers.containsKey(authToken)) {
            return "Invalid Auth Token";
        }
        List<Message> myMsgs = allUsers.get(authToken).messageBox;
        if (messageId < 0 || messageId >= myMsgs.size()) {
            return "Message ID does not exist";}
        Message temp = myMsgs.get(messageId);
        temp.isRead = true;
        return "(" +temp.sender+ ")"+temp.body;
    }

    public String deleteMessage(int authToken, int messageId) throws RemoteException {
        if (!allUsers.containsKey(authToken)) {
            return "Invalid Auth Token";
        }
        List<Message> myMsgs = allUsers.get(authToken).messageBox;
        if (messageId < 0 || messageId >= myMsgs.size()) {
            return "Message does not exist";
        }
        myMsgs.remove(messageId);
        return "OK";}


    public static void main(String[] args) {
        try {if (args.length < 1) {
                System.out.println("Usage: java MessagingServer <port>");
                return;
            }
            int port = Integer.parseInt(args[0]);
            MessagingServer myServer = new MessagingServer();
            Registry reg = LocateRegistry.createRegistry(port);
            reg.rebind("MessagingService", myServer);
            System.out.println("Server is running on port " + port);
        } catch (Exception ex) {
            System.out.println("Server error: "+ex.getMessage());
        }
    }









}







