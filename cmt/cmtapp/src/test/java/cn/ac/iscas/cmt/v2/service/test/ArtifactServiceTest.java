package cn.ac.iscas.cmt.v2.service.test;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.ac.iscas.cmt.v2.model.entity.Artifact;
import cn.ac.iscas.cmt.v2.model.service.ArtifactService;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration("/application_context.xml")
@Transactional
public class ArtifactServiceTest {
	@Autowired
	private ArtifactService artifactService;
	/**
	 * 测试service 的 query查询
	 * @throws IOException
	 */
	@Test
	@Ignore
	public void queryTest() throws IOException {
		Pageable page = new PageRequest(1, 5);
		Page<Artifact> arts =  artifactService.query("php redis", page,"ansible");
		System.out.println(arts.getTotalElements());
		System.out.println(arts.getNumber());
		System.out.println(arts.getSize());
		for (Artifact artifact : arts) {
			System.out.print(artifact.getId()+"\t");
		}
	}
	
	@Test
//	@Ignore
	public void flashIndex() throws IOException {
		artifactService.frashIndex("ansible");
		artifactService.frashIndex("puppet");
	}

}
