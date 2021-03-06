package smsHandy.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.*;

/**
 *  Class Provider 
 *
 */
public class Provider {
    private Map<String, Integer> credits;
    private Set<SmsHandy> subscribers;
    private static List<Provider> providerList = new ArrayList<>();
    private String name;
    private final StringProperty nameProperty;

    /**
     * Constructor of Provider.
     * @param name
     */
    public Provider(String name) {
        this.name = name;
        nameProperty = new SimpleStringProperty(name);
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
                throw new NumberFormatException("This number doesn't exist");
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
     * Register a SmsHandy to this provider.
     * @param smsHandy we want to register.
     * @param credits of smsHandy.
     * @throws Exception
     */
    public void registerFromAnotherProvider(SmsHandy smsHandy, int credits) throws Exception {
        if(this.credits.containsKey(smsHandy.getNumber())) {
            throw new Exception("This phone is already registered!");
        } else {
            this.credits.put(smsHandy.getNumber(), credits);
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
     * Returns true if and only if a subscriber with the number 
     * receiver is registered with this provider.
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
     * Returns the provider where the subscriber with the number receiver is registered, 
     * or null if the number does not exist.
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

    /**
     * Removes all handys of this provider.
     */
    public void removeAllHandys() {
        subscribers.clear();
        credits.clear();
    }

    /**
     * Removes one handy of this provider.
     * @param smsHandy we want to remove from provider.
     */
    public void removeHandy(SmsHandy smsHandy) {
        subscribers.remove(smsHandy);
        credits.remove(smsHandy.getNumber());
    }

    /**
     * Checks if the provider has a smsHandy.
     * @param smsHandy to check.
     * @return true if provider has smsHandy, false if haven't.
     */
    public boolean hasHandy(SmsHandy smsHandy) {
        for (SmsHandy handy: subscribers) {
            if (handy == smsHandy) {
                return true;
            }
        }
        return false;
    }
    public String getName() {
        return name;
    }

    public StringProperty getNameProperty() {
        return nameProperty;
    }

    public static List<Provider> getProviderList() {
        return providerList;
    }
}
