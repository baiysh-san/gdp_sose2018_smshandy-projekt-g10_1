package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import smsHandy.model.PrepaidSmsHandy;
import smsHandy.model.Provider;
import smsHandy.model.SmsHandy;
import smsHandy.model.TariffPlanSmsHandy;

public class SmsHandyTest {
    private Provider provider1;
    private Provider provider2;
    private SmsHandy smsHandy1;
    private SmsHandy smsHandy2;
    private SmsHandy smsHandy3;
    @Before
    public void setUp() throws Exception {
        provider1 = new Provider("testProvider");
        provider2 = new Provider("testProvider2");
        smsHandy1 = new PrepaidSmsHandy("0312666155", provider1);
        smsHandy2 = new TariffPlanSmsHandy("0312630127", provider2);
        smsHandy3 = new PrepaidSmsHandy("0312293098", provider1);
    }
    @Test
    public void sendSms() throws Exception {
        smsHandy1.sendSms(smsHandy3.getNumber(), "test");
        Assert.assertEquals(smsHandy3.getNumber(), smsHandy1.getSent().get(0).getTo());
        Assert.assertEquals(smsHandy3.getNumber(), smsHandy3.getReceived().get(0).getTo());

        smsHandy1.sendSms(smsHandy2.getNumber(), "test");
        Assert.assertEquals(smsHandy2.getNumber(), smsHandy1.getSent().get(1).getTo());
        Assert.assertEquals(smsHandy2.getNumber(), smsHandy2.getReceived().get(0).getTo());

        smsHandy1.sendSms("*101#", "testo");
        Assert.assertEquals("*101#", smsHandy1.getSent().get(2).getTo());
    }
    @Test
    public void sendSmsDirect() {
        smsHandy2.sendSmsDirect(smsHandy1, "Hallo");
        Assert.assertEquals(smsHandy1.getNumber(), smsHandy2.getSent().get(0).getTo());
        Assert.assertEquals(smsHandy1.getNumber(), smsHandy1.getReceived().get(0).getTo());
    }
}
