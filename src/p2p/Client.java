package p2p;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
	
	private final static int _dgLength = 50;
	private DatagramSocket dgSocket;
	private DatagramPacket dgPacket;
	private String uuid;
	private InetAddress address;
	private Integer port;

	public Client(InetAddress address, Integer port) throws IOException {
		dgSocket = new DatagramSocket();
		this.address = address;
		this.port = port;
	}

	private String receive() throws IOException {
		byte[] buffer = new byte[_dgLength];
		dgPacket = new DatagramPacket(buffer, _dgLength);
		dgSocket.receive(dgPacket);
		return new String(dgPacket.getData(), dgPacket.getOffset(),
				dgPacket.getLength());

	}

	private void send(String msg, InetAddress address, int port)
			throws IOException {
		byte[] buffer = msg.getBytes();
		dgPacket = new DatagramPacket(buffer, 0, buffer.length);
		dgPacket.setAddress(address);
		dgPacket.setPort(port);
		dgSocket.send(dgPacket);
	}

	private void sendQuit()
			throws IOException {
		String msg = "QUIT:"+this.uuid;
		byte[] buffer = msg.getBytes();
		dgPacket = new DatagramPacket(buffer, 0, buffer.length);
		dgPacket.setAddress(this.address);
		dgPacket.setPort(this.port);
		dgSocket.send(dgPacket);
	}

	private void register( )
			throws IOException {
		String msg = "RGTR";
		byte[] buffer = msg.getBytes();
		dgPacket = new DatagramPacket(buffer, 0, buffer.length);
		dgPacket.setAddress(this.address);
		dgPacket.setPort(this.port);
		dgSocket.send(dgPacket);
	}

	public static void main(String[] args) throws IOException {
		Client client = new Client(InetAddress.getByName("localhost"), 5001);
		String msg = "RGTR";
		while (true) {
			client.send(msg, InetAddress.getByName("localhost"), 5001);
			System.out.println(client.receive());
		}
	}
}
