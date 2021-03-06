package smsHandy.model;

/**
 * Class PrepaidSmsHandy.
 */
public class PrepaidSmsHandy extends SmsHandy {
    private static final int COST_PER_SMS = 10;
    /**
     * Constructor for creating new PrepaidSmsHandy.
     *
     * @param number   the mobile phone number
     * @param provider the provider instance
     */
    public PrepaidSmsHandy(String number, Provider provider) throws Exception {
        super(number, provider);
    }

    /**
     * Checks whether the credit is still sufficient for sending an SMS.
     *
     * @return is the credit still sufficient?
     */
    @Override
    public boolean canSendSms() {
        if (getProvider().getCreditForSmsHandy(getNumber()) >= COST_PER_SMS) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Deduct the cost of an SMS from the cow.
     */
    @Override
    public void payForSms() {
        try {
            getProvider().deposit(getNumber(), -COST_PER_SMS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Charges the credit for the SmsHandy object.
     *
     * @param amount Amount to load
     */
    public void deposit(int amount) throws Exception {
        if (amount > 0) {
            getProvider().deposit(getNumber(), amount);
        } else {
            throw new Exception("Amount must be greater than 0");
        }
    }
}
