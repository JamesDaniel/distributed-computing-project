/**
 * Created by t00126681 on 25/10/2016.
 */
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * This module contains the presentaton logic of an Echo Client.
 * @author M. L. Liu
 */
public class Client {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        try {
            String hostName = "localhost";
            String portNum = "3001";

            System.out.println("Login.\nEnter username: ");
            String username = "john";//input.nextLine().trim();

            System.out.println("What is your password: ");
            String password = "pass";//input.nextLine().trim();

            ClientHelper helper = new ClientHelper(hostName, portNum);

            //String echo;
            //echo = helper.getEcho("100" + username + password);
            //System.out.println(echo);
            helper.getFile("test.txt");
            //helper.sendFile("data/hello.txt");
            helper.done();



        } // end try
        catch (Exception ex) {
            ex.printStackTrace( );
        } // end catch
    } //end main
    /*public static byte[] getFileAsBytes(String stringPath) {
        Path path = Paths.get(stringPath);
        try {
            byte[] data = Files.readAllBytes(path);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }*/
} // end class
