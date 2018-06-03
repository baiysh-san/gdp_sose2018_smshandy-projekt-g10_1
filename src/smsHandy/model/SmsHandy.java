package smsHandy.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Abstract base class SmsHandy.
 */
public abstract class SmsHandy {

    private String number;
    private Provider provider;
    private List<Message> received;
    private List<Message> sent;
    private String receivedString;
    private String sentString;
    private String type;
    private final StringProperty numberProperty;
    private final StringProperty typeProperty;
    /**
     * Constructor for objects of class SmsHandy.
     * @param number the mobile phone number
     * @param provider the provider instance
     */
    public SmsHandy(String number, Provider provider) throws Exception {
        isNumber(number);
        this.number = number;
        this.numberProperty = new SimpleStringProperty(number);
        this.provider = provider;
        received = new ArrayList<>();
        sent = new ArrayList<>();
        if (this instanceof PrepaidSmsHandy) {
            type = "Prepaid";
        } else {
            type = "Tariff plan";
        }
        this.typeProperty = new SimpleStringProperty(type);
        provider.register(this);
    }
    /**
     * Checks if number is number or *#+
     * @param number
     * @return
     */
    public boolean isNumber(String number) {
        if(number.matches("[0-9*#+]+")) {
            return true;
        } else {
            throw new NumberFormatException("\""+ number +"\" is not number!");
        }
    }

    /**
     * Sends an SMS via the provider to the recipient.
     * @param to the recipient of the SMS
     * @param content the content of the SMS
     */
    public void sendSms(String to, String content) throws Exception {
        Message message = new Message(content, to, number, new Date());
        if (to.equals("*101#")) {
           provider.send(message);
           sent.add(message);
        } else {
            if (canSendSms()) {
                if (provider.send(message)) {
                    payForSms();
                    sent.add(message);
                }
            }
        }
    }

    /**
     * Abstract method to check whether the sending of the SMS can still be paid for.
     * @return is it still possible to send the SMS?
     */
    public abstract boolean canSendSms();

    /**
     * Abstract method to pay for sending SMS.
     */
    public abstract void payForSms();

    /**
     * Sends an SMS to the recipient without the provider
     * @param peer the receiving mobile phone
     * @param content the content of the SMS
     */
    public void sendSmsDirect(SmsHandy peer, String content) {
        if (canSendSms()) {
            Message message = new Message(content, peer.number, number, new Date());
            peer.receiveSms(message);
            payForSms();
            sent.add(message);
        }
    }

    /**
     * Receives an SMS and stores it in the received SMS
     * @param message The message object that is to be sent to the second mobile phone
     */
    public void receiveSms(Message message) {
        received.add(message);
    }

    /**
     * Outputs a list of all sent SMS messages to the console.
     */
    public void listSent() {
        //System.out.println("Sent messages:");
        sentString += "Sent messages:";
        //System.out.println("------------------------------------------");
        sentString += "------------------------------------------";
        sent.forEach(message -> {
            //System.out.println(message);
            sentString += message;
            sentString += "------------------------------------------";
            //System.out.println("------------------------------------------");
        });
    }

    /**
     * Outputs a list of all received SMS messages to the console.
     */
    public void listReceived() {
        
        if(receivedString == null) {
            receivedString = "Received messages:";
        } else {
            receivedString += "Received messages:";
        }
        
        receivedString += "------------------------------------------";
        received.forEach(message -> {
            receivedString += message.getFrom();
            receivedString += "------------------------------------------";
        });
    }

    public String getNumber() {
        return number;
    }

    public Provider getProvider() {
        return provider;
    }

    public List<Message> getReceived() {
        return received;
    }

    public List<Message> getSent() {
        return sent;
    }

    public StringProperty getNumberProperty() {
        return numberProperty;
    }

    public String getType() {
        return type;
    }


    public StringProperty getTypeProperty() {
        return typeProperty;
    }
    public String getReceivedString() {
        return receivedString;
    }
    public String getSentString() {
        return sentString;
    }

    /**
     * Change provider of the SMS-Handy.
     * @param newProvider
     */
    public void changeProvider(Provider newProvider) {
        if (this.provider != newProvider) {
            try {
                newProvider.registerFromAnotherProvider(this, this.provider.getCreditForSmsHandy(getNumber()));
                this.provider.removeHandy(this);
                this.provider = newProvider;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
