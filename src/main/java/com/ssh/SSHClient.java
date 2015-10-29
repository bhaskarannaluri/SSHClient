package com.ssh;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;


public class SSHClient {
	private Session session ;
	private List<Session> sessions;

	private boolean stopLogging;
	

	
	
	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public boolean isStopLogging() {
		return stopLogging;
	}

	public void setStopLogging(boolean stopLogging) {
		this.stopLogging = stopLogging;
	}

/*	public static void main(String[] args) {
	    String host="gchsc1lna025.itcent.ebiz.verizon.com";
	    String user="v418368";
	    String password="!3Gabbar";
	    String command1="pwd;cd  /apps/opt/app/gchsv/logs;pwd;tail -lf app.log;";
	    try{
	    	java.util.Properties config = new java.util.Properties(); 
	    	config.put("StrictHostKeyChecking", "no");
	    	JSch jsch = new JSch();
	    	Session session=jsch.getSession(user, host, 22);
	    	System.out.println("After getting session");
	    	session.setPassword(password);
	    	session.setConfig(config);
	    	System.out.println("Before connecting");
	    	session.connect();
	    	System.out.println("Connected");
	    	
	    	
	    	Channel channel=session.openChannel("exec");
	    	
	        ((ChannelExec)channel).setCommand(command1);
	        channel.setXForwarding(true);  
	     //  channel.setInputStream(null);
	        ((ChannelExec) channel).setOutputStream(System.out);  
	        ((ChannelExec)channel).setErrStream(System.err);
	        
	        InputStream in=channel.getInputStream();
	        channel.connect();
	        channel.setInputStream(System.in);  

	        System.out.println("connected?"+channel.isConnected());
	       //Writing into file 
	        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("D:\\out.txt"));
	        byte[] tmp=new byte[1024];
	        while(true){
	          while(in.available()>0 ){
	            int i=in.read(tmp, 0, 1024);
	            if(i<0)break;
	            bufferedWriter.write(new String(tmp, 0, i));
	            System.out.print(new String(tmp, 0, i));
	            bufferedWriter.flush();
	           // System.out.print();
	          }
	          if(channel.isClosed()){
	            System.out.println("exit-status: "+channel.getExitStatus());
	            break;
	          }
	          try{Thread.sleep(1000);}catch(Exception ee){}
	        }
	        System.out.println("firtst");
	    
		        System.out.println("2nd");
		        channel.disconnect();
	        session.disconnect();
	        System.out.println("DONE");
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	
	    	System.out.println("Final");
	    }

	}*/

	public  String connect(String host,String user,String password){
		String result ="";
		 try{
		    	java.util.Properties config = new java.util.Properties(); 
		    	config.put("StrictHostKeyChecking", "no");
		    	JSch jsch = new JSch();
		    	//New code starts
		    	String[] hosts =null;
		    	if(host.contains(",")){
		    		 hosts = host.split(",");
		    	}else{
		    		hosts[0]=host;
		    	}
		    	sessions = new ArrayList<Session>();
		    	if(hosts!=null&& hosts.length>1){
		    		for(String st:hosts){
		    			System.out.println("The host is "+st);
		    		Session s = jsch.getSession(user, st, 22);
			    	System.out.println("After getting session");
			    	s.setPassword(password);
			    	s.setConfig(config);
			    	System.out.println("Before connecting");
			    	s.connect();
			    	System.out.println("Connected");
			    	sessions.add(s);
		    		}
		    		if(!sessions.isEmpty()){
		    			result ="Connected";
		    		}
		    	}
		    	
		    	//New Code ends
		    /*	session=jsch.getSession(user, host, 22);
		    	System.out.println("After getting session");
		    	session.setPassword(password);
		    	session.setConfig(config);
		    	System.out.println("Before connecting");
		    	session.connect();
		    	System.out.println("Connected");
		    	result ="Connected";*/
		    }catch(Exception e){
		    	result = e.toString();
		    	e.printStackTrace();
		    }
		    return result;
	}
	/*public String logFile(String logDirectory,String logFile){
		String result ="";
		if(session.isConnected()){
			
			try{
				Channel channel=session.openChannel("exec");
					    	String command="pwd;cd "+logDirectory+";pwd;tail -lf "+logFile+";"; ; 
					    	System.out.println("Command is "+command);
					        ((ChannelExec)channel).setCommand(command);
					        channel.setXForwarding(true);  
					        ((ChannelExec) channel).setOutputStream(System.out);  
					        ((ChannelExec)channel).setErrStream(System.err);
					        
					        InputStream in=channel.getInputStream();
					        channel.connect();
					        channel.setInputStream(System.in);  

					        System.out.println("connected?"+channel.isConnected());
					       //Writing into file 
					        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("D:\\out"+Calendar.getInstance().getTime()+".txt"));
					        byte[] tmp=new byte[1024];
					        while( !stopLogging){
					          while(in.available()>0 ){
					            int i=in.read(tmp, 0, 1024);
					            if(i<0)break;
					            bufferedWriter.write(new String(tmp, 0, i));
					            System.out.print(new String(tmp, 0, i));
					            bufferedWriter.flush();
					          }
					        }
					    }catch(Exception e){
					    	e.printStackTrace();
					    }finally{
					    	
					    	System.out.println("Final");
					    }
		}else{
			result ="Session is not active";
		}
		return result;
}*/
	public void closeConnections(){
	/*	if(session.isConnected()){
			System.out.println("Closing connection");
			session.disconnect();
		}*/
		for(Session s: sessions){
			System.out.println("Closing connection");
			s.disconnect();
		}
	}
}
