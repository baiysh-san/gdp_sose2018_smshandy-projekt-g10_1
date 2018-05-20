package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
/**
 * 
 * Suite class
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
    ProviderTest.class,
    PrepaidSmsHandyTest.class,
    ProviderTest.class,
    SmsHandyTest.class,
    TariffPlanSmsHandyTest.class
})
public class AllTests {

}
