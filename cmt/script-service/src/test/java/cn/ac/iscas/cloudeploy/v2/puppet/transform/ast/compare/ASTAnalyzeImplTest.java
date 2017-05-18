package cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.compare;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import cn.ac.iscas.cloudeploy.v2.puppet.compare.ast.entity.Smell;
import cn.ac.iscas.cloudeploy.v2.puppet.compare.service.ASTAnalyzeImpl;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.PuppetParser;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ResouceType;
public class ASTAnalyzeImplTest {
	ASTAnalyzeImpl analyzer;
	String target = "";
//	String path = "";
//	PuppetParser parser;
//	String rubyEnvironment;
//	String PuppetParserSource;
//	String PuppetAnalyseRuby;
	@Before
	public void initASTCompareTest(){
//		parser=new PuppetParser();
//		rubyEnvironment = "E:\\ruby\\Ruby21-x64\\bin\\ruby.exe";
//		PuppetParserSource = "D:\\puppet_parser\\resource";
//		PuppetAnalyseRuby = "D:\\puppet_parser\\main\\single-file-scanner.rb";
		analyzer = new ASTAnalyzeImpl();
//		path = System.getProperty("user.dir");
//		target = path+"/commit/puppetlabs/puppetlabs-postgresql/manifests/server.pp";
	}
	@Test
	public void analyzeTest(){
//		ResouceType aa = parser.extractTree(target+"/ast/test");
		// last true/false implies argument compare
		Map<String,List<Smell>> relist=analyzer.analyzeAST("epel");
//		comparable.compareArguments(aa,bb,relist);
		for (String key : relist.keySet()) {
			for(Smell sm : relist.get(key)){
				System.out.println(key +": ");
				System.out.println(sm);
			}
			
		}
	}
}
