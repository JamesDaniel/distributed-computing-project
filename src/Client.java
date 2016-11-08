/**
 * Created by t00126681 on 25/10/2016.
 */
import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * This module contains the presentaton logic of an Echo Client.
 * @author M. L. Liu
 */
public class Client extends JFrame {
    private JTextField username;
    private JTextField password;
    private JTextField downloadFileName;
    private final String hostname = "localhost";
    private final String port = "3001";
    private Client() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,400);
        this.setLayout(new FlowLayout());

        username = new JTextField("john");
        password = new JTextField("pass");
        JButton login = new JButton("Login");
        JButton logout = new JButton("Logout");
        JButton upload = new JButton("Upload File.");
        JButton download = new JButton("Download File.");
        downloadFileName = new JTextField("test.txt", 20);

        login.addActionListener(e -> login());
        logout.addActionListener(e -> logout());
        upload.addActionListener(e -> uploadFile());
        download.addActionListener(e -> downloadFile());


        this.add(username);
        this.add(password);
        this.add(login);
        this.add(logout);
        this.add(upload);
        this.add(download);
        this.add(downloadFileName);
        this.setVisible(true);
    }
    private void login() {
        try {
            ClientHelper helper = new ClientHelper(hostname, port);
            String echo;
            echo = helper.getEcho("100" + username.getText() + password.getText());
            if (echo.substring(0,3).equals("500")) {
                JOptionPane.showMessageDialog(null,"Login Successful");
            } else if (echo.substring(0,3).equals("600")) {
                JOptionPane.showMessageDialog(null,"Invalid username password combination");
            } else {
                JOptionPane.showMessageDialog(null,"An unknown Error occurred");
            }
            helper.done();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void logout() {
        try {
            ClientHelper helper = new ClientHelper(hostname, port);
            String echo;
            echo = helper.getEcho("400" + username.getText() + password.getText());
            if (echo.substring(0,3).equals("500")) {
                JOptionPane.showMessageDialog(null,"Logout Successful");
            } else if (echo.substring(0,3).equals("600")) {
                JOptionPane.showMessageDialog(null,"Logout not successful");
            } else {
                JOptionPane.showMessageDialog(null,"An unknown Error occurred");
            }
            helper.done();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void uploadFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(this);

        String filePathToUpload = fileChooser.getSelectedFile().getAbsolutePath();

        try {
            ClientHelper helper = new ClientHelper(hostname, port);
            String response = helper.sendFile(filePathToUpload);
            if (response.equals("")) {
                return;
            } else if (response.trim().equals("500")) {
                JOptionPane.showMessageDialog(null, "File uploaded successfully");
            } else if (response.equals("600")) {
                JOptionPane.showMessageDialog(null, "File upload failed");
            } else {
                JOptionPane.showMessageDialog(null, "An unknown error occurred");
            }

            helper.done();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void downloadFile() {
        try {
            ClientHelper helper = new ClientHelper(hostname, port);
            helper.getFile(downloadFileName.getText());
            helper.done();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Client();
    }
}
