package cn.ac.iscas.cmt.v2.model.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.PostRemove;

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
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cn.ac.iscas.cmt.v2.model.dao.ArtifactDAO;
import cn.ac.iscas.cmt.v2.model.entity.Artifact;

/**
 * @author xpxstar@gmail.com
 *	制品的业务逻辑
 */
@Service
public class ArtifactServiceImpl implements ArtifactService{
	
	@Autowired
	private ArtifactDAO artifactDAO;
	
	static public String Directory = "";
	//索引文件
	Directory dirWrite = null;
	//分词工具
	Analyzer analyzer = null;;
	//读取索引
	IndexReader ireader = null;  
    //索引搜索
	IndexSearcher isearcher = null;  
    //检索查询
	QueryParser qp = null;
	/**
	 * 获取包括全部应用的列表
	 * 
	 * @return
	 */
	@Override
	public List<Artifact> getAllArtifacts(){
		return (List<Artifact>) artifactDAO.findAll();
	}
	/**
	 * Service创建后调用，初始化索引及各种流对象
	 * @throws IOException
	 */
	@PostConstruct
	private void init() throws IOException {
		Directory = this.getClass().getClassLoader().getResource("").getPath();
		File dd = new File(Directory+"index");
		if (!dd.exists()) {
			dirWrite = FSDirectory.open(dd.toPath());
			analyzer = new StandardAnalyzer();
			frashIndex();
		}else{
			dirWrite = FSDirectory.open(dd.toPath());
			analyzer = new StandardAnalyzer();
			
		}
		ireader = DirectoryReader.open(dirWrite);  
		isearcher = new IndexSearcher(ireader);  
        qp = new QueryParser("doc", analyzer);  
	}
	/**
	 * service 对象销毁时调用，关闭各种流对象
	 */
	@PostRemove
	private void terminal(){
		analyzer.close();
		try {
			ireader.close();
			dirWrite.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 按照id获取目标制品
	 * 
	 * @return
	 */
	@Override
	public Artifact getArtifactById(Long id){
		return artifactDAO.findOne(id);
	}
	/**
	 * 制品计数
	 * 
	 * @return
	 */
	@Override
	public long count(){
		return artifactDAO.count();
	}
	/**
	 * 获取分页的制品数据
	 * @param page
	 * @return
	 */
	@Override
	public Page<Artifact> getAllArtifacts(Pageable page) {
		// TODO Auto-generated method stub
		return artifactDAO.findAll(page);
	}
	/**
	 * 按照关键字及页面请求返回检索结果
	 * 基本思路是从索引文件中使用lucene 利用tf-idf取得检索结果，然后在数据库中找到完整的数据，并返回给前端。
	 * @param keyword
	 * @param pageable
	 * @return
	 * @throws IOException
	 */
	@Override
	public Page<Artifact> query(String keyword, Pageable pageable) throws IOException {
        Query query=null;
		try {
			query = qp.parse(keyword);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        System.out.println("Query = " + query);  
        long start = System.currentTimeMillis();  
        TopDocs topDocs = isearcher.search(query, Integer.MAX_VALUE);  
        List<Long> ids = parsePage(pageable, topDocs);
        List<Artifact> content = (List<Artifact>) (artifactDAO.findAll(ids));
        content = reOrder(ids, content);
        System.out.println("Spend time:"+(System.currentTimeMillis() - start) + " ms");  
        dirWrite.close();
		
		Page<Artifact> result = new PageImpl<>(content, pageable, topDocs.totalHits);
		return result;
		
	}
	/**
	 * 刷新索引
	 * @return
	 */
	@Override
	public boolean frashIndex() {
		IndexWriter writer = null;
		// 初始化写入配置
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		iwc.setOpenMode(OpenMode.CREATE);
		// 创建模式 OpenMode.CREATE_OR_APPEND 添加模式
		//如果是CREATE ,每次都会重新创建这个索引，清空以前的数据，如果是append 每次都会追加，之前的不删除
		//在日常的需求索引添加中，一般都是 APPEND 持续添加模式
		List<Artifact> art = (List<Artifact>) artifactDAO.findAll();
		try {
			writer = new IndexWriter(dirWrite, iwc);
			for (Artifact artifact : art) {
				Document doc = new Document();
				doc.add(new LongField("id", artifact.getId(), Store.YES));
				doc.add(new TextField("doc",artifact.toDocument(),Store.YES));
				writer.addDocument(doc);
			}
			writer.commit(); 
			writer.close();
			return  true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 对数据库中获取的分页制品重新排序
	 * @param ids
	 * @param arts
	 * @return
	 */
	private List<Artifact> reOrder(List<Long> ids,List<Artifact> arts){
		Map<Long, Artifact> map = new HashMap<>(arts.size());
		for (Artifact artifact : arts) {
			map.put(artifact.getId(), artifact);
		}
		arts.clear();
		for (Long id : ids) {
			arts.add(map.get(id));
		}
		return arts;
	}
	/**
	 * 将检索结果转换为制品的id列表
	 * @param page
	 * @param top
	 * @return
	 */
	private List<Long> parsePage(Pageable page,TopDocs top){
		int pagenum = page.getPageNumber();
		int pagesize = page.getPageSize();
		int start = (pagenum-1) * pagesize;
		int end = start+ pagesize;
		if(end > top.totalHits){
			start = top.totalHits-top.totalHits%pagesize;
			end = top.totalHits;
		}
		ArrayList<Long> result = new ArrayList<>(pagesize);
		for(int i = start;i < end;i++ ){
			try {
				result.add(Long.valueOf(isearcher.doc(top.scoreDocs[i].doc).get("id")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
