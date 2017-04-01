package cn.ac.iscas.cmt.v2.controller.application;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ac.iscas.cmt.v2.model.entity.Artifact;
import cn.ac.iscas.cmt.v2.model.service.ArtifactService;
import cn.ac.iscas.cmt.v2.model.service.ArtifactServiceImpl;
import cn.ac.iscas.cmt.v2.util.StaticCategory;
import cn.ac.iscas.cmt.v2.util.ViewParser;

@Controller
@RequestMapping(value = "v2/artifact")
public class ArtifactController {
	@Autowired
	ArtifactServiceImpl artifactService;
	
	
	/**
	 * 获取制品层次类别列表
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/category" },method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public String getCategory() {
		return ViewParser.parseJSONString(StaticCategory.root);
	}
	/**
	 * 获取制品分页列表
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/{id}" },method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public String getArtifact(@PathVariable("id") Long id) {
		Artifact art = artifactService.getArtifactById(id);
		return ViewParser.parseJSONString(art);
	}
	/**
	 * 获取制品分页列表
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/{pageSize}/{page}" },method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public String getArtifacts(@PathVariable("pageSize") Integer pageSize,
			@RequestParam(value="type",required = true) String type,
			@PathVariable("page") Integer page) {
		pageSize = pageSize>20?20:pageSize<1?1:pageSize;
		page = page<1?1:page;
		Pageable pageable = new PageRequest(page-1, pageSize);
		Page<Artifact> art = artifactService.getArtifactByType(type,pageable);
		return ViewParser.parseJSONString(art);
	}
	
	/**
	 * 获取制品检索的分页列表
	 * 
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = { "query/{pageSize}/{page}" },method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public String queryArtifacts(@RequestParam(value="query",required = true) String query,
			@RequestParam(value="type",required = true) String type,
			@PathVariable("pageSize") Integer pageSize,
			@PathVariable("page") Integer page) throws IOException {
		pageSize = pageSize>20?20:pageSize<1?1:pageSize;
		page = page<1?1:page;
		Pageable pageable = new PageRequest(page-1, pageSize);
		Page<Artifact> art = artifactService.query(query,pageable,type);
		return ViewParser.parseJSONString(art);
	}
	
	/**
	 * 获取在分类中制品检索的分页列表
	 * 
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = { "queryCate/{pageSize}/{page}" },method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public String queryCategoryArtifacts(@RequestParam(value="query",required = true) String query,
			@RequestParam(value="type",required = true) String type,
			@RequestParam(value="cate",required = true) String cate,
			@PathVariable("pageSize") Integer pageSize, 
			@PathVariable("page") Integer page) throws IOException {
		pageSize = pageSize>20?20:pageSize<1?1:pageSize;
		page = page<1?1:page;
		Pageable pageable = new PageRequest(page-1, pageSize);
		Page<Artifact> art = artifactService.queryCate(query,cate,pageable,type);
		return ViewParser.parseJSONString(art);
	}
	/** 
	 * 获取某个类别的制品列表
	 * 
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = { "category/{pageSize}/{page}" },method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public String categoryArtifacts(@RequestParam(value="cate",required = true) String cate,
			@RequestParam(value="type",required = true) String type,
			@PathVariable("pageSize") Integer pageSize, 
			@PathVariable("page") Integer page) throws IOException {
		pageSize = pageSize>20?20:pageSize<1?1:pageSize;
		page = page<1?1:page;
		Pageable pageable = new PageRequest(page-1, pageSize);
		Page<Artifact> art = artifactService.getArtifactByCategory(cate,pageable,type);
		return ViewParser.parseJSONString(art);
	}
	/**
	 * 索引刷新
	 * 
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = { "flashindex/type" },method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public String frashIndex(@PathVariable("type") String type) throws IOException {
		if (type.equals("ansible")||type.equals("puppet")) {
			return artifactService.frashIndex(type)?"success":"fail";
		}
		return "fail";
	}
}
