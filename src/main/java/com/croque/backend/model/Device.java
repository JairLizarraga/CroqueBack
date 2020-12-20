package com.croque.backend.model;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;

public class Device implements MqttCallback{
	private int id;
	private MqttClient client;
	private MqttConnectOptions connOpts = new MqttConnectOptions();
    private String broker;
    private String clientId;
    
	private String topic_sub_statLed = "iot-2/cmd/stat_led/fmt/json";	// Sub Topic
    private String topic_pub_setLed		= "iot-2/evt/set_led/fmt/json"; // Pub Topic
    
    public Device() {
    	
    }
    
    public Device(String username, String token, String broker, String clientId) {
		this.broker = broker;
		this.clientId = clientId;
        connOpts.setUserName(username);
        connOpts.setPassword(token.toCharArray());
        connOpts.setCleanSession(true);

	}
    
    public void connect() throws MqttException {    	
    	this.client = new MqttClient(broker, clientId, new MemoryPersistence() );
        this.client.setCallback(this);
        this.client.connect(connOpts);
        System.out.println("Connected to broker " + broker);
    }

	@Override
	public void connectionLost(Throwable cause) {
        System.out.println("Connection lost because: " + cause);
        System.exit(1);
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(String.format("[%s] %s", topic, new String(message.getPayload())));
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
	}
    
	public void disconnect() throws MqttException {
		client.disconnect();
	}
	
	public void publish(String topic, JSONObject msg) throws MqttPersistenceException, MqttException {
	    int qos = 0;
	    
        System.out.println("Publishing message: " + msg.toString());
        MqttMessage message = new MqttMessage(msg.toString().getBytes());
        message.setQos(qos);
		client.publish(topic, message);
        System.out.println("Message published");
	}
	
	public void subscribe() throws MqttException {
		client.subscribe(topic_sub_statLed);
	}

	public void unsubscribe() throws MqttException {
		client.unsubscribe(topic_sub_statLed);
	}

	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MqttClient getClient() {
		return client;
	}

	public void setClient(MqttClient client) {
		this.client = client;
	}

	public MqttConnectOptions getConnOpts() {
		return connOpts;
	}

	public void setConnOpts(MqttConnectOptions connOpts) {
		this.connOpts = connOpts;
	}

	public String getBroker() {
		return broker;
	}

	public void setBroker(String broker) {
		this.broker = broker;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getTopic_sub_statLed() {
		return topic_sub_statLed;
	}

	public void setTopic_sub_statLed(String topic_sub_statLed) {
		this.topic_sub_statLed = topic_sub_statLed;
	}

	public String getTopic_pub_setLed() {
		return topic_pub_setLed;
	}

	public void setTopic_pub_setLed(String topic_pub_setLed) {
		this.topic_pub_setLed = topic_pub_setLed;
	}
	


}
