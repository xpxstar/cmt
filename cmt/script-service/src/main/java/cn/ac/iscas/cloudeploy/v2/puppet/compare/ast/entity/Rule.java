package cn.ac.iscas.cloudeploy.v2.puppet.compare.ast.entity;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Rule {
	/**
	 * 约束的语法块种类，如hostclass,function等等
	 */
	@Setter @Getter private String syntax;
	/**
	 * 约束的位置 如file,service,functionname等等
	 * 
	 */
	@Setter @Getter private String position;
	
	/**
	 * 约束的属性如argument，title，path，enable等等
	 */
	@Setter @Getter private String attribute;
	/**
	 * 替代属性的改变，如改变前为true，改变后为false
	 */
	@Setter @Getter private String msg;
	/**
	 *约束内容，正则表达式
	 */
	@Setter @Getter private String rule;
	
	/**
	 * 错误提醒的级别，ERROR，WARNING，
	 */
	@Setter @Getter private String level;
	
	public String genKey(){
		return  syntax+"-"+position+"-"+attribute;
	}
	public Rule parseRule(String json){
		JSONObject js = new JSONObject(json);
		Rule result = new Rule();
		result.setSyntax(js.getString("syntax"));
		result.setPosition(js.getString("position"));
		result.setAttribute(js.getString("attribute"));
		result.setMsg(js.getString("mgs"));
		result.setLevel(js.getString("level"));
		result.setRule(js.getString("rule"));
		return  result;
	}
	public Rule(JSONObject js){
		syntax = js.getString("syntax");
		position = js.getString("position");
		attribute = js.getString("attribute");
		msg = js.getString("msg");
		level = js.getString("level");
		rule = js.getString("rule");
	}
	public Smell parseSmell() {
		Smell re = new Smell();
		re.setSyntax(syntax);
		re.setPosition(position);
		re.setAttribute(attribute);
		re.setMsg(msg);
		re.setLevel(level);
		return re;
	}
}
