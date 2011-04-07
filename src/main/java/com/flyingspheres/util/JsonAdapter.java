package com.flyingspheres.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonAdapter {
	public static JSONObject objectTypeToJson(Object instance, Class convertibleClasses, boolean makeKeysLowerCase) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, JSONException, InstantiationException{
		List<Method> getMethods = retrieveConvertableGetMethods(instance);
		List<Method> complexConvertibles = retreiveConvertibleComplexGetMethods(instance, convertibleClasses);
		List<Method> simpleListMethods =retrieveSimpleListMethods(instance);
		List<Method> covertibleListMethods = retrieveConvertibleListMethods(instance, convertibleClasses);
		
		JSONObject returnValue = new JSONObject();
		for (Method getter : getMethods){
			Object value = getter.invoke(instance, new Object[]{});
			returnValue.put(getKey(getter.getName(), makeKeysLowerCase), value);
		}
		if (convertibleClasses != null){
			for (Method complexObject : complexConvertibles){
				Object value = complexObject.invoke(instance, new Object[]{});
				JSONObject jObj = JsonAdapter.objectTypeToJson(value, convertibleClasses, makeKeysLowerCase);
				returnValue.put(getKey(complexObject.getName(), makeKeysLowerCase), jObj);
				
			}
		}
		if (simpleListMethods != null){
			
			for (Method m : simpleListMethods){
				JSONArray simpleJsonArray = new JSONArray();
				List value = (List) m.invoke(instance, null);
				for (Object v : value){
					simpleJsonArray.put(v);					
				}
				returnValue.put(getKey(m.getName(),makeKeysLowerCase), simpleJsonArray);
				
			}
		}
		if (covertibleListMethods.size() > 0){
			
			for (Method m : covertibleListMethods){
				JSONArray simpleJsonArray = new JSONArray();
				Object value = m.invoke(instance, null);
				if (value instanceof List){
					List testList = (List) value;
					if (testList.size() > 0){
						for (Object obj : testList){
							simpleJsonArray.put(JsonAdapter.objectTypeToJson(obj, convertibleClasses, makeKeysLowerCase));
						}
						returnValue.put(getKey(m.getName(),makeKeysLowerCase), simpleJsonArray);
					}
				}
			}
		}
		return returnValue;
	}
	
	private static List<Method> retrieveConvertibleListMethods(Object instance, Class convertibleClasses) {
		Method[] methods = instance.getClass().getMethods();
		ArrayList<Method> methodList = new ArrayList<Method>();
		for (Method m : methods){
			if (m.getName().startsWith("get")){
				try {
					Object value = m.invoke(instance, null);
					if (value instanceof List){
						List testList = (List) value;
						if (testList.size() > 0){
							Object o = testList.get(0);
							 captureMethodsThatReturnTypesOfConcern(convertibleClasses, methodList, m, o);
						}
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		}
		return methodList;
	}

	@SuppressWarnings("unchecked")
	private static List<Method> retrieveSimpleListMethods(Object instance){
		Method[] methods = instance.getClass().getMethods();
		ArrayList<Method> methodList = new ArrayList<Method>();
		for (Method m : methods){
			if (m.getName().startsWith("get")){
				try {
					Object value = m.invoke(instance, null);
					if (value instanceof List){
						List testList = (List) value;
						if (testList.size() > 0){
							Object o = testList.get(0);
							if (o.getClass().isPrimitive() || o instanceof String){
								methodList.add(m);
							}
						}
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		}
		return methodList;
	}

	@SuppressWarnings("unchecked")
	private static List<Method> retreiveConvertibleComplexGetMethods(Object instance, Class convertibleClasses) throws InstantiationException, IllegalAccessException {
 		if (convertibleClasses == null) {
			return null;
		}
		Method[] methods = instance.getClass().getMethods();
		ArrayList<Method> methodList = new ArrayList<Method>();
		
		for (Method method : methods){
			if (method.getName().startsWith("get")){
				Object value = null;
				try {
					value = method.invoke(instance, null);
				} catch (IllegalArgumentException e) {
				//	e.printStackTrace();
				} catch (InvocationTargetException e) {
				//	e.printStackTrace();
				}
				if (value != null){
					if (value instanceof ArrayList){
						 List aList = (ArrayList)value;
						 if (aList.size() > 0){
							 Object obj = aList.get(0);
							 captureMethodsThatReturnTypesOfConcern(convertibleClasses, methodList, method, obj);
						 }
					} else {
						captureMethodsThatReturnTypesOfConcern(convertibleClasses, methodList, method, value);
					}
				}
			}
		}
		return methodList;
	}

	private static void captureMethodsThatReturnTypesOfConcern(Class convertibleClasses, ArrayList<Method> methodList, Method method, Object value) {
		Class[] interfaces = value.getClass().getInterfaces();
		for (Class interfaceType : interfaces){
			if(interfaceType.getSimpleName().equals(convertibleClasses.getSimpleName())){
				methodList.add(method);
				break;
			}
		}
	}

	/**
	 * simple method to take a method named something like getName
	 * and change it to Name or name based on preference
	 * @param name
	 * @return
	 */
	private static String getKey(String name, boolean forceLowerCase) {
		String key = name.substring(3, name.length());
		if (forceLowerCase){
			key = key.toLowerCase();
		}
		return key;
	}

	@SuppressWarnings("unchecked")
	private static List<Method> retrieveConvertableGetMethods(Object instance) {
		Method[] methods = instance.getClass().getMethods();
		ArrayList<Method> methodList = new ArrayList<Method>();
		for (Method method : methods){
			if (method.getName().startsWith("get")){
				Class returnType = method.getReturnType();
				if (goodreturnType(returnType) && method.getParameterTypes().length == 0){
					methodList.add(method);
				}
			}
		}
		return methodList;
	}

	@SuppressWarnings("unchecked")
	private static boolean goodreturnType(Class returnType) {
		boolean isGood = false;
		if (returnType.isPrimitive()){
			isGood = true;
		} else if (returnType.getName().equals("java.lang.String")){
			isGood = true;
		}
		
		return isGood;
	}
}
