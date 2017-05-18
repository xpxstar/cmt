package cn.ac.iscas.cmt.v2.service.test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.ac.iscas.cmt.v2.model.service.AnalyzeServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration("/application_context.xml")
@Transactional
public class AnalyzeServiceTest {
	@Autowired
	private AnalyzeServiceImpl analyzeService;
	/**
	 * 测试service 的 query查询
	 * @throws IOException
	 */
	@Test
	@Ignore
	public void lintCheckTest() throws IOException {
		Map<String, List<String>> arts =  analyzeService.lintCheck("myhadoop");
		for (String key : arts.keySet()) {
			System.out.println(key+": "+ arts.get(key).size());
		}
	}
	@Test
	public void syntaxCheckTest() throws IOException {
		String arts =  analyzeService.syntaxCheck("install.pp");
		System.out.println(arts);
	}

}
