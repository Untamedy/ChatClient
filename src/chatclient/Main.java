package chatclient;

import chatclient.procesing.GetThread;
import chatclient.source.Utils;
import chatclient.entities.Message;
import chatclient.entities.User;
import chatclient.procesing.Login;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {           
            User user = new User();
           

            while (!user.isStatus()) {
                System.out.println("Enter your login: ");
                String login = scanner.nextLine();
                System.out.println("Enter your password");
                String pass = scanner.nextLine();

                user = Login.send(Utils.getURL() + "/login" + "?login=" + login + "&pass=" + pass);

                if (null == user) {
                    System.out.println("Ooops, Something went wrong. Try again");
                } else {
                    user.setStatus(true);                   
                }
            }

            System.out.println("Select Chat-Room");
            System.out.println("Write 'All' to select common room or 'Sport' to select sport room");
            String room = scanner.nextLine();
            user.setRoom(room);
            
            
            
            Thread th = new Thread(new GetThread());
            th.setDaemon(true);
            th.start();

            System.out.println("Enter user login whom the message will sent");
            String to = scanner.nextLine();
            System.out.println("Enter your message: ");
            while (true) {
                String text = scanner.nextLine();
                if (text.isEmpty()) {
                    break;
                }

                Message m = new Message(user.getName(), text, to);
                int res = m.send(Utils.getURL() + "/add");

                if (res != 200) { // 200 OK
                    System.out.println("HTTP error occured: " + res);
                    return;
                }
            }
        } catch (IOException ex) {
        }
    }
}
