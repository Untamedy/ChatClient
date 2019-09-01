package chatclient;

import chatclient.procesing.GetThread;
import chatclient.source.Utils;
import chatclient.entities.Message;
import chatclient.entities.User;
import chatclient.procesing.Login;
import chatclient.procesing.UserList;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        
       
        //   Socket socket = new Socket("localhost",8080)
        try (Scanner scanner = new Scanner(System.in)) {
            User user = new User();
           // PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
           // BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (user.getStatus().equals("off")) {
                System.out.println("Enter your login: ");
                String login = scanner.nextLine();
                System.out.println("Enter your password");
                String pass = scanner.nextLine();

                user = Login.send(Utils.getURL() + "/login" + "?login=" + login + "&pass=" + pass);

                if (null == user) {
                    System.out.println("Ooops, Something went wrong. Try again");
                } else {
                    user.setStatus("active");
                }
            }

            System.out.println("Select Chat-Room");
            System.out.println("Write 'All' to select common room or 'Sport' to select sport fanats room");
            String room = scanner.nextLine();
            if(room==null||room.isEmpty()){
                room = "All";
            }
            user.setRoom(room);

            Thread th = new Thread(new GetThread(user));
            th.setDaemon(true);
            th.start();
           
            System.out.println("Enter user login whom the message will sent");
            String to = scanner.nextLine();
            System.out.println("Is it a private message?(Yes/No)");
            String isPrivate = scanner.nextLine();

            System.out.println("Enter your message: ");
            while (true) {
                String text = scanner.nextLine();
                if (text.isEmpty()) {
                    break;
                }
                if (text.equalsIgnoreCase("show all users")) {
                    List<User> users = UserList.send(Utils.getURL() + "/users");
                    if (!users.isEmpty()) {
                        users.forEach((User u) -> {
                            System.out.println(u.toString());
                        });

                        Message m = new Message(user.getName(), text, to);
                        if (isPrivate.equalsIgnoreCase("Yes")) {
                            m.setIsprivate(true);
                        }
                      //  writer.write(m.toJSON());

                        int res = m.send(Utils.getURL() + "/add");

                        if (res != 200) { // 200 OK
                            System.out.println("HTTP error occured: " + res);
                            return;
                        }

                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
