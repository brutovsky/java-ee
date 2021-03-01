package networkConnection.tools;

public class TelnetProtocol implements ConnectionProtocol {

    public void connect(String device) {
        System.out.println("Connected to the " + device + " by Telnet");
    }

}
