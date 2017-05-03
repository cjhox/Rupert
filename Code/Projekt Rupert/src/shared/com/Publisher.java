package shared.com;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
/**
 * Der Publisher schreibt Messages auf die Message Queue.
 */
public class Publisher {

	private TopicSession session;
	private TopicPublisher topicPublisher;

	public Publisher(TopicSession session, TopicPublisher topicPublisher) {
		this.session = session;
		this.topicPublisher = topicPublisher;
	}

	public void publish(Serializable obj) throws JMSException {
		topicPublisher.publish(session.createObjectMessage(obj));
		System.out.println(topicPublisher.getTopic().getTopicName() + " -> Sent: " + obj.toString());
	}

}