package cn.ac.iscas.cloudeploy.v2.puppet.compare.service;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import cn.ac.iscas.cloudeploy.v2.model.entity.crawl.CProcess;
import cn.ac.iscas.cloudeploy.v2.model.util.FileUtil;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.PuppetParser;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ResouceType;

/**
 * @author admin
 *
 */
public class GenModuleAST {
	PuppetParser parser;
	String rubyEnvironment;
	String PuppetParserSource;
	String PuppetAnalyseRuby;
	String BasePath;
	public GenModuleAST() {
		rubyEnvironment = "E:\\ruby\\Ruby21-x64\\bin\\ruby.exe";
		PuppetParserSource = "D:\\puppet_parser\\resource";
		PuppetAnalyseRuby = "D:\\puppet_parser\\main\\single-file-scanner.rb";
		BasePath = this.getClass().getResource("/").getPath().substring(1)+"check";
		parser = new PuppetParser();
	}
	
	/**遍历指定的module 文件列表
	 * @param module
	 */
	private int  scanModuleList(String path, String module){
		int filenum = 0;
		File ff = new File(path+"/"+module+"/manifests");
		FileUtil nalist = new FileUtil(path,module,"filelist",true);
		File astpath = FileUtil.MakeDir(path,module,"/ast/manifests");
		LinkedList<File> list = new LinkedList<>();
		list.add(ff);
		File tmp = null;
		while (!list.isEmpty()) {
			tmp = list.removeFirst();
			File[] files = tmp.listFiles();
			for (File f : files) {
				if (f.isDirectory()) {
					list.add(f);
					FileUtil.MakeDir(astpath.getAbsolutePath(), "", f.getName());
				}else if (f.getName().endsWith("pp")) {
					String apath = "manifests"+f.getAbsolutePath().split("manifests")[1];
					apath = apath.replaceAll("\\\\", "/");
					nalist.writeLine(apath);
					nalist.flush();
					filenum++;
				}
			}
		}
		nalist.close();
		return filenum;
	}
	
	public void generateAST(String module){
		scanModuleList(BasePath, module);
		
		FileUtil nalist = new FileUtil(BasePath,module,"filelist",true);
		String path = nalist.readLine();
		File astpath = FileUtil.MakeDir(BasePath,module,"/ast/manifests");
		
		while (path != null) {
				System.out.println(module+" : "+path);
				parserFile(BasePath,module,path);
			path = nalist.readLine();	
		}
		
	}
	
	public void generateAST(String module,CProcess cp){
		FileUtil nalist = new FileUtil(module,"filelist",false);
		String path = nalist.readLine();
		int count = 1;
		while (path != null) {
			if (++count > cp.getFile()) {
				System.out.println(module+" : "+path);
				parserFile(module,path,cp);
				cp.setFile(count);
				cp.setCommit(1);
			}
			path = nalist.readLine();	
		}
		
	}
	public void parserFile(String Base,String module,String path){
			System.out.println(path);
			String input = Base+"/"+module+"/"+path;
			String output = Base+"/"+module+"/ast/"+path+".ast";
			try {
				parser.usingRubyAnalyseSingle(rubyEnvironment, PuppetParserSource, PuppetAnalyseRuby, input,output);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
	}
	public void parserFile(String module,String path){
		File files = FileUtil.GenerateFile(module,path);
		File astpath = FileUtil.MakeDir(module,"/ast");
		File[] commits = files.listFiles();
		for (File file : commits) {
			if (file.isFile() ) {
				try {
					System.out.println(file.getName());
					parser.usingRubyAnalyseSingle(rubyEnvironment, PuppetParserSource, PuppetAnalyseRuby, file.getAbsolutePath(), astpath.getAbsolutePath()+"/"+path);
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println(module+" : "+file.getName());
				}
				
			}
		}
		
	}
	public void parserFile(String module,String path,CProcess cp){
		File files = FileUtil.GenerateFile(module+"/"+path,"/raw");
		File astpath = FileUtil.MakeDir(module+"/"+path,"/ast/");
		File[] commits = files.listFiles();
		int count = 1;
		for (File file : commits) {
			if (file.isFile() && (++count > cp.getCommit())) {
				try {
					System.out.println(file.getName());
					parser.usingRubyAnalyseSingle(rubyEnvironment, PuppetParserSource, PuppetAnalyseRuby, file.getAbsolutePath(), astpath.getAbsolutePath()+"/"+file.getName());
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println(module+" : "+cp.getFile()+" : "+count+" : "+file.getName());
				}
			}
		}
	}
	public ResouceType getResourceType(String module,String path,String sha){
		File file = FileUtil.GenerateFile(module+"/"+path+"/ast",sha);
		return parser.extractTree(file);
	}
	
	public ResouceType getResourceTypeFromFile(String path,String module,String filename){
		File file = FileUtil.GenerateFile(path,module+"/ast",filename);
		return parser.extractTree(file);
	}
	
}
