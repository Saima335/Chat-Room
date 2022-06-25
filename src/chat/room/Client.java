/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chat.room;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) {
		final Socket clientSocket;
		final BufferedReader in; //to read server's message
		final PrintWriter out; // to send message to server
		final Scanner input = new Scanner(System.in); // to read user input from console
		try {
			clientSocket = new Socket("127.0.0.1", 400);
			out = new PrintWriter(clientSocket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			// sender thread
			Thread senderThread = new Thread(new Runnable() {
				String message;

				@Override
				public void run() {
					while (true) {
                                                System.out.println("Enter message");
						message = input.nextLine();
						out.println(message);
						out.flush();
                                                System.out.println("Enter Reciever ID");
						message = input.nextLine();
						out.println(message);
						out.flush();
					}
				}
			});
			senderThread.start();

			// receiver thread
			Thread receiverThread = new Thread(new Runnable() {
				String message;

				@Override
				public void run() {
					try {
						System.out.println("Enter your ID: ");
						message = in.readLine();
						while (message != null) {
							System.out.println("Server: " + message);
							message = in.readLine();
						}

						System.out.println("Server Disconnected");

						out.close();
						clientSocket.close();
					}  catch (SocketException ex) {
						System.out.println("Server Disconnected");
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			});
			receiverThread.start();
		} catch (SocketException ex) {
			System.out.println("Connection ended");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
