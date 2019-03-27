package com.ge.model;

import java.util.HashMap;
import java.util.List;

/**
 * @author Akash T
 * 		   This class is used to store the input file in the form of hash
 *         map
 *
 */
public class InputModel {

	private HashMap<String, List<Object>> mapOfAddressesAndLinks;

	public HashMap<String, List<Object>> getMapOfAddressesAndLinks() {
		return mapOfAddressesAndLinks;
	}

	public void setMapOfAddressesAndLinks(HashMap<String, List<Object>> mapOfAddressesAndLinks) {
		this.mapOfAddressesAndLinks = mapOfAddressesAndLinks;
	}

	@Override
	public String toString() {
		return "InputModel [mapOfAddressesAndLinks=" + mapOfAddressesAndLinks + "]";
	}

}
