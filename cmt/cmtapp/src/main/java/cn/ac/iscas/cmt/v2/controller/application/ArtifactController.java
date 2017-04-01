package cn.ac.iscas.cmt.v2.controller.application;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
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
import cn.ac.iscas.cmt.v2.util.ViewParser;

@Controller
@RequestMapping(value = "v2/artifact")
public class ArtifactController {
	@Autowired
	ArtifactService artifactService;
	/**
	 * 获取应用列表
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/{pageSize}/{page}" },method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public String getArtifacts(@PathVariable("pageSize") Integer pageSize, @PathVariable("page") Integer page) {
		pageSize = pageSize>20?20:pageSize<1?1:pageSize;
		Pageable pageable = new PageRequest(page-1, pageSize);
		Page<Artifact> art = artifactService.getAllArtifacts(pageable);
		return ViewParser.parseJSONString(art);
	}
	
	/**
	 * 查询应用列表
	 * 
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = { "query/{pageSize}/{page}" },method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public String queryArtifacts(@RequestParam(value="query",required = true) String query,@PathVariable("pageSize") Integer pageSize, @PathVariable("page") Integer page) throws IOException {
		pageSize = pageSize>20?20:pageSize<1?1:pageSize;
		Pageable pageable = new PageRequest(page, pageSize);
		Page<Artifact> art = artifactService.query(query,pageable);
		return ViewParser.parseJSONString(art);
	}
	/**
	 * 更新索引
	 * 
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = { "flashindex" },method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public String frashIndex() throws IOException {
		return artifactService.frashIndex()?"success":"fail";
	}
	
	/**
	 * 按照类别获取制品
	 * @return
	 */
	@RequestMapping(value = {"cat/{pageSize}/{page}"},method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public String getArtifactsByCat(@RequestParam(value="cat",required=true) String cat,
			@PathVariable("pageSize") Integer pagesize,@PathVariable("page") Integer page){
		pagesize = pagesize>20?20:pagesize<1?1:pagesize;
		Pageable pageable = new PageRequest(page, pagesize);
		Page<Artifact> art = artifactService.getArtifactsByCat("/"+cat+"/", pageable);
		return ViewParser.parseJSONString(art);
	}
}
