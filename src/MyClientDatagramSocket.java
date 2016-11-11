/**
 * Created by t00126681 on 25/10/2016.
 */
import javax.swing.*;
import java.net.*;
import java.io.*;

/**
 * A subclass of DatagramSocket which contains
 * methods for sending and receiving messages
 * @author M. L. Liu
 *
 * Reference:
 * Even though this code is included in my project, this code is not written by myself JamesMcGarr.
 * The author of this code is M. L. Liu
 */
public class MyClientDatagramSocket extends DatagramSocket {
    static final int MAX_LEN = 100;
    MyClientDatagramSocket( ) throws SocketException{
        super( );
    }
    MyClientDatagramSocket(int portNo) throws SocketException{
        super(portNo);
    }
    public void sendMessage(InetAddress receiverHost,
                            int receiverPort,
                            String message)
            throws IOException {
        byte[ ] sendBuffer = message.getBytes( );
        DatagramPacket datagram =
                new DatagramPacket(sendBuffer, sendBuffer.length,
                        receiverHost, receiverPort);
        this.send(datagram);
    } // end sendMessage
    public String sendFile(InetAddress receiverHost,
                         int receiverPort,
                         byte[] file) throws IOException {
        DatagramPacket datagram = new DatagramPacket(file, file.length, receiverHost, receiverPort);
        System.out.println("message sent");
        this.send(datagram);
        System.out.println("message received");
        String receiveMessage = new String(receiveMessage());
        return receiveMessage;
    }
    public byte[] getFile() throws IOException {
        byte[] receiveBuffer = new byte[MAX_LEN];
        DatagramPacket datagram = new DatagramPacket(receiveBuffer, MAX_LEN);
        this.receive(datagram);
        return receiveBuffer;
    }

    public byte[] receiveMessage()
            throws IOException {
        byte[ ] receiveBuffer = new byte[MAX_LEN];
        DatagramPacket datagram =
                new DatagramPacket(receiveBuffer, MAX_LEN);
        this.receive(datagram);
        return receiveBuffer;
    } //end receiveMessage
} //end class
