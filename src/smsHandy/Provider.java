package smsHandy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * TODO 
 * 
 *
 */
public class Provider {
    private Map<String, Integer> credits;
    private List<SmsHandy> subscribers; //TODO Change to Set!!!
    private static List<Provider> providerList = new ArrayList<>();
    
    public Provider() {
        credits = new HashMap<>();
        subscribers = new ArrayList<>();
        providerList.add(this);
    }
    
    /**
     * Sends the SMS to the recipient if the recipient is known.
     * @param message
     * @return
     */
    public boolean send(Message message) {
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
        if(canSendTo(to)) {
            for (SmsHandy smsHandy : subscribers) {
                smsHandy.receiveSms(message);
            }
            return true;
        } else {
            Provider provider = findProviderFor(to);
            if(provider!=null) {
                provider.send(message);
                return true;
            } else {
                return false; //TODO Exceptions
            }
        }
        
        
    }
    
    
    /**
     * Register a new "SmsHandy" with this provider.
     * @param smsHandy
     */
    public void register(SmsHandy smsHandy) {
        if(credits.containsKey(smsHandy.getNumber())) {
            //TODO Exception
            System.out.println("This phone is already registered!");
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
    public void deposit(String number, int amount) {
        if(canSendTo(number)) {
            credits.put(number, credits.get(number) + amount);
        } else {
            System.out.println("This phone number is not subscriber!");//TODO exception
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
     * @param number
     * @return
     */
    private boolean canSendTo(String receiver) {
        for (SmsHandy smsHandy : subscribers) {
            if(smsHandy.getNumber().equals(receiver)){
                return true;
            } 
        }
        return false;
    }
    
    /**
     * 
     * @param number
     * @return
     */
    private Provider findProviderFor(String receiver) {
       for (Provider provider : providerList) {
           if(provider.canSendTo(receiver)) {
               return provider;
           } 
       }
       return null;
    }
}
