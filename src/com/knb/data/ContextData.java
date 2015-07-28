package com.knb.data;

import java.util.concurrent.ConcurrentHashMap;

public class ContextData {

	private static ConcurrentHashMap<String, String> context = new ConcurrentHashMap<String, String>();

	private static volatile boolean areObjLoaded;
	
	public static String getValue(String key) {
		return context.get(key);
	}

	public static void setValue(String key, String value) {
		context.put(key, value);
	}

	public static void loadAllObjs(){
		areObjLoaded = true;
	}
	
	public static boolean areObjsLoaded(){
		return areObjLoaded;
	}
}
