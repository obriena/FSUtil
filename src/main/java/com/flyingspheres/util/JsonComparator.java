package com.flyingspheres.util;

import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonComparator implements Comparator<JSONObject>{

	/**
	 * compare two json objects for equality
	 * equal means keys match exactly as well as values
	 * order does not matter
	 */
	public int compare(JSONObject one, JSONObject two) {
		int returnCode = -1;
		String[] keysOne = JSONObject.getNames(one);
		String[] keysTwo = JSONObject.getNames(two);
		
		boolean keysMatch = compareKeys(returnCode, keysOne, keysTwo);
		boolean valuesMatch = false;
		if (keysMatch){
			valuesMatch = compareValues(keysOne, one, two);
		}
		if (keysMatch && valuesMatch){
			returnCode = 1;
		}
		
		return returnCode;
	}

	/**
	 * An assumption is made that the keys do match in name and how many keys in each object
	 * @param keys
	 * @param one
	 * @param two
	 * @return
	 */
	private boolean compareValues(String[] keys, JSONObject one, JSONObject two) {
		boolean matchingObjects = true;
		if (keys != null){
			for (String key : keys){
				Object valueOne = one.opt(key);
				Object valueTwo = two.opt(key);
				boolean matchingValue =  compareValue(valueOne, valueTwo);
				if (!matchingValue){
					matchingObjects = false;
					break;
				}
			}
		}
		return matchingObjects;
	}

	private boolean compareValue(Object valueOne, Object valueTwo) {
		boolean matchingValue = false;
		if (valueOne instanceof JSONObject){
			int match = compare((JSONObject)valueOne, (JSONObject)valueTwo);
			if (match == 1) {
				matchingValue = true;
			}
		} else if ( valueOne instanceof JSONArray && valueTwo instanceof JSONArray){
			matchingValue = compareJsonArray((JSONArray)valueOne, (JSONArray)valueTwo);
		} else {
			matchingValue = valueOne.equals(valueTwo);
		}
		return matchingValue;
	}

	private boolean compareJsonArray(JSONArray jArrayOne, JSONArray jArrayTwo) {
		boolean matchingValue = false;
		//arrays have the same number
		if (jArrayOne.length() == jArrayTwo.length()){
			try {
				boolean oddMatch = true;
				for (int a = 0; a < jArrayOne.length(); a++){
					Object o1 = jArrayOne.get(a);
					Object o2 = jArrayTwo.get(a);
					boolean matched = compareValue(o1, o2);
					if (!matched){
						oddMatch= false;
						break;
					}
				}
				if (oddMatch){
					matchingValue = true;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return matchingValue;
	}

	private boolean compareKeys(int returnCode, String[] keysOne, String[] keysTwo) {
		boolean matchingKeys = false;
		if (keysOne == null && keysTwo == null)
		{
			matchingKeys = true;
		} 
		else if (null == keysOne)
		{
			matchingKeys = false;
		} 
		else if (null == keysTwo)
		{
			matchingKeys = false;
		} 
		else if (keysOne.length == keysTwo.length)
		{
			matchingKeys = true;
		} 
		else {
			matchingKeys = false;
		}
		
		//if our keys match and the keys aren't null
		if (matchingKeys && (keysOne != null)){	
			for (String key : keysOne){
				boolean found = false;
				for (String key2 :keysTwo){
					if (key.equals(key2)){
						found = true;
						break;
					}
				}
				if (!found){
					returnCode = -1;
					break;
				}
			}
		}
		return matchingKeys;
	}

}
