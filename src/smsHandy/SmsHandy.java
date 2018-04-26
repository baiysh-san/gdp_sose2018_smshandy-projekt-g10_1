package smsHandy;

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
    /**
     * Constructor for objects of class SmsHandy.
     * @param number the mobile phone number
     * @param provider the provider instance
     */
    public SmsHandy(String number, Provider provider) {
        this.number = number;
        this.provider = provider;
        received = new ArrayList<>();
        sent = new ArrayList<>();
        provider.register(this);
    }

    /**
     * Sends an SMS via the provider to the recipient.
     * @param to the recipient of the SMS
     * @param content the content of the SMS
     */
    public void sendSms(String to, String content) {
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
        System.out.println("Sent messages:");
        System.out.println("------------------------------------------");
        sent.forEach(message -> {
            System.out.println(message);
            System.out.println("------------------------------------------");
        });
    }

    /**
     * Outputs a list of all received SMS messages to the console.
     */
    public void listReceived() {
        System.out.println("Received messages:");
        System.out.println("------------------------------------------");
        received.forEach(message -> {
            System.out.println(message);
            System.out.println("------------------------------------------");
        });
    }

    public String getNumber() {
        return number;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
