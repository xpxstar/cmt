package cn.ac.iscas.cmt.v2.model.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//import cn.ac.iscas.cloudeploy.v2.dataview.application.ArtifactView;
import cn.ac.iscas.cmt.v2.model.entity.Artifact;

public interface ArtifactService {

	/**
	 * 获取全部制品
	 * 
	 * @return
	 */
	public List<Artifact> getAllArtifacts();
	/**
	 * 分页获取制品
	 * 
	 * @return
	 */
	public Page<Artifact> getAllArtifacts(Pageable page);
	/**
	 * 按照id获取制品
	 * 
	 * @return
	 */
	public Artifact getArtifactById(Long id);

	/**
	 * 计数
	 * 
	 * @return
	 */
	public long count();
	
	/**
	 * 查询 依据query 查询返回page对象
	 * @param query 
	 * @param pageable
	 * @return
	 * @throws IOException 
	 */
	public Page<Artifact> query(String query, Pageable pageable,String Type) throws IOException;
	
	/**
	 * 强制更新索引
	 * @param query 
	 * @param pageable
	 * @return
	 * @throws IOException 
	 */
	public boolean frashIndex(String type) throws IOException;
	
	
	/**
	 * 返回特定类别的对象
	 * @param cate
	 * @param pageable
	 * @return
	 */
	public Page<Artifact> getArtifactByCategory(String cate, Pageable pageable,String type);
	/**
	 * 在指定类别中检索
	 * @param query
	 * @param cate
	 * @param pageable
	 * @return
	 * @throws IOException 
	 */
	public Page<Artifact> queryCate(String keyword, String cate, Pageable pageable,String type) throws IOException;

}
