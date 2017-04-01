package cn.ac.iscas.cmt.v2.util;

import org.json.JSONObject;
import org.springframework.data.domain.Page;

public class ViewParser {
	public static String parseJSONString(Object obj){
		return new JSONObject(obj).toString();
	}
	public static String parseJSONString(Page<?> list){
		JSONObject result = new JSONObject(list);
		return result.toString();
	}
	
}
