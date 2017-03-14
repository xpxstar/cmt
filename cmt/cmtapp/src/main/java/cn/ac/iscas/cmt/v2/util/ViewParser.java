package cn.ac.iscas.cmt.v2.util;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewParser {
	public static String parseJSONString(Object obj){
		return new JSONObject(obj).toString();
	}
	public static String parseJSONString(Iterable<?> list){
		JSONArray result = new JSONArray();
		for (Object obj : list) {
			result.put(new JSONObject(obj));
		}
		return result.toString();
	}
	
}
