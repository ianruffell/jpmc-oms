package com.gridgain.demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.pool.OracleDataSource;

public class OracleTest {

	public static void main(String[] args) throws SQLException {
		OracleDataSource dataSrc = new OracleDataSource();
		dataSrc.setURL("jdbc:oracle:thin:@localhost:1521:FREE");
		dataSrc.setUser("system");
		dataSrc.setPassword("oracle");
		Connection connection = dataSrc.getConnection();
		Statement statement = connection.createStatement();
		statement.execute("select * from CO.customers");
		ResultSet resultSet = statement.getResultSet();
		while (resultSet.next()) {
			System.out.printf("id=%d, email=%s, name=%s\n", resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
		}
	}

}
