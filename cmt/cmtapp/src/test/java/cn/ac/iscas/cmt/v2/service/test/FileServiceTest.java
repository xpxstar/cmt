package cn.ac.iscas.cmt.v2.service.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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

import com.google.common.io.Files;

import cn.ac.iscas.cmt.v2.model.dao.io.Md5CachingByteSource;
import cn.ac.iscas.cmt.v2.model.service.AnalyzeServiceImpl;
import cn.ac.iscas.cmt.v2.model.service.FileService;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration("/application_context.xml")
@Transactional
public class FileServiceTest {
	@Autowired
	private FileService fileService;
	String fileRoot = "F:/paper/scripts/puppet";
	/**
	 * 测试service 的 query查询
	 * @throws IOException
	 */
	@Test
	public void md5Test() throws IOException {
		File file = new File(fileRoot);
		File[] childs = file.listFiles();
		File tar = new File("f:/temp1.txt");
		FileOutputStream fo = new FileOutputStream(tar);
		OutputStreamWriter ow = new OutputStreamWriter(fo, "UTF-8");
		for (File ff : childs) {
			Md5CachingByteSource md5ByteSource = Md5CachingByteSource
				.fromImmutableByteSource(Files.asByteSource(ff));
			ow.write(ff.getName()+","+md5ByteSource.getMd5()+"\n");
		}
		ow.close();
		fo.close();
	}

}
