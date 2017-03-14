package cn.ac.iscas.cmt.v2.dao.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.ac.iscas.cmt.v2.model.dao.ArtifactDAO;
import cn.ac.iscas.cmt.v2.model.entity.Artifact;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration("/application_context.xml")
@Transactional
public class ArtifactDAOTest {
	@Autowired
	private ArtifactDAO artifactDAO;
	@Test
	public void testSaveFile() throws IOException {
//		String test="testbytesourcestring";
//		ByteSource byteSource=ByteSource.wrap(test.getBytes());
		String directory = "lucene/index";
		long var = 1;
		IndexWriter writer = null;
		Analyzer analyzer = new StandardAnalyzer();
		Directory dirWrite = FSDirectory.open(new File(directory).toPath());
		// 初始化写入配置
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		iwc.setOpenMode(OpenMode.CREATE);// 创建模式 OpenMode.CREATE_OR_APPEND 添加模式
		//如果是CREATE ,每次都会重新创建这个索引，清空以前的数据，如果是append 每次都会追加，之前的不删除
		           //在日常的需求索引添加中，一般都是 APPEND 持续添加模式
		writer = new IndexWriter(dirWrite, iwc);
//		Sort sort = new Sort(Direction.DESC, "id");
		List<Artifact> art = (List<Artifact>) artifactDAO.findAll();
		System.out.println(art.size());
		for (Artifact artifact : art) {
			Document doc = new Document();
			doc.add(new LongField("id", artifact.getId(), Store.YES));
			doc.add(new TextField("doc",artifact.toDocument(),Store.YES));
//			doc.add(new StringField("name", artifact.getName(), Store.YES));
			writer.addDocument(doc);
		}
		writer.commit(); 
		writer.close();
//		fileService.saveFile(byteSource);
		
		
		IndexReader ireader = DirectoryReader.open(dirWrite);  
        IndexSearcher isearcher = new IndexSearcher(ireader);  
        
        String keyword = "php web";
//        String[] fields = {"name","tags"};
        QueryParser qp = new QueryParser("doc", analyzer);  
        qp.setDefaultOperator(QueryParser.AND_OPERATOR);  
        Query query=null;
		try {
			query = qp.parse(keyword);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        System.out.println("Query = " + query);  
        long start = System.currentTimeMillis();  
        //搜索相似度最高的2条记录  
        System.out.println("搜索相似度最高的5条记录");  
        TopDocs topDocs = isearcher.search(query, 150);  
        System.out.println("命中：" + topDocs.totalHits);  
        for (ScoreDoc sd : topDocs.scoreDocs) {  
            Document doc = isearcher.doc(sd.doc);  
            System.out.println(sd.score);  
            System.out.println("id:" + doc.get("id"));  
            System.out.println("doc:" + doc.get("doc"));  
        }  
        System.out.println("Spend time:"+(System.currentTimeMillis() - start) + " ms");  
        dirWrite.close();
	}

}
