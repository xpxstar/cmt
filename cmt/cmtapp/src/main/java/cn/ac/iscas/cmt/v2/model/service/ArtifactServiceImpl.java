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
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StoredField;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cn.ac.iscas.cmt.v2.model.dao.ArtifactDAO;
import cn.ac.iscas.cmt.v2.model.dao.SynonymsDao;
import cn.ac.iscas.cmt.v2.model.entity.Artifact;
import cn.ac.iscas.cmt.v2.model.entity.Synonyms;

/**
 * @author xpxstar@gmail.com
 *	制品的业务逻辑
 */
@Service
public class ArtifactServiceImpl implements ArtifactService{
	
	@Autowired
	private ArtifactDAO artifactDAO;
	@Autowired
	private SynonymsDao synonymsDao;
	static public String Directory = "";
	//索引文件
	Directory aWrite = null;
	Directory pWrite = null;
	//分词工具
	Analyzer analyzer = null;;
	//读取索引
	IndexReader areader = null;  
	IndexReader preader = null;  
    //索引搜索
	IndexSearcher aSearcher = null;  
	IndexSearcher pSearcher = null;  
    //检索查询
	QueryParser qp = null;
	/**
	 * 获取包括全部应用的列表
	 * 
	 * @return
	 */
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
		File add = new File(Directory+"ansible");
		File pdd = new File(Directory+"puppet");
		analyzer = new StandardAnalyzer();
		aWrite = FSDirectory.open(add.toPath());
		pWrite = FSDirectory.open(pdd.toPath());
//		if (!add.exists() || !pdd.exists()) {
			frashIndex("ansible");
			frashIndex("puppet");
//		}
		areader = DirectoryReader.open(aWrite);  
		preader = DirectoryReader.open(pWrite);  
		aSearcher = new IndexSearcher(areader);  
		pSearcher = new IndexSearcher(preader);  
        qp = new QueryParser("doc", analyzer);  
        
/*        List<Synonyms> synonymsList=  (List<Synonyms>) synonymsDao.findAll();
		for (Synonyms synonyms : synonymsList) {
			smap.put(synonyms.getSynonyms(), synonyms.getName());
		}
*/	}
	/**
	 * service 对象销毁时调用，关闭各种流对象
	 */
	@PostRemove
	private void terminal(){
		analyzer.close();
		try {
			areader.close();
			preader.close();
			aWrite.close();
			pWrite.close();
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
	
	public Artifact getArtifactById(Long id){
		return artifactDAO.findOne(id);
	}
	/**
	 * 制品计数
	 * 
	 * @return
	 */
	
	public long count(){
		return artifactDAO.count();
	}
	/**
	 * 获取分页的制品数据
	 * @param page
	 * @return
	 */
	
	public Page<Artifact> getAllArtifacts(Pageable page) {
		// TODO Auto-generated method stub
		return artifactDAO.findAll(page);
	}
	public Page<Artifact> getArtifactByType(String type,Pageable page) {
		// TODO Auto-generated method stub
		return artifactDAO.findArtifactByType(type,page);
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
	public Page<Artifact> query(String keyword, Pageable pageable,String type) throws IOException {
		keyword = querySynonyms(keyword);//查询同义词
        IndexSearcher isearcher = type.equals("ansible")?aSearcher:pSearcher;
		Query query=null;
		try {
			query = qp.parse(keyword);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        System.out.println("Query = " + query);  
        long start = System.currentTimeMillis();
        TopDocs topDocs = null;
        topDocs = isearcher.search(query, Integer.MAX_VALUE);  
        
//        ScoreDoc[] hits = topDocs.scoreDocs;
//        for(int i = 0;i<hits.length;i++){
//        	Document hitDoc = isearcher.doc(hits[i].doc);
//        	hitDoc.get("version");
//        }
//        System.out.println("Spend time:"+(System.currentTimeMillis() - start) + " ms");  
        List<Long> ids = parsePage(pageable, topDocs,isearcher);
        List<Artifact> content = (List<Artifact>) (artifactDAO.findAll(ids));
        content = reOrder(ids, content);
        System.out.println("Spend time:"+(System.currentTimeMillis() - start) + " ms");  
		
		Page<Artifact> result = new PageImpl<>(content, pageable, topDocs.totalHits);
		return result;
		
	}
	@Override
	public Page<Artifact> getArtifactByCategory(String cate, Pageable pageable,String type){
		return artifactDAO.findByCategory(cate,type,pageable);
	}
	/**
	 * 刷新索引
	 * @return
	 */
	@Override
	public boolean frashIndex(String type) {
		Directory dwriter=type.equals("ansible")?aWrite:pWrite;
		IndexWriter writer = null;
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		iwc.setOpenMode(OpenMode.CREATE);
		List<Artifact> art = (List<Artifact>) artifactDAO.findArtifactByType(type);
		try {
			writer = new IndexWriter(dwriter, iwc);
			for (Artifact artifact : art) {
				Document doc = new Document();
				doc.add(new LongField("id", artifact.getId(), Store.YES));
				String document = artifact.toDocument().toLowerCase().replaceAll("c++", "cpp").replaceAll("c#", "c_sharp");
				doc.add(new TextField("doc",document,Store.YES)); //替换C++ C#之类的词汇
				
//				doc.add(new StoredField("version", artifact.getVersion()));
//				doc.add(new StoredField("alldown", artifact.getAlldown()));
//				doc.add(new StoredField("download", artifact.getDownload()));
//				doc.add(new StoredField("lastDate", artifact.getLastDate().toString()));
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
	 * 对数据库中获取的分页制品重新排序
	 * @param ids
	 * @param arts
	 * @return
	 */
	private List<Artifact> reOrder(List<Long> ids,List<Artifact> arts,Pageable page){
		int pagenum = page.getPageNumber();
		int pagesize = page.getPageSize();
		int start = pagenum * pagesize;
		int end = start+ pagesize;
		if(end > arts.size()){
			start = arts.size()-arts.size()%pagesize;
			end = arts.size();
		}
		Map<Long, Artifact> map = new HashMap<>(arts.size());
		for (Artifact artifact : arts) {
			map.put(artifact.getId(), artifact);
		}
		arts.clear();
		for (Long id : ids) {
			if (map.containsKey(id)) {
				arts.add(map.get(id));
			}
			if (arts.size()>=end) {
				break;
			}
		}
		return arts.subList(start, end);
	}
	/**
	 * 将检索结果转换为制品的id列表
	 * @param page
	 * @param top
	 * @return
	 */
	private List<Long> parsePage(Pageable page,TopDocs top,IndexSearcher isearcher){
		int pagenum = page.getPageNumber();
		int pagesize = page.getPageSize();
		int start = pagenum * pagesize;
		int end = start+ pagesize;
		if(end > top.totalHits){//end大于总数则显示最后一页
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

	public Page<Artifact> getArtifactsByCat(String category,Pageable page) {
		return artifactDAO.findByCategoryContaining(category, page);
	}
	/**
	 * 将检索结果转换为制品的id列表
	 * @param page
	 * @param top
	 * @return
	 */
	private List<Long> parsePage(TopDocs top,IndexSearcher isearcher){
		ArrayList<Long> result = new ArrayList<>(top.totalHits);
		for(int i = 0;i < top.totalHits;i++ ){
			try {
				result.add(Long.valueOf(isearcher.doc(top.scoreDocs[i].doc).get("id")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public Page<Artifact> queryCate(String keyword, String cate, Pageable pageable,String type) throws IOException {
		keyword = querySynonyms(keyword);//查询同义词
		
		Query query=null;
		IndexSearcher isearcher = type.equals("ansible")?aSearcher:pSearcher;
		try {
			query = qp.parse(keyword);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        System.out.println("Query = " + query);  
        long start = System.currentTimeMillis();  
        TopDocs topDocs = isearcher.search(query, Integer.MAX_VALUE);  
        List<Long> ids = parsePage(topDocs,isearcher);
        List<Artifact> content = (List<Artifact>) (artifactDAO.findIdsInCategory(cate,ids));
        content = reOrder(ids, content,pageable);
        System.out.println("Spend time:"+(System.currentTimeMillis() - start) + " ms");  
		
		Page<Artifact> result = new PageImpl<>(content, pageable, content.size());
		return result;
	}
	
	public String querySynonyms(String keyword) {
		String[] strs = keyword.split(" ");//查询出每个单词同义词
		for(int i=0;i<strs.length;i++){
			List<Synonyms> synonym = synonymsDao.findBySynonyms(strs[i]);
			List<Synonyms> synonyms = null;
			if(synonym.size()>0){//该词形式不标准
				synonyms = synonymsDao.findByName(synonym.get(0).getName());
				keyword += " " + synonym.get(0).getName();//加上原型词
			}else {//是原型词或者无该词
				synonyms = synonymsDao.findByName(strs[i]);
			}
			if(synonyms.size()>0){
				for(int j = 0 ; j<synonyms.size(); j++){
					if(!synonyms.get(j).getSynonyms().equals(strs[i]))
						keyword += " " + synonyms.get(j).getSynonyms();
				}
					
			}
		}
		return keyword;
	}
}
