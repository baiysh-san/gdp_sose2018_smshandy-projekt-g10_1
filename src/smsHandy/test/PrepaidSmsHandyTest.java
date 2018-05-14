package smsHandy.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import smsHandy.model.PrepaidSmsHandy;
import smsHandy.model.Provider;
import smsHandy.model.TariffPlanSmsHandy;

public class PrepaidSmsHandyTest {
    private PrepaidSmsHandy prepaidSmsHandy;
    private TariffPlanSmsHandy tariffPlanSmsHandy;
    private Provider provider;
    private final int COST_PER_SMS = 10;
    private int remainingSms;
    private int creditOfHandy;
    @Before
    public void setUp() throws Exception {
        provider = new Provider("nettoCOM");
        prepaidSmsHandy = new PrepaidSmsHandy("996312", provider);
        tariffPlanSmsHandy = new TariffPlanSmsHandy("996555", provider);
        creditOfHandy = prepaidSmsHandy.getProvider().getCreditForSmsHandy(prepaidSmsHandy.getNumber());
        remainingSms = creditOfHandy/COST_PER_SMS;
    }

    @Test
    public void сanSendSms() throws Exception {
        Assert.assertTrue(prepaidSmsHandy.canSendSms());
        for (int i = 0; i<remainingSms; i++) {
            prepaidSmsHandy.sendSms(tariffPlanSmsHandy.getNumber(), "Test");
        }
        Assert.assertFalse(prepaidSmsHandy.canSendSms());
    }

    @Test
    public void testPayForSms() {
        Assert.assertEquals(creditOfHandy, provider.getCreditForSmsHandy(prepaidSmsHandy.getNumber()));
        prepaidSmsHandy.payForSms();
        Assert.assertNotEquals(creditOfHandy, provider.getCreditForSmsHandy(prepaidSmsHandy.getNumber()));
        Assert.assertEquals(creditOfHandy - COST_PER_SMS, provider.getCreditForSmsHandy(prepaidSmsHandy.getNumber()));
    }

    @Test
    public void testDeposit() throws Exception {
        Assert.assertEquals(creditOfHandy, provider.getCreditForSmsHandy(prepaidSmsHandy.getNumber()));
        prepaidSmsHandy.deposit(100);
        Assert.assertNotEquals(creditOfHandy, provider.getCreditForSmsHandy(prepaidSmsHandy.getNumber()));
        Assert.assertEquals(creditOfHandy + 100, provider.getCreditForSmsHandy(prepaidSmsHandy.getNumber()));
    }

}
