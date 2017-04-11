package cn.ac.iscas.cmt.v2.controller;

import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "v2/views/artifact")
@Transactional
public class ArtifactViewController {

	@RequestMapping(method = RequestMethod.GET)
	public String mainPage() {
		return "app/main";
	}

	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public String listPage() {
		return "app/list";
	}

	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String serachPage() {
		return "app/search";
	}
	@RequestMapping(value = { "/analyze" }, method = RequestMethod.GET)
	public String analyzePage() {
		return "app/analyze";
	}
	@RequestMapping(value = { "/upload" }, method = RequestMethod.GET)
	public String uploadPage() {
		return "app/upload";
	}
	@RequestMapping(value = { "/detail" }, method = RequestMethod.GET)
	public String detailPage() {
		return "app/detail";
	}
}