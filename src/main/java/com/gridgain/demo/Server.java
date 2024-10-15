package com.gridgain.demo;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

public class Server implements AutoCloseable {
	private Ignite ignite;

	public static void main(String[] args) {
		new Server();
	}

	public Server() {
		AppConfiguration cfg = new AppConfiguration();

		ignite = Ignition.start(cfg);
	}

	@Override
	public void close() throws Exception {
		ignite.close();
	}

}
