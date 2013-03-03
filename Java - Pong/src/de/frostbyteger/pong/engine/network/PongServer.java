package de.frostbyteger.pong.engine.network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class PongServer {

	protected String ip;
	protected int port;
	protected DatagramSocket socket;
	protected DatagramPacket query;
	
	public PongServer(String ip, int port){
		this.ip = ip;
		this.port = port;
	}
	
	public void initServer(){
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
