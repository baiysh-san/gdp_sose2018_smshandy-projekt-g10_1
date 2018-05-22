package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import smsHandy.model.PrepaidSmsHandy;
import smsHandy.model.Provider;
import smsHandy.model.SmsHandy;
import smsHandy.model.TariffPlanSmsHandy;

public class SmsHandyTest {

    @Test
    public void sendSms() throws Exception {
        Provider testProvider = new Provider("testProvider");
        Provider testProvider2 = new Provider("testProvider 2");
        SmsHandy senderHandy = new PrepaidSmsHandy("1111", testProvider);
        SmsHandy receiverHandy = new TariffPlanSmsHandy("2222", testProvider2);
        SmsHandy receiverHandy2 = new PrepaidSmsHandy("3333", testProvider);

        senderHandy.sendSms(receiverHandy2.getNumber(), "test1");
        Assert.assertEquals(receiverHandy2.getNumber(), senderHandy.getSent().get(0).getTo());
        Assert.assertEquals(receiverHandy2.getNumber(), receiverHandy2.getReceived().get(0).getTo());

        senderHandy.sendSms(receiverHandy.getNumber(), "test2");
        Assert.assertEquals(receiverHandy.getNumber(), senderHandy.getSent().get(1).getTo());
        Assert.assertEquals(receiverHandy.getNumber(), receiverHandy.getReceived().get(0).getTo());


        senderHandy.sendSms("*101#", "testo");
        Assert.assertEquals("*101#", senderHandy.getSent().get(2).getTo());
    }
    @Test
    public void sendSmsDirect() throws  Exception {
        Provider senderProvider = new Provider("senderProvider");
        Provider receiverProvider = new Provider("receiverProvider");
        SmsHandy senderHandy = new TariffPlanSmsHandy("4444", senderProvider);
        SmsHandy receiverHandy = new PrepaidSmsHandy("5555", receiverProvider);
        senderHandy.sendSmsDirect(receiverHandy, "Hallo");
        Assert.assertEquals(receiverHandy.getNumber(), senderHandy.getSent().get(0).getTo());
        Assert.assertEquals(receiverHandy.getNumber(), receiverHandy.getReceived().get(0).getTo());
    }
    @Test
    public void isNumber() throws Exception{
        Provider provider = new Provider("Provider");
        SmsHandy handy = new PrepaidSmsHandy("1233", provider);
        Assert.assertTrue(handy.isNumber("1234"));
        Assert.assertTrue(handy.isNumber("+*100#"));
    }
    @Test(expected = Exception.class)
    public void isNumberExceptionTest() throws Exception {
        Provider provider = new Provider("Provider");
        SmsHandy handy = new PrepaidSmsHandy("1233", provider);
        handy.isNumber("AAA");
        handy.isNumber("");
        
    }
}
