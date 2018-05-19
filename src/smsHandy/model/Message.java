package smsHandy.model;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
        isNumber(to);
        isNumber(from);
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
        if(isNumber(from))this.from = from;
    }
    public void setTo(String to) {
        if(isNumber(to)) this.to = to;
    }
    
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String message = dateFormat.format(date) + "\n"
                + "From: " + from + " To: " + to + "\n"
                + content;
        return message;
    }
    
    public boolean isNumber(String number) {
        if(number.matches("[0-9*#+]+")) {
            return true;
        } else {
            throw new NumberFormatException("\""+ number +"\" is not number!");
        }
    }
    
    
}
