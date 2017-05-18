package cn.ac.iscas.cmt.v2.service.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;

import cn.ac.iscas.cmt.v2.util.CommandRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration("/application_context.xml")
@Transactional
public class ShellCommandRunnerTest {
//	@Autowired
//	private CommandRunner runner;
	/**
	 * 测试service 的 query查询
	 * @throws IOException
	 */
	/*@Test
	@Ignore
	public void runnerTest() throws IOException {
		List<String> arts =  runner.tidyRun("puppet parser validate /cmt/install.pp");
		if(arts.size() > 0 ){
			for (String string : arts) {
				System.out.println(string);
			}
				
		}
	}
	@Test
	@Ignore
	public void normalRunnerTest() throws IOException {
		Optional<InputStream>  re =  runner.run("puppet parser");
		if(re.isPresent()){
			Scanner scanner = new Scanner(re.get());
			while(scanner.hasNext()){
				System.out.println(scanner.nextLine());
			}
			scanner.close();
		}
	}*/
	@Test
	public void puppetLintCheck() {
		String command="E:/ruby/Ruby21-x64/bin/ruby.exe E:/ruby/Ruby21-x64/bin/puppet-lint f:/puppet/myhadoop";
		Map<String,List<String>> result = null;
		try {
			Process process = Runtime.getRuntime().exec(command,null);
			result = printStream(process.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
