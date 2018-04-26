package smsHandy;

/**
 * Abstract base class SmsHandy.
 */
public abstract class SmsHandy {
    private String number;
    private Provider provider;

    /**
     * Constructor for objects of class SmsHandy.
     * @param number the mobile phone number
     * @param provider the provider instance
     */
    public SmsHandy(String number, Provider provider) {
        this.number = number;
        this.provider = provider;
    }

    /**
     * Sends an SMS via the provider to the recipient.
     * @param to the recipient of the SMS
     * @param content the content of the SMS
     */
    public void sendSms(String to, String content) {

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

    }

    /**
     * Receives an SMS and stores it in the received SMS
     * @param message The message object that is to be sent to the second mobile phone
     */
    public void receiveSms(Message message) {

    }

    /**
     * Outputs a list of all sent SMS messages to the console.
     */
    public void listSent() {

    }

    /**
     * Outputs a list of all received SMS messages to the console.
     */
    public void listReceived() {

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
