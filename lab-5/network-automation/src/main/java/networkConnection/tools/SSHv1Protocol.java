package networkConnection.tools;

public class SSHv1Protocol implements ConnectionProtocol {

    public void connect(String device) {
        System.out.println("Connected to the " + device + " by SSHv1");
    }

}
