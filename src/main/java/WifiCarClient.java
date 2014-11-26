import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class WifiCarClient {
	private HeartBeatSocket heartBeatSocket;
	private final SocketAddress address;

	public WifiCarClient(final String hostName, final int port) throws UnknownHostException, IOException {
		this.address = new InetSocketAddress(hostName, port);
		initSocket();
	}

	private void initSocket() {
		while (true) {
			try {
				final Socket socket = new Socket();
				socket.setKeepAlive(true);
				socket.setTcpNoDelay(true);
				socket.setPerformancePreferences(0, 2, 1);

				socket.connect(this.address, 3000);
				System.out.println("connected to " + this.address.toString());
				this.heartBeatSocket = new HeartBeatSocket(socket, 5000);
				return;
			}
			catch (final Exception e) {
				System.out.println("init Failed. Prepare to reinit");
				System.err.println(e);
			}

			try {
				Thread.sleep(5000);
			}
			catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void send(final String message) {
		this.heartBeatSocket.writeLine(message);
	}

	public String readline() {
		try {
			return this.heartBeatSocket.readLine();
		}
		catch (final Exception e) {
			System.out.println(e.getMessage());
			this.heartBeatSocket.setAlive(false);
			return null;
		}

	}

	public void reconnect() {
		initSocket();
	}

	public HeartBeatSocket getHeartBeatSocket() {
		return this.heartBeatSocket;
	}

	public static void main(final String[] args) throws InterruptedException, IOException {
		String hostName = "hqd-usermgmt-01";
		if (args.length > 0) {
			hostName = args[0];
		}
		final WifiCarClient client = new WifiCarClient(hostName, 10000);
		Thread.sleep(3000);
		while (true) {
			System.out.println("isAlive:" + client.getHeartBeatSocket().isAlive());
			if (client.getHeartBeatSocket().isAlive()) {
				System.out.println(client.readline());
			}
			else {
				client.reconnect();
			}
		}
	}
}
