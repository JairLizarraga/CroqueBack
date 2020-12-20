package com.croque.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.croque.backend.model.Device;

@Service
public class DeviceService {
	private static List<Device> devices = new ArrayList<Device>();
	private int idCounter = 0;
	
	public DeviceService() {
	}
	
	static {
	    String broker       = "tcp://31pdmc.messaging.internetofthings.ibmcloud.com:1883";
	    String clientId     = "d:31pdmc:JavaProgram:JavaClient";
	    String token 	= "password";
	    String username 	= "use-token-auth";
		devices.add(new Device(username, token, broker, clientId));
	}

	
	public Device findById(int id) {
		for (Device device: devices) {
			if(device.getId() == id) {
				return device;
			}
		}
		return null;
	}
	
	public Device save(Device device) {	  
		devices.add(device);
		idCounter++;
	  return device;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public int getIdCounter() {
		return idCounter;
	}

	public void setIdCounter(int idCounter) {
		this.idCounter = idCounter;
	}
}