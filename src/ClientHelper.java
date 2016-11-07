/**
 * Created by t00126681 on 25/10/2016.
 */
import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class is a module which provides the application logic
 * for an Echo client using connectionless datagram socket.
 * @author M. L. Liu
 */
public class ClientHelper {
    private MyClientDatagramSocket mySocket;
    private InetAddress serverHost;
    private int serverPort;

    ClientHelper(String hostName, String portNum)
            throws SocketException, UnknownHostException {
        this.serverHost = InetAddress.getByName(hostName);
        this.serverPort = Integer.parseInt(portNum);
        // instantiates a datagram socket for both sending
        // and receiving data
        this.mySocket = new MyClientDatagramSocket();
    }

    public String getEcho( String message)
            throws SocketException, IOException {
        String echo = "";
        mySocket.sendMessage( serverHost, serverPort, message);
        System.out.println("message sent");
        // now receive the echo
        echo = mySocket.receiveMessage();
        System.out.println("message recieved");
        return echo;
    } //end getEcho
    public void sendFile(String stringPath) throws IOException {
        String protocol = "200";
        byte[] data = FileSystemUtils.getBytesFromPath(stringPath);
        int length = data.length;
        String name = FileSystemUtils.getFileNameFromPath(stringPath);

        if (length > 83) {
            System.out.println("File is too large. Exiting.");
            return;
        }
        byte[] bytesForPacket = PackageFilePacket.packagedPacket("200", length, name, data);

        mySocket.sendFile(serverHost, serverPort, bytesForPacket);
    }

    public void done( ) throws SocketException {
        mySocket.close( );
    }  //end done

} //end class
