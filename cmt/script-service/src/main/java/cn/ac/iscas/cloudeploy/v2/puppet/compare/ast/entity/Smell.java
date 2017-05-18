package cn.ac.iscas.cloudeploy.v2.puppet.compare.ast.entity;

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
public class Smell {
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
	 * 检测的错误类型
	 */
	@Setter @Getter private String msg;
	/**
	 * 错误级别，ERROR，WARNING，
	 */
	@Setter @Getter private String level;
	
	/**
	 * 错误行号
	 */
	@Setter @Getter private int line;

	
}
