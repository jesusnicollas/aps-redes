package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientGui extends JFrame implements KeyListener{
	
	// Primitive variable.
	private String username;
	private PrintWriter writer;
	
	// GUI variable.
	private JTextArea OutputMessage;
	private JTextField InputMessage;
	private JScrollPane OutputMessageScroll;
	
	private JPanel panelConfig;
	private JPanel panelChat;
	
	public ClientGui(PrintWriter writer, String username) {
		this.writer = writer;
		this.username = username;
		
		panelConfig = new JPanel();
		panelConfig.setSize(new Dimension(300, 200));
		panelConfig.setBackground(new Color(140, 130, 160));
		
		panelChat = new JPanel();
		panelChat.setLayout(new BorderLayout(2 ,2));
		
		OutputMessage = new JTextArea();
		OutputMessage.setEditable(false);
		OutputMessage.setBorder(BorderFactory.createTitledBorder("chat:"));
		OutputMessage.setLineWrap(true);
		OutputMessage.setWrapStyleWord(true);
		
		OutputMessageScroll = new JScrollPane(OutputMessage);
		OutputMessageScroll.getVerticalScrollBar().setValue(OutputMessageScroll.getVerticalScrollBar().getMaximum());
		OutputMessageScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		InputMessage = new JTextField();
		InputMessage.setBorder(BorderFactory.createTitledBorder("Send message:"));
		InputMessage.addKeyListener(this);
		
		panelChat.add(OutputMessageScroll, BorderLayout.CENTER);
		panelChat.add(InputMessage, BorderLayout.SOUTH);
		
		add(panelConfig);
		add(panelChat);
		
		setLayout(new GridLayout(0, 2));
		setTitle("Chat: "+username);
		setSize(new Dimension(600, 400));
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public JTextArea getOutputMessage() {return OutputMessage;}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			String message = InputMessage.getText();
			
			if(!message.isEmpty()) {
				writer.println(username+": "+message);
				writer.flush();				
				InputMessage.setText("");
				
			}
				
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	
	

}