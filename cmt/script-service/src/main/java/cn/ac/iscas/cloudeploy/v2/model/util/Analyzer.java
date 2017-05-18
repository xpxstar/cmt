package cn.ac.iscas.cloudeploy.v2.model.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import cn.ac.iscas.cloudeploy.v2.puppet.compare.ast.entity.Smell;
/**
 * @author xpxstar@gmail.com
 *
 */
import cn.ac.iscas.cloudeploy.v2.puppet.compare.service.ASTAnalyzeImpl;
import cn.ac.iscas.cloudeploy.v2.puppet.compare.service.GenModuleAST;
public class Analyzer {
	String  BasePath = this.getClass().getResource("/").getPath().substring(1)+"check/";
	
	ASTAnalyzeImpl analyzeImpl;
	
	GenModuleAST genModuleAST;
	
	
	public Analyzer() {
		analyzeImpl = new ASTAnalyzeImpl();
		genModuleAST = new GenModuleAST();
	}
	public Map<String,List<Smell>> check(String module) {
		genModuleAST.generateAST(module);
		
		return analyzeImpl.analyzeAST(module);
//		genModuleAST.
	}

	/**用puppet -lint解析单个 Module 返回检测结果
	 * @param inputFilePath 输入目标文件
	 * @param output 输出目录
	 * @param rubyEnvironment ruby环境
	 * @param PuppetParserSource ruby依赖的库
	 * @param PuppetAnalyseRuby 目标程序
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public Map<String,List<String>> puppetLintCheck(String module) {
		String command="E:/ruby/Ruby21-x64/bin/ruby.exe E:/ruby/Ruby21-x64/bin/puppet-lint "+BasePath+module;
		Map<String,List<String>> result = null;
		try {
			Process process = Runtime.getRuntime().exec(command,null);
			result = printStream(process.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	private Map<String, List<String>> printStream(InputStream inputStream) {
		Map<String,List<String>> result = new HashMap<>();
		if(inputStream !=null){
			Scanner scanner = new Scanner(inputStream);
			while(scanner.hasNext()){
				String tmp = scanner.nextLine();
				String[] lines = tmp.split(" - ");
				String value = lines[1];
				String key = lines[0].split("manifests/")[1];
				key = key.substring(0, key.length()-3);
				if(result.containsKey(key)){
					result.get(key).add(value);
				}else{
					List<String> report = new ArrayList<>();
					report.add(value);
					result.put(key, report);
				}
			}
			scanner.close();
		}else{
			System.err.println("shellCommandRunner: parseString: Optional is not present");
		}
		return result;
	}
}
