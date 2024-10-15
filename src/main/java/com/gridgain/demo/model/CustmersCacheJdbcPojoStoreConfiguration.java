package com.gridgain.demo.model;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.cache.configuration.Factory;
import javax.sql.DataSource;

import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.QueryEntity;
import org.apache.ignite.cache.store.jdbc.CacheJdbcPojoStoreFactory;
import org.apache.ignite.cache.store.jdbc.JdbcType;
import org.apache.ignite.cache.store.jdbc.JdbcTypeField;
import org.apache.ignite.cache.store.jdbc.dialect.OracleDialect;
import org.apache.ignite.configuration.CacheConfiguration;

import oracle.jdbc.pool.OracleDataSource;

public class CustmersCacheJdbcPojoStoreConfiguration<K, V> extends CacheConfiguration<Integer, Customers> {

	private static final long serialVersionUID = 1L;
	
	public CustmersCacheJdbcPojoStoreConfiguration() {
		super();

		setName("CUSTOMERS");
        setSqlSchema("PUBLIC");
		setCacheMode(CacheMode.PARTITIONED);
		setAtomicityMode(CacheAtomicityMode.ATOMIC);
		setBackups(1);

		setReadThrough(true);
		setWriteThrough(true);
		setWriteBehindEnabled(true);
		// Use these to tune write-through
		setWriteBehindBatchSize(1000);
		setWriteBehindFlushFrequency(1000);
		setWriteBehindFlushSize(1000);
		setWriteBehindFlushThreadCount(16);

		CacheJdbcPojoStoreFactory<Integer, Customers> factory = new CacheJdbcPojoStoreFactory<>();
		factory.setDialect(new OracleDialect());
		factory.setDataSourceFactory((Factory<DataSource>) () -> {
			OracleDataSource dataSrc = null;
			try {
				dataSrc = new OracleDataSource();
				dataSrc.setURL("jdbc:oracle:thin:@localhost:1521:FREE");
				dataSrc.setUser("system");
				dataSrc.setPassword("oracle");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return dataSrc;
		});
		JdbcType customersType = new JdbcType();
		customersType.setCacheName("CUSTOMERS");
		customersType.setKeyType(Integer.class);
		customersType.setValueType(Customers.class);
		customersType.setDatabaseSchema("CO");
		customersType.setDatabaseTable("CUSTOMERS");

		customersType.setKeyFields(new JdbcTypeField(java.sql.Types.INTEGER, "customer_id", Integer.class, "customer_id"));

		customersType.setValueFields(new JdbcTypeField(java.sql.Types.VARCHAR, "email_address", String.class, "email_address"),
		                             new JdbcTypeField(java.sql.Types.VARCHAR, "full_name", String.class, "full_name"));

		factory.setTypes(customersType);

		setCacheStoreFactory(factory);

		QueryEntity qryEntity = new QueryEntity();

		qryEntity.setKeyType(Integer.class.getName());
		qryEntity.setValueType(Customers.class.getName());
		qryEntity.setKeyFieldName("customer_id");
		qryEntity.addQueryField("customer_id", String.class.getName(), null);
		qryEntity.addQueryField("email_address", String.class.getName(), null);
		qryEntity.addQueryField("fullName", String.class.getName(), null);

		Set<String> keyFields = new HashSet<>();
		keyFields.add("customer_id");
		qryEntity.setKeyFields(keyFields);

		LinkedHashMap<String, String> fields = new LinkedHashMap<>();
		fields.put("customer_id", "java.lang.Integer");
		fields.put("email_address", "java.lang.String");
		fields.put("full_name", "java.lang.String");

		qryEntity.setFields(fields);

		setQueryEntities(Collections.singletonList(qryEntity));
	}

}
