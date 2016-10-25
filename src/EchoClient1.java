/**
 * Created by t00126681 on 25/10/2016.
 */
import java.io.*;
import java.util.Scanner;

/**
 * This module contains the presentaton logic of an Echo Client.
 * @author M. L. Liu
 */
public class EchoClient1 {
    static final String endMessage = ".";
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        try {
            String hostName = "localhost";
            String portNum = "9601";

            System.out.println("Login.\nEnter username: ");
            String username = input.nextLine().trim();

            System.out.println("What is your password: ");
            String password = input.nextLine().trim();

            EchoClientHelper1 helper = new EchoClientHelper1(hostName, portNum);



            String echo;
            echo = helper.getEcho("100:username:" + username + ":password:" + password);

            helper.done();
            System.out.println(echo);


        } // end try
        catch (Exception ex) {
            ex.printStackTrace( );
        } // end catch
    } //end main
} // end class
