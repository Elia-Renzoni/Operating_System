package mqtt;

public class Message<String, Integer> {
	private String topic;
	private Integer message;
	
	public Message(final String topic, final Integer message) {
		this.topic = topic;
		this.message = message;
	}
	
	public String getTopic() {
		return this.topic;
	}
	
	public Integer getMessage() {
		return this.message;
	}
}
