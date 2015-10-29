
package com.ssh;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;

public class StopLogging extends Thread {
	private SSHClient client;
	public StopLogging(SSHClient client) {
		this.client= client;
	}

	public void run() {
		/*if(client.getSession().isConnected()){
			System.out.println("Inside disable");
			client.setStopLogging(true);
		}*/
		System.out.println("Inside Logging disable");
		client.setStopLogging(true);
	}
	
}
