package com.gridgain.demo.model;

import java.io.Serializable;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

public class Customers implements Serializable {
    private static final long serialVersionUID = 0L;

	@QuerySqlField(index = true)
	Integer customer_id;
	@QuerySqlField
	String email_address;
	@QuerySqlField
	String full_name;

	public Customers(Integer customer_id, String email_address, String full_name) {
		this.customer_id = customer_id;
		this.email_address = email_address;
		this.full_name = full_name;
	}

	public Customers() {
	}

	public Integer getCustomerId() {
		return customer_id;
	}

	public void setCustomerId(Integer customer_id) {
		this.customer_id = customer_id;
	}

	public String getEmailAddress() {
		return email_address;
	}

	public void setEmailAddress(String emailAddress) {
		this.email_address = email_address;
	}

	public String getFullName() {
		return full_name;
	}

	public void setFullName(String full_name) {
		this.full_name = full_name;
	}

	@Override
	public String toString() {
		return "Customers [customer_id=" + customer_id + ", email_address=" + email_address + ", full_name=" + full_name
				+ "]";
	}
}