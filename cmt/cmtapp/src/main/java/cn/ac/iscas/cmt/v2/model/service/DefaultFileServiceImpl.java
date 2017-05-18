package cn.ac.iscas.cmt.v2.model.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSink;
import com.google.common.io.CharSource;
import com.google.common.io.Files;

import cn.ac.iscas.cmt.v2.model.dao.FileSourceDAO;
import cn.ac.iscas.cmt.v2.model.dao.io.Md5CachingByteSource;
import cn.ac.iscas.cmt.v2.model.entity.FileSource;
import cn.ac.iscas.cmt.v2.util.Constants;


@Service
public class DefaultFileServiceImpl implements FileService {

	private String fileRoot;

	private String basePath;
	@Autowired
	private FileSourceDAO fileSourceDAO;

	@PostConstruct
	private void init() {
		
		fileRoot = "f:/paper/scripts";//this.getClass().getClassLoader().getResource("").getPath();
		basePath = this.getClass().getClassLoader().getResource("").getPath()+"/check";
	}

	@Override
	public String saveFile(ByteSource byteSource) throws IOException {
		Md5CachingByteSource md5ByteSource = Md5CachingByteSource
				.fromImmutableByteSource(byteSource);
		String md5 = md5ByteSource.getMd5();
		if (fileSourceDAO.findFileByMd5(md5) != null) {
			return md5ByteSource.getMd5();
		}
		String relativePath = calculateRelativePath(FileServiceUtils
				.randomString());
		System.out.println(fileRoot);
		File target = new File(fileRoot + File.separator + relativePath);
		FileUtils.forceMkdir(target.getParentFile());
		ByteSink byteSink = Files.asByteSink(target);
		md5ByteSource.copyTo(byteSink);
		FileSource file = new FileSource();
		file.setMd5(md5);
		file.setCredential(relativePath);
		file.setTyp("file");
		fileSourceDAO.save(file);
		return md5;
	}

	@Override
	public ByteSource findFile(String md5) throws FileNotFoundException {
		FileSource source = fileSourceDAO.findFileByMd5(md5);
		if (source == null) {
			System.out.println(md5);
			throw new FileNotFoundException();
		}
		File file = new File(fileRoot+ File.separator+source.getTyp() + File.separator + source.getCredential());
		
		if (!file.exists()) {
			System.out.println(file.getAbsolutePath());
			throw new FileNotFoundException();
		}
		return Files.asByteSource(file);
	}

	private String calculateRelativePath(String name) {
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/hh/mm/ss");
		Calendar calendar = Calendar.getInstance();
		String path = sdf.format(calendar.getTime()) + "/" + name;
		return path;
	}

/*	@Override
	public String generateDownloadURL(String md5) {
		return String
				.format(configService
						.getConfigAsString(ConfigKeys.SERVICE_FILE_URL_DOWNLOAD_FORMAT),
						configService.getConfigAsString(ConfigKeys.SERVER_ROOT),
						md5);
	}*/

	@Override
	public String readFileContent(String md5) throws IOException {
		ByteSource byteSource = findFile(md5);
		CharSource charSource = byteSource.asCharSource(Charset
				.forName(Constants.FILE_DEFAULT_CHARSET));
		return charSource.read();
	}

	@Override
	public String writeFileContent(String content) throws IOException {
		String md5 = FileServiceUtils.md5(content);
		if (fileSourceDAO.findFileByMd5(md5) != null) {
			return md5;
		}
		String relativePath = calculateRelativePath(FileServiceUtils
				.randomString());
		File target = new File(fileRoot + File.separator + relativePath);
		FileUtils.forceMkdir(target.getParentFile());
		ByteSink byteSink = Files.asByteSink(target);
		CharSink charSink = byteSink.asCharSink(Charset
				.forName(Constants.FILE_DEFAULT_CHARSET));
		charSink.write(content);
		FileSource file = new FileSource();
		file.setMd5(md5);
		file.setCredential(relativePath);
		file.setTyp("file");
		fileSourceDAO.save(file);
		return md5;
	}

	@Override
	public String savePuppetModule(ByteSource byteSource) throws IOException{
		String absolutePath = basePath;//TODO calculateRelativePath(FileServiceUtils
//				.randomString());
		InputStream zin = byteSource.openStream();
//		String absolutePath = fileRoot + File.separator + relativePath;
		FileServiceUtils.Unzip(zin, absolutePath);
		
		return "success";
	}
	@Override
	public String savePuppetFile(ByteSource byteSource) throws IOException{
		Md5CachingByteSource md5ByteSource = Md5CachingByteSource
				.fromImmutableByteSource(byteSource);
		String md5 = md5ByteSource.getMd5();
		if (fileSourceDAO.findFileByMd5(md5) != null) {
			return md5ByteSource.getMd5();
		}
		String relativePath = calculateRelativePath(FileServiceUtils
				.randomString());
		InputStream zin = byteSource.openStream();
		String absolutePath = fileRoot + File.separator + relativePath;
		FileServiceUtils.Unzip(zin, absolutePath);
		
		FileSource file = new FileSource();
		file.setMd5(md5);
		file.setCredential(relativePath);
		file.setTyp("puppet");
		fileSourceDAO.save(file);
		return md5;
	}
	@Override
	public ZipBuilder buildZipBuilder(){
		String relativePath = calculateRelativePath(FileServiceUtils
				.randomString()) + ".zip";
		String absolutePath = fileRoot + File.separator + relativePath;
		return new ZipBuilder(absolutePath, relativePath);
	}
	@Override
	public String saveZip(ZipBuilder builder) throws IOException{
		FileServiceUtils.zip(builder.getTargetFilePath(), builder.getFiles());
		ByteSource zipFile = Files.asByteSource(new File(builder.getTargetFilePath()));
		Md5CachingByteSource md5ByteSource = Md5CachingByteSource
				.fromImmutableByteSource(zipFile);
		String md5 = md5ByteSource.getMd5();
		if (fileSourceDAO.findFileByMd5(md5) != null) {
			FileServiceUtils.delete(builder.getTargetFilePath());
			return md5ByteSource.getMd5();
		}
		FileSource file = new FileSource();
		file.setMd5(md5);
		file.setCredential(builder.getRelativePath());
		file.setTyp("file");
		fileSourceDAO.saveAndFlush(file);
		return md5;
	}
}
