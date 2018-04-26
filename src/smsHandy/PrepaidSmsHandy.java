package smsHandy;

public class PrepaidSmsHandy extends SmsHandy {
    private static final int COST_PER_SMS = 10;
    /**
     * Constructor for creating new PrepaidSmsHandy.
     *
     * @param number   the mobile phone number
     * @param provider the provider instance
     */
    public PrepaidSmsHandy(String number, Provider provider) {
        super(number, provider);
    }

    /**
     * Checks whether the credit is still sufficient for sending an SMS.
     *
     * @return is the credit still sufficient?
     */
    @Override
    public boolean canSendSms() {
        return false;
    }

    /**
     * Deduct the cost of an SMS from the cow.
     */
    @Override
    public void payForSms() {

    }

    /**
     * Charges the credit for the SmsHandy object.
     *
     * @param amount Amount to load
     */
    public void dedosit(int amount) {

    }
}
