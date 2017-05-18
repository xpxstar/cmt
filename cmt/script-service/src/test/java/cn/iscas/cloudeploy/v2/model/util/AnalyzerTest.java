package cn.iscas.cloudeploy.v2.model.util;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cn.ac.iscas.cloudeploy.v2.model.util.Analyzer;

public class AnalyzerTest {
	Analyzer analyzer; 
	@Before
	public void init(){
		analyzer = new Analyzer();
		
	}
	@Test
	public void checkTest(){
		Map<String, List<String>> rLint = analyzer.puppetLintCheck("epel");
		for(String key:rLint.keySet()){
			for(String va : rLint.get(key)){
				System.out.println(key+": "+va);
			}
		}
	}
	@Test
	@Ignore
	public void checkerTest(){
		analyzer.check("firewall");
	}
}
