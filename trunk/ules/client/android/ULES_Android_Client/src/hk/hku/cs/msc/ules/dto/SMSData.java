package hk.hku.cs.msc.ules.dto;

public class SMSData {

	private String sender;
	private String from;
	private String content;
	
	public SMSData() {
		super();
	}
	
	public SMSData(String sender, String from, String content) {
		super();
		this.sender = sender;
		this.from = from;
		this.content = content;
	}
	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
