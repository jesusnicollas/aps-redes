package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import client.Client;


// This class is responsible for managing client connections.
public class ClientHandler implements Runnable{
	
	private Server server;
	private Socket socketClient;
	
	// Read messages from the client.
	private BufferedReader readerFromClient;
	
	// Write messages for the client.
	private PrintWriter writerFromClient;
	
	private String username;
	
	public ClientHandler(Server server, Socket socketClient) {
		this.server = server;
		this.socketClient = socketClient;
		
		new Thread(this).start();
	}
	
	// This thread is responsible for waiting for messages, reading messages, 
	// and also sending messages to all clients.
	@Override
	public void run() {
		
		try {
			readerFromClient = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
			writerFromClient = new PrintWriter(new OutputStreamWriter(socketClient.getOutputStream()));
			
			username = readerFromClient.readLine();
			
			// Send the message to all the clients that are connected.
			server.broadCastMessage(username + ": has connected!");
			
			// Reading messages and sending them.
			String message = readerFromClient.readLine();
			
			while(message != null) {
				server.broadCastMessage(message);
				
				message = readerFromClient.readLine();			
			}
			
		}
		catch(IOException e) {
			server.removeClient(this);
			server.broadCastMessage(username + ": has disconnected...");
			closeAll(socketClient, readerFromClient, writerFromClient);
		}
		
	}
	
	public void closeAll(Socket socket, BufferedReader reader, PrintWriter writer) {
		try {
			socket.close();
			reader.close();
			writer.close();
		}
		catch(IOException e) {
			
		}
	}
	
	// This method sends a message to the client.
	public void sendMessage(String message) {
		writerFromClient.println(message);
		writerFromClient.flush();
	}
	
	
}