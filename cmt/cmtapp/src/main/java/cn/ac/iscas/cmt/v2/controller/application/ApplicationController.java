package cn.ac.iscas.cmt.v2.controller.application;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "v2/test")
public class ApplicationController {
	/**
	 * 获取应用列表
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public String getApplications() {
		return "OK";
	}
}
