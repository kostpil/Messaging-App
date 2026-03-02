import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
public class MessagingClient {
    public static void main(String[] args) {
        if (args.length <3) {
            System.out.println("Usage: java MessagingClient <ip> <port> <FN_ID> [args]");
            return;}

        try {String ip = args[0];
            int port = Integer.parseInt(args[1]);
            int fnId = Integer.parseInt(args[2]);
            Registry registry= LocateRegistry.getRegistry(ip, port);
            MessagingInterface stub= (MessagingInterface) registry.lookup("MessagingService");

            switch (fnId) {
                case 1:
                    System.out.println(stub.createAccount(args[3]));
                    break;

                case 2:
                    List<String> accounts= stub.showAccounts();
                    for (String acc: accounts) {
                        System.out.println(acc);
                    }
                    break;

                case 3:
                    int token3= Integer.parseInt(args[3]);
                    String recipient= args[4];
                    String body= args[5];
                    System.out.println(stub.sendMessage(token3, recipient, body));
                    break;

                case 4:
                    int token4= Integer.parseInt(args[3]);
                    List<String> inbox= stub.showInbox(token4);
                    for (String msg: inbox) {
                        System.out.println(msg) ;
                    }
                    break;

                case 5:
                    int token5= Integer.parseInt(args[3]);
                    int msgId5= Integer.parseInt(args[4]);
                    System.out.println(stub.readMessage(token5, msgId5));
                    break;

                case 6:
                    int token6= Integer.parseInt(args[3]);
                    int msgId6= Integer.parseInt(args[4]);
                    System.out.println(stub.deleteMessage(token6, msgId6));
                    break;

                default:
                    System.out.println("Invalid FN_ID. Use 1-6.");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
