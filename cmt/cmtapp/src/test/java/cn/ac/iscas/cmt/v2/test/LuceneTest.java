//package cn.ac.iscas.cmt.v2.test;
//
//import java.io.IOException;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.domain.Sort.Direction;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import cn.ac.iscas.cmt.v2.model.entity.Artifact;
//import cn.ac.iscas.cmt.v2.model.service.ArtifactService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ActiveProfiles("dev")
//@ContextConfiguration("/application_context.xml")
//@Transactional
//public class LuceneTest {
//	@Autowired
//	private ArtifactService artifactService;
//	@Test
//	public void testSaveFile() throws IOException {
////		Artifact art = artifactService.getArtifactById(var);
////		long count = artifactService.count();
////		System.out.println(count);
////		System.out.println(art.getName());
////		fileService.saveFile(byteSource); 
//		
//		Sort sort = new Sort(Direction.DESC, "id");
//	    Pageable pageable = new PageRequest(1, 5, sort);
//		Page<Artifact> art = artifactService.getAllArtifacts(pageable);
//	}
//
//}
