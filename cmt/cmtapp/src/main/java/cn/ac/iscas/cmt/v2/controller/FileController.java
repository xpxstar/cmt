package cn.ac.iscas.cmt.v2.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.ByteSource;
import com.google.common.net.HttpHeaders;

import cn.ac.iscas.cmt.v2.controller.BasicController.DResponseBuilder;
import cn.ac.iscas.cmt.v2.model.dao.io.MultipartFileByteSource;
import cn.ac.iscas.cmt.v2.model.dao.io.ResponseByteSink;
import cn.ac.iscas.cmt.v2.model.service.FileService;
import cn.ac.iscas.cmt.v2.util.RandomUtils;

@Controller
@Transactional
@RequestMapping(value = "v2/files")
public class FileController {
	@Autowired
	private FileService fileService;


	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Object uploadFile(@RequestParam("file") MultipartFile file)
			throws IOException {
		
		return DResponseBuilder
				.instance()
				.add("fileKey",
						fileService.saveFile(new MultipartFileByteSource(file)))
				.build();
	}
	
	/**
	 * @description upload puppet modules zipfile
	 * @param file
	 * @return file Md5-key
	 * @throws IOException Object
	 * @author xpxstar@gmail.com
	 * 2015年11月13日 下午3:38:45
	 */
	@RequestMapping(value = { "upload/puppet" }, method = RequestMethod.POST)
	@ResponseBody
	public Object uploadPuppetFile(@RequestParam("file") MultipartFile file)
			throws IOException {
		return DResponseBuilder
				.instance()
				.add("fileKey",
						fileService.savePuppetFile(new MultipartFileByteSource(file)))
				.build();
	}

/*	@RequestMapping(value = { "/{fileKey}" }, method = RequestMethod.POST)
	@ResponseBody
	public Object requestFileDownloadURL(@PathVariable("fileKey") String fileKey) {
		return DResponseBuilder.instance()
				.add("url", fileService.generateDownloadURL(fileKey)).build();
	}*/

	@RequestMapping(value = { "/{downloadKey}" }, method = RequestMethod.GET)
	public void downloadFile(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("downloadKey") String downloadKey,
			@RequestParam(value = "name", required = false, defaultValue = "") String name)
			throws IOException {
		ByteSource byteSource = fileService.findFile(downloadKey);
		launchDownloadResponse(response, byteSource,
				StringUtils.isEmpty(name) ? RandomUtils.randomString() : name);
	}

	@RequestMapping(value = { "/content/{fileKey}" }, method = RequestMethod.GET)
	@ResponseBody
	public Object requestFileContent(@PathVariable("fileKey") String fileKey)
			throws IOException {
		return DResponseBuilder.instance()
				.add("content", fileService.readFileContent(fileKey)).build();
	}

	@RequestMapping(value = { "/content" }, method = RequestMethod.POST)
	@ResponseBody
	public Object writeContentToFile(
			@RequestParam("fileContent") String fileContent) throws IOException {
		return DResponseBuilder.instance()
				.add("fileKey", fileService.writeFileContent(fileContent))
				.build();
	}

	private void launchDownloadResponse(HttpServletResponse response,
			ByteSource byteSource, String fileName) throws IOException {
		response.reset();
		response.setContentType("application/octet-stream");
		response.addHeader(HttpHeaders.CONTENT_LENGTH,
				String.valueOf(byteSource.size()));
		String name = new String(fileName.getBytes("GB2312"), "ISO8859-1");
		response.addHeader(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + name + "\"");
		byteSource.copyTo(new ResponseByteSink(response));
	}
}
