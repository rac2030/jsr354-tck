package org.javamoney.tck;

import java.lang.reflect.AccessibleObject;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmounts;
import javax.money.MonetaryCurrencies;

import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.Money;

public final class TCKTestSetup {

	private static JSRTestSetup TEST_SETUP = new JSRTestSetup();

	private TCKTestSetup() {
	}

	public static JSR354TestConfiguration getTestConfiguration() {
		// TODO load dynamicylly
		return TEST_SETUP;
	}

	private static final class JSRTestSetup implements JSR354TestConfiguration {

		@Override
		public Collection<Class> getExceptionClasses() {
			return Collections.emptySet();
			// return Arrays
			// .asList(new Class[] { CurrencyMismatchException.class,
			// UnknownCurrencyException.class });
		}

		@Override
		public Collection<Class> getAmountClasses() {
			return Arrays
					.asList(new Class[] { Money.class, FastMoney.class });
		}

		@Override
		public Collection<Class> getCurrencyClasses() {
			return Arrays.asList(new Class[] { MonetaryCurrencies.class });
		}

		@Override
		public Collection<Class> getOperators() {
			return Arrays
					.asList(new Class[] {});
		}

		@Override
		public Collection<Class> getQueries() {
			return Arrays
					.asList(new Class[] {});
		}

		@Override
		public AccessibleObject getConstructionMethod(Class type,
				Class... paramTypes) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T create(Class<T> type, Object... params) {
			if (CurrencyUnit.class.equals(type)) {
				return (T) getOrCreateCurrency((String) params[0]);
			}
			if (Money.class.equals(type)) {
				return (T) Money.of(getOrCreateCurrency((String) params[0]),
						(Number) params[1]);
			}
			if (FastMoney.class.equals(type)) {
				return (T) FastMoney.of(
						getOrCreateCurrency((String) params[0]),
						(Number) params[1]);
			}
			return null;
		}

		private CurrencyUnit getOrCreateCurrency(String code) {
			try {
				return MonetaryCurrencies.getCurrency(code);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

}