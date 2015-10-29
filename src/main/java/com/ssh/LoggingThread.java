package com.ssh;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;

public class LoggingThread extends Thread {
	private SSHClient client;
	String logDirectory;
	String logFile;
	Session session;
	public LoggingThread(SSHClient client,Session session,String logDirectory,String logFile) {
		this.client= client;
		this.session=session;
		this.logDirectory=logDirectory;
		this.logFile= logFile;
	}

	public void run() {
		if(session.isConnected()){
			this.logFile(logDirectory, logFile);
		}
	}
	public String logFile(String logDirectory,String logFile){
		String result ="";
		//Session session = client.getSession();
		if(session.isConnected()){
			client.setStopLogging(false);
			BufferedWriter bufferedWriter = null;
			Channel channel=null;
			try{
							channel=session.openChannel("exec");
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
					        String file="D:\\out"+this.getId()+".txt";
					        bufferedWriter = new BufferedWriter(new FileWriter(file));
					        System.out.println("File Name is "+file);
					        byte[] tmp=new byte[1024];
					        while( !client.isStopLogging()){
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
					    	channel.disconnect();
					    	try{
					    	bufferedWriter.close();
					    	}catch (IOException e) {
							}
					    	System.out.println("Final");
					    }
		}else{
			result ="Session is not active";
		}
		return result;
}
}
