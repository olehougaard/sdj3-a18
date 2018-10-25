package dk.via.cars.ws;

import javax.xml.ws.Endpoint;

public class CarServicePublisher {
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8080/ws/car", new CarServiceImpl());
	}
}
