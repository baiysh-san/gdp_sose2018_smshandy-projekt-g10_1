package smsHandy.model;

import java.util.*;

/**
 * TODO Create packages for model, tests, etc...
 * 
 *
 */
public class Provider {
    private Map<String, Integer> credits;
    private Set<SmsHandy> subscribers;
    private static List<Provider> providerList = new ArrayList<>();
    private String name;
    public Provider(String name) {
        this.name = name;
        credits = new HashMap<>();
        subscribers = new HashSet<>();
        providerList.add(this);
    }
    
    /**
     * Sends the SMS to the recipient if the recipient is known.
     * @param message
     * @return
     */
    public boolean send(Message message) throws Exception {
        if (message.getTo().equals("*101#")) {
            for (SmsHandy smsHandy: subscribers) {
                if(smsHandy.getNumber().equals(message.getFrom())) {
                    String credit = "Your credits: " + getCreditForSmsHandy(smsHandy.getNumber());
                    smsHandy.receiveSms(new Message(credit, smsHandy.getNumber(),"Provider", new Date()));
                    return true;
                }
            }
        }
        String to = message.getTo();
        if (canSendTo(to)) {
            for (SmsHandy smsHandy : subscribers) {
                if (smsHandy.getNumber().equals(to)) {
                    smsHandy.receiveSms(message);
                }
            }

            return true;
        } else {
            Provider provider = findProviderFor(to);
            if (provider != null) {
                provider.send(message);
                return true;
            } else {
                throw new Exception("This number doesn't exist");
            }
        }
    }

    /**
     * Register a new "SmsHandy" with this provider.
     * @param smsHandy
     */
    public void register(SmsHandy smsHandy) throws Exception {
        if(credits.containsKey(smsHandy.getNumber())) {
            throw new Exception("This phone is already registered!");
        } else {
            credits.put(smsHandy.getNumber(), 100);
            subscribers.add(smsHandy);
        }
    }
    
    /**
     * Loads credit on a cell phone. 
     * This is necessary because the mobile phone cannot 
     * change its credit itself, only the provider. 
     * Negative amounts of money are allowed here, 
     * in order to be able to deduct the costs for 
     * a message via this function. Negative values during manual 
     * charging are prevented in the SmsHandy class.
     * @param number
     * @param amount
     */
    public void deposit(String number, int amount) throws Exception {
        if(canSendTo(number)) {
            credits.put(number, credits.get(number) + amount);
        } else {
            throw new Exception("This phone number is not subscriber!");
        }
    }
    
    /**
     * Returns the current credit of the respective mobile phone
     * @param number
     * @return
     */
    public int getCreditForSmsHandy(String number) {
        return credits.get(number);
    }
    
    /**
     * 
     * @param receiver
     * @return
     */
    private boolean canSendTo(String receiver) {
        for (SmsHandy smsHandy : subscribers) {
            if (smsHandy.getNumber().equals(receiver)){
                return true;
            } 
        }
        return false;
    }
    
    /**
     * 
     * @param receiver
     * @return
     */
    private static Provider findProviderFor(String receiver) {
       for (Provider provider : providerList) {
           if (provider.canSendTo(receiver)) {
               return provider;
           } 
       }
       return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}