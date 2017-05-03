package client.domain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.URL;
import java.util.Observable;

import javax.jms.JMSException;

import shared.Player;
import shared.com.Publisher;

public class WindowModel extends Observable {

	protected Player	player;
	protected Publisher	toServer;

	public WindowModel(Player player, Publisher toServer) {
		this.player = player;
		this.toServer = toServer;
	}
	
	
	public void publish(Serializable obj) throws JMSException {
		toServer.publish(obj);
	}

	public Player getPlayer() {
		return player;
	}

	public static InetAddress getIP() {
		InetAddress inetAddress;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new URL("http://bot.whatismyipaddress.com").openConnection().getInputStream()));
			inetAddress = InetAddress.getByName((in.readLine()).trim());
		} catch (Exception e) {
			inetAddress = null;
		}
		return (inetAddress);
	}
}
