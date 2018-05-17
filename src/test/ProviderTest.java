package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import smsHandy.model.*;

import java.util.Date;

public class ProviderTest {
    private Provider provider1;
    private Provider provider2;
    private SmsHandy senderHandy;
    private SmsHandy receiverHandy;
    @Before
    public void setUp() throws Exception {
        provider1 = new Provider("testProvider");
        provider2 = new Provider("testProvider2");
        senderHandy = new TariffPlanSmsHandy("031233211", provider1);
        receiverHandy = new PrepaidSmsHandy("0312777888", provider2);
    }

    @Test
    public void send() throws Exception {
        Message message = new Message("Hallo", receiverHandy.getNumber(), senderHandy.getNumber(), new Date());
        provider1.send(message);
        Assert.assertTrue(senderHandy.getSent().isEmpty());
        Assert.assertEquals(receiverHandy.getNumber(), receiverHandy.getReceived().get(0).getTo());

        Message statusMessage = new Message("Status", "*101#", senderHandy.getNumber(), new Date());
        provider1.send(statusMessage);
        Assert.assertEquals("Your credits: " + provider1.getCreditForSmsHandy(senderHandy.getNumber()),
                senderHandy.getReceived().get(0).getContent());

    }

    @Test(expected = Exception.class)
    public void sendExceptionTest() throws Exception {
        Message message = new Message("Hallo", "3254354313", senderHandy.getNumber(), new Date());
        provider1.send(message);
    }

    @Test(expected = Exception.class)
    public void registerExceptionTest() throws Exception {
        provider1.register(senderHandy);
    }

    @Test
    public void deposit() throws Exception {
        int depositAmountPositive = 20;
        int depositAmountNegative = -33;
        Assert.assertEquals(100, provider1.getCreditForSmsHandy(senderHandy.getNumber()));
        provider1.deposit(senderHandy.getNumber(), depositAmountPositive);
        Assert.assertEquals(100 + depositAmountPositive, provider1.getCreditForSmsHandy(senderHandy.getNumber()));
        provider1.deposit(senderHandy.getNumber(), depositAmountNegative);
        Assert.assertEquals(100 + depositAmountNegative + depositAmountPositive,
                provider1.getCreditForSmsHandy(senderHandy.getNumber()));
    }

    @Test(expected = Exception.class)
    public void depositExceptionTest() throws Exception {
        provider1.deposit(receiverHandy.getNumber(), 20);
    }
 }