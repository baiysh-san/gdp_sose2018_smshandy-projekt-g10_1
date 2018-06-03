package smsHandy.model;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;

import javafx.beans.property.StringProperty;
/**
 * A Message that can be sent via SMSHandy.
 */
public class Message {
    private String content;
    private Date date;
    private String from;
    private String to;
    private final StringProperty contentProperty;
    private final StringProperty toProperty;
    private final StringProperty fromProperty;
    private final StringProperty datePropery;
    
    //public Message() {   
    //}
    
    public Message(String content, String to, String from, Date date) {
        this.content = content;
        this.to = to;
        this.from = from;
        this.date = date;
        this.contentProperty = new SimpleStringProperty(content);
        this.toProperty = new SimpleStringProperty(to);
        this.fromProperty = new SimpleStringProperty(from);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String dateString = dateFormat.format(date);
        this.datePropery = new SimpleStringProperty(dateString);
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
    
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String message = dateFormat.format(date) + "\n"
                + "From: " + from + " To: " + to + "\n"
                + content;
        return message;
    }
    public StringProperty getContentProperty() {
        return contentProperty;
    }
    public StringProperty getDatePropery() {
        return datePropery;
    }
    public StringProperty getFromProperty() {
        return fromProperty;
    }
    public StringProperty getToProperty() {
        return toProperty;
    }
     
}
