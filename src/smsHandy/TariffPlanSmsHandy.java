package smsHandy;

/**
 * Class TariffPlanSmsHandy. A contract mobile phone that has a certain amount of free SMS.
 */
public class TariffPlanSmsHandy extends SmsHandy {
    private int remainingFreeSms = 100;
    /**
     * Constructor to create a new TariffPlanSmsHandy.
     *
     * @param number   the mobile phone number
     * @param provider the provider instance
     */
    public TariffPlanSmsHandy(String number, Provider provider) {
        super(number, provider);
    }

    /**
     * Checks whether free SMS is still sufficient for sending.
     *
     * @return still available free SMS?
     */
    @Override
    public boolean canSendSms() {
        if (remainingFreeSms > 0){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Reduces the free SMS.
     */
    @Override
    public void payForSms() {
        remainingFreeSms--;
    }

    /**
     * Returns the number of remaining free SMS.
     * @return Number of free SMS
     */
    public int getRemainingFreeSms() {
        return remainingFreeSms;
    }
}
