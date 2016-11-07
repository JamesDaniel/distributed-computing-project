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
    public void getFile(String fileName) throws IOException {
        String protocol = "300";

        if (fileName.length() > 10) {
            fileName = fileName.substring(0,10);
        } else {
            while (fileName.length()<10) {
                fileName = fileName + " ";
            }
        }

        byte[] bytesForPacket = PackageFilePacket.packagedPacket("300", -1, fileName, " ".getBytes());

        mySocket.sendFile(serverHost, serverPort, bytesForPacket);
        System.out.println("got here");
        byte[] receivedData = mySocket.getFile();

        byte[] fileData = receivedData;
        createFileFromNameAndBytes(fileName,fileData);
        System.out.println("download request finished.");
    }
    public void createFileFromNameAndBytes(String name, byte[] bytes) throws IOException {
        byte[] fileContent = ReadFilePacket.getFileContent(bytes);
        new File("Downloads").mkdir();
        Path path = Paths.get("Downloads/" + name.trim());
        Files.write(path,fileContent);
    }

    public void done( ) throws SocketException {
        mySocket.close( );
    }  //end done

} //end class
