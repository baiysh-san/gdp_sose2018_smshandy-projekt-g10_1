package smsHandy;

import java.sql.Date;
/**
 * A Message that can be sent via SMSHandy.
 */
public class Message {
    private String content;
    private Date date;
    private String from;
    private String to;
    
    public Message() {
        
    }
    
    public Message(String content, String to, String from, Date date) {
        this.content = content;
        this.to = to;
        this.from = from;
        this.date = date;
    }
    
    public String getContent() {
        return content;
    }
    public Date getDate() {
        return date;
    }
    public String getFrom() {
        return from;
    }
    public String getTo() {
        return to;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public void setTo(String to) {
        this.to = to;
    }
    
    
}
