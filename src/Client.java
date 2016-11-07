/**
 * Created by t00126681 on 25/10/2016.
 */
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

            String echo;
            echo = helper.getEcho("100:username:" + username + ":password:" + password);

            helper.done();
            String hash = echo.substring(4, echo.length());
            System.out.println(echo);
            System.out.println(hash);


        } // end try
        catch (Exception ex) {
            ex.printStackTrace( );
        } // end catch
    } //end main
} // end class
