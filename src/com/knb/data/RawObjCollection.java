package com.knb.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.res.Resources;
import android.util.Log;

public class RawObjCollection {
//
//	private static HashMap<String, StringBuffer> collection = new HashMap<String, StringBuffer>();
//
//	private static List<String> resIDList = new ArrayList<String>();
//
//	static {
//		resIDList.add("com.knb:raw/ball_final_obj");
//		resIDList.add("com.knb:raw/ball_final_mtl");
//		resIDList.add("com.knb:raw/cut_ball1_obj");
//		resIDList.add("com.knb:raw/cut_ball1_mtl");
//		resIDList.add("com.knb:raw/katana_sword_obj");
//		resIDList.add("com.knb:raw/katana_sword_mtl");
//		resIDList.add("com.knb:raw/background_obj");
//		resIDList.add("com.knb:raw/background_mtl");
//	}
//
//	public static void loadAllData(Resources resources) throws IOException {
//
//		for (String resID : resIDList) {
//			InputStream fileIn = resources.openRawResource(resources
//					.getIdentifier(resID, null, null));
//			BufferedReader buffer = new BufferedReader(new InputStreamReader(
//					fileIn));
//			String line = buffer.readLine();
//
//			StringBuffer sBuff = new StringBuffer();
//
//			while (line != null) {
//				// Log.i("LoadObj","anurag got line "+line);
//				sBuff.append(line);
//				sBuff.append("\n");
//				line = buffer.readLine();
//			}
//
//			collection.put(resID, sBuff);
//
//		}
//
//	}
//
//	public static StringReader getStringReader(String resID) {
//		Log.i("anurag", "getting resID " + resID);
//		return new StringReader(collection.get(resID).toString());
//
//	}

}
