package cn.ac.iscas.cmt.v2.service.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;

import cn.ac.iscas.cmt.v2.model.service.CommandRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration("/application_context.xml")
@Transactional
public class ShellCommandRunnerTest {
	@Autowired
	private CommandRunner runner;
	/**
	 * 测试service 的 query查询
	 * @throws IOException
	 */
	@Test
//	@Ignore
	public void queryTest() throws IOException {
		Optional<InputStream> arts =  runner.run("date");
		if(arts.isPresent()){
			Scanner scanner = new Scanner(arts.get());
			while(scanner.hasNext()){
				System.out.println(scanner.nextLine());
			}
			scanner.close();
		}
	}

}
