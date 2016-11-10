/**
 * Created by t00126681 on 25/10/2016.
 */
import javax.swing.*;
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
        echo = new String(mySocket.receiveMessage());
        System.out.println("message recieved");
        return echo;
    } //end getEcho
    public String sendFile(String stringPath) throws IOException {
        String protocol = "200";
        byte[] data = FileSystemUtils.getBytesFromPath(stringPath);
        int length = data.length;
        String name = FileSystemUtils.getFileNameFromPath(stringPath);

        if (name.length() > 10) {
            JOptionPane.showMessageDialog(null, "File name too large. Name cannot be greater than 10 characters in length including the extension.");
            return "";
        }
        if (length > 83) {
            JOptionPane.showMessageDialog(null, "File size too large to upload. Must by 83 bytes or less.");
            return "";
        }
        byte[] bytesForPacket = PackageFilePacket.packagedPacket("200", length, name, data);

        return mySocket.sendFile(serverHost, serverPort, bytesForPacket);
    }
    public void getFile(String fileName) throws IOException {
        String protocol = "300";

        if (fileName.length() > 10) {
            JOptionPane.showMessageDialog(null, "File name too long. Files on the server are only 10 characters including the file extension.");
            return;
        }
        if (fileName.length() < 10) {
            while (fileName.length()<10) {
                fileName = fileName + " ";
            }
        }

        byte[] bytesForPacket = PackageFilePacket.packagedPacket("300", -1, fileName, " ".getBytes());

        String response = mySocket.sendFile(serverHost, serverPort, bytesForPacket);

        byte[] fileData = response.getBytes();
        String receiveProtocol = ReadFilePacket.getProtocol(fileData).trim();
        if (receiveProtocol.equals("600")) {
            JOptionPane.showMessageDialog(null, "Error Downloading file. Error 600");
            return;
        } else if (receiveProtocol.equals("200")) {
            createFileFromNameAndBytes(fileName,fileData);
            JOptionPane.showMessageDialog(null, "Download succeeded.");
        } else {
            JOptionPane.showMessageDialog(null, "An unknown error occurred.");
        }

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
