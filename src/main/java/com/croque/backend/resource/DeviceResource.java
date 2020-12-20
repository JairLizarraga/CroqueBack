package com.croque.backend.resource;

import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.croque.backend.model.Device;
import com.croque.backend.service.DeviceService;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
public class DeviceResource {
	
    @Autowired
    private DeviceService deviceManagementService;
	
    @GetMapping("/users/{username}/devices")
	public List<Device> getAllDevices() {
	    return deviceManagementService.getDevices();
	}
    
    @GetMapping("/users/{username}/devices/{id}/connect")
	public void connectDevice(@PathVariable String username, @PathVariable int id) throws MqttException {
	    deviceManagementService.findById(id).connect();
	}

    @GetMapping("/users/{username}/devices/{id}/led/on")
    public void on() throws MqttPersistenceException,  MqttException {
    	String msg = "{'led_status': 'on'}";
    	Device device = deviceManagementService.findById(0);
        String topic_pub_setLed		= "iot-2/evt/set_led/fmt/json"; // Pub Topic
	    device.publish(topic_pub_setLed, new JSONObject(msg));
    }

    @GetMapping("/users/{username}/devices/{id}/led/off")
	public void off() throws MqttPersistenceException, MqttException {
    	String msg = "{'led_status': 'off'}";
    	Device device = deviceManagementService.findById(0);
        String topic_pub_setLed		= "iot-2/evt/set_led/fmt/json"; // Pub Topic
	    device.publish(topic_pub_setLed, new JSONObject(msg));
	}
    
    @GetMapping("/users/{username}/devices/{id}/led/{state}")
	public void setLed(@PathVariable String username, @PathVariable int id, @PathVariable int state) throws MqttPersistenceException, MqttException {
    	String msg = (state == 0)? "{'led_status': 'off'}" : "{'led_status': 'on'}";
    	Device device = deviceManagementService.findById(id);
        String topic_pub_setLed		= "iot-2/evt/set_led/fmt/json"; // Pub Topic
	    device.publish(topic_pub_setLed, new JSONObject(msg));
	}

    @GetMapping("/users/{username}/devices/{id}/subscribe")
	public Device sub(@PathVariable String username, @PathVariable int id) throws MqttException{
		Device device = deviceManagementService.findById(id);
		device.subscribe();
		return device;
	}

    @GetMapping("/users/{username}/devices/{id}/unsibscribe")
	public Device unsub(@PathVariable String username, @PathVariable int id) throws MqttException{
		Device device = deviceManagementService.findById(id);
		device.unsubscribe();
		return device;
	}

}
