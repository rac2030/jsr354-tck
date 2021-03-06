package org.javamoney.tck.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Currency;

import javax.money.MonetaryAmount;
import javax.money.MonetaryAmounts;
import javax.money.MonetaryOperator;

import org.javamoney.tck.ClassTester;
import org.javamoney.tck.TCKTestSetup;
import org.jboss.test.audit.annotations.SpecAssertion;
import org.jboss.test.audit.annotations.SpecVersion;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@SpecVersion(spec = "JSR 354", version = "1.0.0")
public class MonetaryAmountTest {

    private final static String DEFAULT_CURRENCY = "CHF";
            
	@SpecAssertion(section = "4.2.2", id = "422-0")
	@Test
	public void testEnsureMonetaryAmount() {
		assertNotNull(MonetaryAmounts.getAmountTypes());
		assertTrue(MonetaryAmounts.getAmountTypes().size() > 0);
	}

	@SpecAssertion(section = "4.2.2", id = "422-A1")
	@Test
	public void testCurrencyCode() {
		for (Class type : MonetaryAmounts.getAmountTypes()) {
			for (Currency jdkCur : Currency.getAvailableCurrencies()) {
				MonetaryAmount amount = MonetaryAmounts.getAmountFactory(type)
						.setCurrency(jdkCur.getCurrencyCode()).setNumber(10.15)
						.create();
				assertNotNull(amount);
				assertNotNull(amount.getCurrency());
				assertEquals(jdkCur.getCurrencyCode(), amount.getCurrency()
						.getCurrencyCode());
			}
		}
	}


	@SpecAssertion(section = "4.2.2", id = "422-E1")
	@Test
	@Ignore
	public void testWith() {
	}

	@SpecAssertion(section = "4.2.2", id = "422-E3")
	@Test
	@Ignore
	public void testQuery() {
		for (Class type : MonetaryAmounts.getAmountTypes()) {
			MonetaryAmount amount = MonetaryAmounts.getAmountFactory(type)
					.setCurrency("XXX").setNumber(0).create();
			// amount.query();
			fail("not implemented.");
			// TODO
		}
	}

	@SpecAssertion(section = "4.2.2", id = "422-F2")
	@Test
	public void testImplementsEquals() {
		for (Class type : MonetaryAmounts.getAmountTypes()) {
			MonetaryAmount amount = MonetaryAmounts.getAmountFactory(type)
					.setCurrency("XXX").setNumber(0).create();
			ClassTester.testHasPublicStaticMethodOpt(type, type, "equals",
					MonetaryOperator.class);
			MonetaryAmount amount2 = MonetaryAmounts.getAmountFactory(type)
					.setCurrency("XXX").setNumber(0).create();
			assertEquals(amount, amount2);
		}
	}

	@SpecAssertion(section = "4.2.2", id = "422-F1")
	@Test
	public void testImplementsHashCode() {
		for (Class type : MonetaryAmounts.getAmountTypes()) {
			MonetaryAmount amount = MonetaryAmounts.getAmountFactory(type)
					.setCurrency("USD").setNumber(0).create();
			ClassTester.testHasPublicStaticMethodOpt(type, type, "hashCode",
					MonetaryOperator.class);
			MonetaryAmount amount2 = MonetaryAmounts.getAmountFactory(type)
					.setCurrency("USD").setNumber(0).create();
			assertEquals(amount.hashCode(), amount2.hashCode());
		}
	}

	@SpecAssertion(section = "4.2.2", id = "422-F3")
	@Test
	public void testImplementComparable() {
		for (Class type : MonetaryAmounts.getAmountTypes()) {
			ClassTester.testComparable(type);
			MonetaryAmount amount = MonetaryAmounts.getAmountFactory(type)
					.setCurrency("XXX").setNumber(0).create();
			ClassTester.testHasPublicStaticMethodOpt(type, type, "hashCode",
					MonetaryOperator.class);
			MonetaryAmount amount2 = MonetaryAmounts.getAmountFactory(type)
					.setCurrency("XXX").setNumber(0).create();
			assertTrue("Comparable failed for: " + type.getName(),
					((Comparable) amount).compareTo(amount2) == 0);
			MonetaryAmount amount3 = MonetaryAmounts.getAmountFactory(type)
					.setCurrency("CHF").setNumber(1).create();
			assertTrue("Comparable failed for: " + type.getName(),
					((Comparable) amount).compareTo(amount3) > 0);
			assertTrue("Comparable failed for: " + type.getName(),
					((Comparable) amount3).compareTo(amount) < 0);
			MonetaryAmount amount4 = MonetaryAmounts.getAmountFactory(type)
					.setCurrency("XXX").setNumber(1).create();
			assertTrue("Comparable failed for: " + type.getName(),
					((Comparable) amount3).compareTo(amount4) < 0);
			assertTrue("Comparable failed for: " + type.getName(),
					((Comparable) amount4).compareTo(amount3) > 0);
		}
	}
    
     @SpecAssertion(section = "4.2.2", id = "422-D1")
    @Test
    public void testAddNumber() {
        for (Class type : MonetaryAmounts.getAmountTypes()) {
            MonetaryAmount mAmount1 = MonetaryAmounts.getAmountFactory(type)
                    .setCurrency(DEFAULT_CURRENCY).setNumber(10).create();
            MonetaryAmount mAmount2 = MonetaryAmounts.getAmountFactory(type)
                    .setCurrency(DEFAULT_CURRENCY).setNumber(20).create();
            MonetaryAmount mActualResult = mAmount1.add(mAmount2);
            MonetaryAmount mExpectedResult = MonetaryAmounts.getAmountFactory(type)
                    .setCurrency(DEFAULT_CURRENCY).setNumber(30).create();
            Assert.assertEquals("Adding two simple ammounts", mExpectedResult, mActualResult);
        }
    }

    @SpecAssertion(section = "4.2.2", id = "422-D1")
    @Test
    public void testAddDouble() {
        for (Class type : MonetaryAmounts.getAmountTypes()) {
            MonetaryAmount mAmount1 = MonetaryAmounts.getAmountFactory(type)
                    .setCurrency(DEFAULT_CURRENCY).setNumber(10d).create();
            MonetaryAmount mAmount2 = MonetaryAmounts.getAmountFactory(type)
                    .setCurrency(DEFAULT_CURRENCY).setNumber(20d).create();
            MonetaryAmount mActualResult = mAmount1.add(mAmount2);
            MonetaryAmount mExpectedResult = MonetaryAmounts.getAmountFactory(type)
                    .setCurrency(DEFAULT_CURRENCY).setNumber(30d).create();
            assertEquals("Adding two simple ammounts", mExpectedResult, mActualResult);
        }
    }

    @SpecAssertion(section = "4.2.2", id = "422-D1")
    @Test
    public void testAddLong() {
        for (Class type : MonetaryAmounts.getAmountTypes()) {
            MonetaryAmount mAmount1 = MonetaryAmounts.getAmountFactory(type)
                    .setCurrency(DEFAULT_CURRENCY).setNumber(10l).create();
            MonetaryAmount mAmount2 = MonetaryAmounts.getAmountFactory(type)
                    .setCurrency(DEFAULT_CURRENCY).setNumber(20l).create();
            MonetaryAmount mActualResult = mAmount1.add(mAmount2);
            MonetaryAmount mExpectedResult = MonetaryAmounts.getAmountFactory(type)
                    .setCurrency(DEFAULT_CURRENCY).setNumber(30l).create();
            Assert.assertEquals("Adding two simple ammounts", mExpectedResult, mActualResult);
        }
    }


}
