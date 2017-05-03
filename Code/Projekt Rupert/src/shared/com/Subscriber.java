package shared.com;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
/**
 * Der Subscriber wird aktiv, sobald ein Publisher eine Message auf die Queue geschrieben hat und liest diese aus.
 */
public abstract class Subscriber implements MessageListener {

	public abstract void onMessage(ObjectMessage message);
	
	public void onMessage(Message message) {
		onMessage((ObjectMessage) message);
	}
}