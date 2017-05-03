package shared.com;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Connection implements ExceptionListener {
		
	private TopicConnection connection;
	private TopicSession session;
	private Topic sendTopic;
	private Topic receiveTopic;

	public Connection(String url, String sender, String receiver) throws JMSException {
		super();
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);
		connection = factory.createTopicConnection();
		connection.setExceptionListener(this);
		session = connection
				.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		sendTopic = session.createTopic(sender);
		receiveTopic = session.createTopic(receiver);
	}
/**
 * Erstellt einen neuen Publisher und gibt diesen zurück. Der Publisher wird bei der Kommunikation zwischen Client und Server für das Schreiben von Messages auf die Queue verwendet.
 * 
 */
	public Publisher createPublisher(int deliveryMode) throws JMSException {
		TopicPublisher topicPublisher = session.createPublisher(sendTopic);
		topicPublisher.setDeliveryMode(deliveryMode);
		return new Publisher(session, topicPublisher);
	}
/**
 * Startet die Connection.
 * Dient als Kapselung, damit Client bzw. Server nicht direkt auf die connection zugreifen.
 */
	public void start() throws JMSException {
		connection.start();
	}
/**
 * Schliesst die Connection.
 * Dient als Kapselung, damit Client bzw. Server nicht direkt auf die connection zugreifen.
 */
	public void close() {
		try {
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
/**
 * Fügt der Session einen Subscriber hinzu. Der Subscriber wird bei der Client-Server-Kommunikation für das Auslesen von Messages aus der Message-Queue benötigt.
 */
	public void createSubscriber(Subscriber subscriber) throws JMSException {
		session.createSubscriber(receiveTopic).setMessageListener(subscriber);
	}

	@Override
	public void onException(JMSException exception) {
		System.err.println("an error occurred: " + exception);
	}
}
