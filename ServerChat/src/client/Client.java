package client;

// Import read and write strings and bytes.
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

// Import the client's socket.
import java.net.Socket;

// Notify the client.
import javax.swing.JOptionPane;

public class Client{
	
	// Primitive variable.
	private String address;
	private String username;
	private int port;
	
	// object
	private Socket connectServer;
	private BufferedReader serverReader;
	private PrintWriter serverWriter;
	
	// The object from the other class, specifically the graphical user interface class.
	private ClientGui gui;
	
	// construct
	public Client(int port) {
		
		this.port = port;
		username = JOptionPane.showInputDialog("Enter your username: ");
		address = JOptionPane.showInputDialog("IP-Adress");
		
		// If the IP is different from null.
		if(address != null) {
			reciveMessage();
		}
	}
	
	// This method is responsible for receiving a message, creating the GUI, and sending the message.
	public void reciveMessage() {
		try {
			connectServer = new Socket(address, port);	
			
			serverReader = new BufferedReader(new InputStreamReader(connectServer.getInputStream()));
			serverWriter = new PrintWriter(new OutputStreamWriter(connectServer.getOutputStream()));
			
			serverWriter.println(username);
            serverWriter.flush();
			
            // Create the user interface.
			gui = new ClientGui(serverWriter, username);
			
			// Receive a message and print it in the chat.
			while(true) {
				String message = serverReader.readLine();
				gui.getOutputMessage().append(message+"\n");				
			}
		}
		catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Filed connection to Server \""+ address+"\"");
			closeAll(connectServer, serverReader, serverWriter, gui);
		}
	}
	
	// The method was created to close all objects.
	public void closeAll(Socket socket, BufferedReader reader, PrintWriter writer, ClientGui gui) {
		try {
			socket.close();
			reader.close();
			writer.close();
			gui.dispose();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	// Main method to start the client.
	public static void main(String[] args) {
		new Client(1234);
	}
}