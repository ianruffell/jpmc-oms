package com.gridgain.demo;

import static org.apache.ignite.configuration.DeploymentMode.CONTINUOUS;

import java.util.ArrayList;

import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;

public class AppConfiguration extends IgniteConfiguration {

	public AppConfiguration() {
		super();
		
		System.setProperty("IGNITE_QUIET", "true");
		System.setProperty("java.net.preferIPv4Stack", "true");

		setPeerClassLoadingEnabled(true);
		setDeploymentMode(CONTINUOUS);
		setWorkDirectory("/tmp/gridgain/work");

		TcpDiscoverySpi tcpDiscoverySpi = new org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi();
		TcpDiscoveryVmIpFinder tcpDiscoveryVmIpFinder = new org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder();
		ArrayList<String> list = new ArrayList<String>();
		list.add("127.0.0.1:47500..47510");

		tcpDiscoveryVmIpFinder.setAddresses(list);
		tcpDiscoverySpi.setIpFinder(tcpDiscoveryVmIpFinder);

		setDiscoverySpi(tcpDiscoverySpi);
	}

}
