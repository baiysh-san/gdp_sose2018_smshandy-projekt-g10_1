package smsHandy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 
 * 
 *
 */
public class Provider {
    private Map<String, Integer> credits;
    private List<SmsHandy> subscribers;
    private List<Provider> providers;
    
    public Provider() {
        credits = new HashMap<>();
        subscribers = new ArrayList<>();
        providers = new ArrayList<>();
    }
    
    /**
     * Sends the SMS to the recipient if the recipient is known.
     * @param message
     * @return
     */
    public boolean send(Message message) {
        return true;
    }
    
    /**
     * Register a new "SmsHandy" with this provider.
     * @param smsHandy
     */
    public void register(SmsHandy smsHandy) {
        subscribers.add(smsHandy);
        if(credits.containsKey(smsHandy.getNumber())) {
            //TODO Exception
            System.out.println("This phone is already registered!");
        }else {
            credits.put(smsHandy.getNumber(), 0);
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
            credits.put(number, credits.get(number)+amount);
        }else {
            System.out.println("This phone number is not subscriber!");
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
    private boolean canSendTo(String number) {
        if(credits.containsKey(number)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 
     * @param number
     * @return
     */
    //private Provider findProviderFor(String number) {
     //   providers.
    //}
}
