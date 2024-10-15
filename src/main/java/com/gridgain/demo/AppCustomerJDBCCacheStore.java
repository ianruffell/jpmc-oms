package com.gridgain.demo;

import java.util.Iterator;
import java.util.List;

import javax.cache.integration.CompletionListener;

import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;

import com.github.javafaker.Faker;
import com.gridgain.demo.model.Customers;

public class AppCustomerJDBCCacheStore implements CompletionListener {

	private IgniteClientHelper ich;

	public static void main(String[] args) throws Exception {
		new AppCustomerJDBCCacheStore();
	}

	public AppCustomerJDBCCacheStore() throws Exception {
		ich = new IgniteClientHelper(true);
		IgniteCache<Integer, Customers> cache = ich.getCustomersCache();
		
		cache.loadCache(null);
		
		Customers customers = cache.get(1);
		System.out.println(customers.toString());
		
		FieldsQueryCursor<List<?>> query = cache.query(new SqlFieldsQuery("select * from CUSTOMERS where customer_id in (10, 11, 12, 13, 14, 15)"));
		Iterator<List<?>> iterator = query.iterator();
		while (iterator.hasNext()) {
			List<?> row = iterator.next();
			System.out.printf("id=%d, email=%s, name=%s\n", row.get(0), row.get(1), row.get(2));
		}

		String fn = Faker.instance().name().firstName();
		String ln = Faker.instance().name().lastName();
		String cn = "acme";

		Customers c = new Customers(1000, fn + "." + ln + "@" + cn + ".com", fn + " " + ln);
		cache.put(c.getCustomerId(), c);
	}

	@Override
	public void onCompletion() {
		System.out.println("Customers load complete");
		Customers customers = ich.getCustomersCache().get(5);
		System.out.println(customers.toString());
	}

	@Override
	public void onException(Exception e) {
		System.err.println(e.getMessage());
		e.printStackTrace();
	}
}
