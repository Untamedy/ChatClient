package chatclient;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Enter your login: ");
			String login = scanner.nextLine();
	
			Thread th = new Thread(new GetThread());
			th.setDaemon(true);
			th.start();
                        
                    System.out.println("Enter user login whom the message will sent");
                    String to = scanner.nextLine();
            System.out.println("Enter your message: ");
			while (true) {
				String text = scanner.nextLine();
				if (text.isEmpty()) break;

				Message m = new Message(login, text,to);
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
