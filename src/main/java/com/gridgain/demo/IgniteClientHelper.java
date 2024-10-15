package com.gridgain.demo;

import static org.apache.ignite.cluster.ClusterState.ACTIVE;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;

import com.gridgain.demo.model.CustmersCacheJdbcPojoStoreConfiguration;
import com.gridgain.demo.model.Customers;

public class IgniteClientHelper implements AutoCloseable {

	public static final String SQL_SCHEMA = "PUBLIC";

	public static final String CUSTOMER_CACHE_NAME = "CUSTOMERS";

	private final Ignite ignite;
	private final IgniteCache<Integer, Customers> customersCache;

	public static IgniteClientHelper start() throws Exception {
		return new IgniteClientHelper(true);
	}

	public IgniteClientHelper(boolean destroyCaches) throws Exception {
		System.setProperty("IGNITE_QUIET", "true");
		System.setProperty("java.net.preferIPv4Stack", "true");

		IgniteConfiguration cfg = new AppConfiguration();
		cfg.setClientMode(true);

		ignite = Ignition.start(cfg);
		ignite.cluster().state(ACTIVE);
		ignite.cluster().tag("Demo Cluster");

		if (destroyCaches) {
			System.out.println("Deleting Caches...");
			ignite.destroyCache(CUSTOMER_CACHE_NAME);
		}
		
		System.out.println("Creating Caches...");
		customersCache = ignite.getOrCreateCache(new CustmersCacheJdbcPojoStoreConfiguration<Integer, Customers>());
	}

	public Ignite getIgnite() {
		return ignite;
	}

	@Override
	public void close() throws Exception {
		ignite.close();
	}

	public IgniteCache<Integer, Customers> getCustomersCache() {
		return customersCache;
	}
}
