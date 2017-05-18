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
import org.eclipse.jdt.core.dom.ThisExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cn.ac.iscas.cmt.v2.model.dao.ArtifactDAO;
import cn.ac.iscas.cmt.v2.model.entity.Artifact;
import cn.ac.iscas.cmt.v2.util.CommandRunner;

/**
 * @author xpxstar@gmail.com
 *	制品的业务逻辑
 */
@Service
public class AnalyzeServiceImpl{
	
	@Autowired
	private CommandRunner runner;
	String  BasePath = this.getClass().getResource("/").getPath().substring(1)+"check ";
	private static String Lint_Command = "puppet-lint ";
	
	private static String Syntax_Command = "puppet parser validate /cmt/";
	
	public Map<String, List<String>> lintCheck(String name){
		Map<String, List<String>> result = new HashMap<>();
		List<String> runResult = runner.tidyRun(Lint_Command+BasePath+ name);
		for (String string : runResult) {
			String[] lines = string.split(" - ");
			String value = lines[1];
			String key = lines[0].split("manifests/")[1];
			key = key.substring(0, key.length()-3);
			if(result.containsKey(key)){
				result.get(key).add(value);
			}else{
				List<String> report = new ArrayList<>();
				report.add(value);
				result.put(key, report);
			}
		}
		return result;
	}
	public String syntaxCheck(String file){
		String runResult = runner.normalRun("puppet parser validate /cmt/install.pp");
		return runResult;
	}
}
