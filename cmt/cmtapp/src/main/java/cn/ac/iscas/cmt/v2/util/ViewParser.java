package cn.ac.iscas.cmt.v2.util;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.domain.Page;

import cn.ac.iscas.cloudeploy.v2.puppet.compare.ast.entity.Smell;

public class ViewParser {
	public static String parseJSONString(Object obj){
		return new JSONObject(obj).toString();
	}
	public static String parseJSONString(Map<String,List<Smell>> slist,Map<String,List<String>> llist){
		JSONObject result = new JSONObject();
		JSONObject sresult = new JSONObject();
		for(String ele: slist.keySet()){
			JSONArray jo = new JSONArray(slist.get(ele));
			sresult.put(ele, jo);
		}
		JSONObject lresult = new JSONObject();
		for(String ele: llist.keySet()){
			JSONArray jo = new JSONArray(llist.get(ele));
			lresult.put(ele, jo);
		}
		result.put("check", sresult);
		result.put("lint", lresult);
		return result.toString();
	}
	public static String parseJSONString(Page<?> list){
		JSONObject result = new JSONObject(list);
		return result.toString();
	}
	
}
