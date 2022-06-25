/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chat.room;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
	public static void main(String[] args) {
		final ServerSocket serverSocket;
		Socket clientSocket;
		  
		// out; // to write message for client
		final Scanner input = new Scanner(System.in); // to take input from console
                
                ArrayList<String> IDs= new ArrayList<String>();
                ArrayList<Socket> clientsocs= new  ArrayList<Socket>();
                

		try {
			serverSocket = new ServerSocket(400);
                        while(true){
                            clientSocket = serverSocket.accept();
                            boolean add = clientsocs.add(clientSocket);
                            clientsocs.add(clientSocket);
                            
			
//			// sender thread
//			Thread senderThread = new Thread(new Runnable() {
//				String msg; 
//				@Override
//				public void run() {
//					while (true) {
//						
//					}
//				}
//			});
//			senderThread.start(); //starting

			// receiver thread
			Thread receiverThread;
                            receiverThread = new Thread(new Runnable() {
                                
                                String msg;
                                String id;
                                
                                @Override
                                public void run() {
                                    Socket client = clientsocs.get(clientsocs.size()-1);
                                    try {
                                        PrintWriter out = null;
                                        
                                        BufferedReader in;
                                        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                                        msg = in.readLine();
                                        System.out.println("Client conected: " + msg);
                                        while (msg != null) {
                                            msg = in.readLine();
                                            System.out.println("Client: " + msg);
                                            id = in.readLine();
                                            IDs.add(id);
                                            System.out.println("Client: " + msg);
                                            int index=IDs.indexOf(id);
                                            try{
                                            out= new PrintWriter(clientsocs.get(index).getOutputStream());
                                            msg = input.nextLine();
				            out.println(msg);
					    out.flush();
                                            }
                                            catch(Exception e){
                                                
                                            }
                                        }
                                        
                                        System.out.println("Client Disconnected");
                                        
                                        out.close();
                                        client.close();
                                        serverSocket.close();
                                    }  catch (SocketException ex) {
                                        System.out.println("Client Disconnected");
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });
			receiverThread.start(); //starting
                        }
			
		} catch (SocketException ex) {
			System.out.println("Connection ended");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
