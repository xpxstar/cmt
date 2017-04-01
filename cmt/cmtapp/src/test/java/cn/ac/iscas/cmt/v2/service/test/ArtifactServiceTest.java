//package cn.ac.iscas.cmt.v2.service.test;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field.Store;
//import org.apache.lucene.document.LongField;
//import org.apache.lucene.document.TextField;
//import org.apache.lucene.index.DirectoryReader;
//import org.apache.lucene.index.IndexReader;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.index.IndexWriterConfig;
//import org.apache.lucene.index.IndexWriterConfig.OpenMode;
//import org.apache.lucene.queryparser.classic.ParseException;
//import org.apache.lucene.queryparser.classic.QueryParser;
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.Query;
//import org.apache.lucene.search.ScoreDoc;
//import org.apache.lucene.search.TopDocs;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import cn.ac.iscas.cmt.v2.model.dao.ArtifactDAO;
//import cn.ac.iscas.cmt.v2.model.entity.Artifact;
//import cn.ac.iscas.cmt.v2.model.service.ArtifactService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ActiveProfiles("dev")
//@ContextConfiguration("/application_context.xml")
//@Transactional
//public class ArtifactServiceTest {
//	@Autowired
//	private ArtifactService artifactService;
//	/**
//	 * 测试service 的 query查询
//	 * @throws IOException
//	 */
//	@Test
//	public void queryTest() throws IOException {
//		Pageable page = new PageRequest(1, 5);
//		Page<Artifact> arts =  artifactService.query("php redis", page);
//		System.out.println(arts.getTotalElements());
//		System.out.println(arts.getNumber());
//		System.out.println(arts.getSize());
//		for (Artifact artifact : arts) {
//			System.out.print(artifact.getId()+"\t");
//		}
//	}
//
//}
