package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import smsHandy.model.PrepaidSmsHandy;
import smsHandy.model.Provider;
import smsHandy.model.SmsHandy;
import smsHandy.model.TariffPlanSmsHandy;

public class TariffPlanSmsHandyTest {
    private TariffPlanSmsHandy tariffPlanSmsHandy;
    private SmsHandy smsHandy;
    private Provider provider;
    private int remainingSms;
    @Before
    public void setUp() throws Exception {
        provider = new Provider("testProvider");
        tariffPlanSmsHandy = new TariffPlanSmsHandy("0312666155", provider);
        smsHandy = new PrepaidSmsHandy("434321", provider);
        remainingSms = tariffPlanSmsHandy.getRemainingFreeSms();
    }

    @Test
    public void canSendSms() throws Exception {
        Assert.assertTrue(tariffPlanSmsHandy.canSendSms());
        for (int i = 0; i < remainingSms; i++) {
            tariffPlanSmsHandy.sendSms(smsHandy.getNumber(), "Hi");
        }
        Assert.assertFalse(tariffPlanSmsHandy.canSendSms());
    }

    @Test
    public void payForSms() {
        Assert.assertEquals(remainingSms, tariffPlanSmsHandy.getRemainingFreeSms());
        tariffPlanSmsHandy.payForSms();
        Assert.assertEquals(--remainingSms, tariffPlanSmsHandy.getRemainingFreeSms());
    }
}