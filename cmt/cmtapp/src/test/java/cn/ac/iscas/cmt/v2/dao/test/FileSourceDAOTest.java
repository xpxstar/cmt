package cn.ac.iscas.cmt.v2.dao.test;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.ac.iscas.cmt.v2.model.dao.FileSourceDAO;
import cn.ac.iscas.cmt.v2.model.entity.FileSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration("/application_context.xml")
@Transactional
public class FileSourceDAOTest {
	@Autowired
	private FileSourceDAO fileDAO;
	@Test
	public void testGetFile() throws IOException {
		
		FileSource ff = fileDAO.findFileByMd5("45b3b06061b834d4556c33ed1aa427ab");
		System.out.println(ff.toString());
	}

}
