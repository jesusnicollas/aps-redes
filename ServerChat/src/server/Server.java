package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
	
	private ServerSocket serverSocket;
	
	private List<ClientHandler> client_array;
	
	public Server(int port) {
		
		client_array = new CopyOnWriteArrayList<>();
		
		
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server Start on port: "+ port);
			
			while(true) {
				System.out.println("Wait a new client...");
				
				Socket socket = serverSocket.accept();
				ClientHandler client = new ClientHandler(this, socket);
				
				client_array.add(client);
				
				System.out.println("Accept new client");
				System.out.println("This client is:"+ socket.getInetAddress().getHostAddress());
			}
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			if(serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}				
			}
			
		}
	}
	

	public void removeClient(ClientHandler client) {
		client_array.remove(client);
	}
	
	// broadCast is responsible for sending messages to all clients without repeating a message that a client has sent before.
	public void broadCastMessage(String message) {
		
		// confirm input
		//System.out.println("Client "+ serverSocket.getInetAddress().getHostName()+": "+message);
		
		if(message != null) {
			for(ClientHandler client : client_array) {
				client.sendMessage(message); 	
			}			
		}
		
	}


	public static void main(String[] args) {
		new Server(1234);
		
	}

}