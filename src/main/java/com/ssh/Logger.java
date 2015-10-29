package com.ssh;



import java.awt.*;
import java.awt.event.*;

import com.jcraft.jsch.Session;

public class Logger {

   private Frame mainFrame;
   private Label headerLabel;
   private Label statusLabel;
   private Panel controlPanel;
   private  SSHClient client;

   public Logger(){
      prepareGUI();
      client =  new SSHClient();
   }

   public static void main(String[] args){
	   Logger  awtControlDemo = new Logger();
      awtControlDemo.showTextFieldDemo();
   }

   private void prepareGUI(){
      mainFrame = new Frame("Log File ");
      mainFrame.setSize(400,400);
      mainFrame.setLayout(new GridLayout(4, 1));
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });    
      headerLabel = new Label();
      headerLabel.setAlignment(Label.CENTER);
      statusLabel = new Label();        
      statusLabel.setAlignment(Label.LEFT);
      statusLabel.setSize(350,100);

      Label support = new Label();   
      support.setAlignment(Label.LEFT);
      support.setText("For technical support, contact bhaskara.annaluri@verizon.com");
      
      controlPanel = new Panel();
      controlPanel.setLayout(new GridLayout(4,2));
      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
      mainFrame.add(statusLabel);
      mainFrame.add(support);
      mainFrame.setVisible(true);  
   }
   private void afterLogin(){
		Frame secureFrame = new Frame("Welcome to SSH Client");
		secureFrame.setSize(400, 400);
		secureFrame.setLayout(new GridLayout(4, 1));
		secureFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				client.closeConnections();
				System.exit(0);
			}
		});
		Label headerLabel1 = new Label();
		headerLabel1.setText("Logged in Session ");
		headerLabel1.setAlignment(Label.CENTER);
		Panel controlPanel1 = new Panel();
		controlPanel1.setLayout(new GridLayout(3,2));
	      
		Label  directoryLabel= new Label("Log Directory path: ", Label.CENTER);
		Label  logFileLabel= new Label("Log File Name: ", Label.CENTER);
		final  TextField directoryText = new TextField(50);
		 directoryText.setText("/apps/opt/app/gchsv/logs");
		 final TextField logFileText = new TextField(50);
		 logFileText.setText("app.log");
		 Button logButton = new Button("Start Loging");
		 logButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {     
	           /* String data = "Host Name : "+hostText.getText()+" \nUsername: " + userText.getText();
	            data += "\nPassword: " + passwordText.getText();*/
	        	 //String status  = client.logFile(directoryText.getText(), logFileText.getText());
	        	 //new LoggingThread(client,directoryText.getText(), logFileText.getText()).start();
	        	 for(Session s:client.getSessions()){
	        		 new LoggingThread(client,s,directoryText.getText(), logFileText.getText()).start();
	        	 }

	         }
	      }); 
		 Button stopLog = new Button("Stop Loging");
		 stopLog.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {     
	        	 //client.setStopLogging(true);
	        	 new StopLogging(client).start();
	         }
	      }); 
		 
		 Label support = new Label();   
	      support.setAlignment(Label.LEFT);
	      support.setText("For technical support, contact bhaskara.annaluri@verizon.com");
	      
	      
		 controlPanel1.add(directoryLabel);
		 controlPanel1.add(directoryText);
		 controlPanel1.add(logFileLabel);
		 controlPanel1.add(logFileText);
		 controlPanel1.add(stopLog);
	 	 controlPanel1.add(logButton);


		secureFrame.add(headerLabel1);
		secureFrame.add(controlPanel1);
		secureFrame.add(support);

		mainFrame.setVisible(false);
		secureFrame.setVisible(true);
	   }

   private void showTextFieldDemo(){
      headerLabel.setText("Connect to your Servers"); 
      Label  hostLabel= new Label("Host Names(eg:host1,host2): ", Label.RIGHT);
      Label  namelabel= new Label("User ID: ", Label.RIGHT);
      Label  passwordLabel = new Label("Password: ", Label.RIGHT);
      final TextField hostText = new TextField(50);
      final TextField userText = new TextField(10);
      final TextField passwordText = new TextField(10);
      passwordText.setEchoChar('*');

      Button loginButton = new Button("Login");
   
      loginButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {     
           /* String data = "Host Name : "+hostText.getText()+" \nUsername: " + userText.getText();
            data += "\nPassword: " + passwordText.getText();*/
        	 String status  =client.connect(hostText.getText(), userText.getText(), passwordText.getText());
        	 if(!(status.equals("Connected"))){
                 statusLabel.setText(status);        
        	 }else{
        		    afterLogin();
        	 }
         }
      }); 
      controlPanel.add(hostLabel);
      controlPanel.add(hostText);
      controlPanel.add(namelabel);
      controlPanel.add(userText);
      controlPanel.add(passwordLabel);       
      controlPanel.add(passwordText);
      controlPanel.add(new Label("", Label.CENTER));
      controlPanel.add(loginButton);
      mainFrame.setVisible(true);  
   }
}
