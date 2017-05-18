package cn.ac.iscas.cloudeploy.v2.puppet.compare.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import cn.ac.iscas.cloudeploy.v2.model.util.FileUtil;
import cn.ac.iscas.cloudeploy.v2.puppet.compare.ast.entity.ASTAction;
import cn.ac.iscas.cloudeploy.v2.puppet.compare.ast.entity.Rule;
import cn.ac.iscas.cloudeploy.v2.puppet.compare.ast.entity.Smell;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.PuppetParser;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ResouceType;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTBase;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTBlockExpression;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTCaseStatement;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTFunction;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTIfStatement;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTNop;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTRelationship;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTResource;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTResourceInstance;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTResourceParam;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ASTVarDef;
import cn.ac.iscas.cloudeploy.v2.puppet.transform.ast.ElementTop;

/**
 * @author admin
 *
 */
public class ASTAnalyzeImpl {
	String  classpath = this.getClass().getResource("/").getPath();
	String BasePath =classpath+"/check";
	
	/**
	 * @param after
	 * @param before
	 * @param range 变动的行范围
	 * @return
	 */
	private Map<String, Rule> ruleMap;
	private Map<String, String> paramMap;
	PuppetParser parser;
	/*static{
		Rule titleRule = new Rule("resource", "file", "title","Format Error ", ".*", "ERROR");
		Rule pathRule = new Rule("resource", "file", "path","Format Error ", "^/.*", "ERROR");
		Rule modeRule = new Rule("resource", "file","mode", "Format Error ", "^[0-9]{3,4}", "ERROR");
		Rule ensureRule = new Rule("resource", "file","ensure", "Illegal Value ", "absent|present|file|directory|link", "ERROR");
		Rule attRule = new Rule("resource", "file", "attribute","Missing Attribute ", "content", "ERROR");
		ruleMap = new HashMap<>();
		ruleMap.put("resource-file-title", titleRule);
		ruleMap.put("resource-file-path", pathRule);
		ruleMap.put("resource-file-mode", modeRule);
		ruleMap.put("resource-file-ensure", ensureRule);
		ruleMap.put("resource-file-attribute", attRule);
		
	}*/
	public ASTAnalyzeImpl() {
		paramMap = new HashMap<>();
		ruleMap = new HashMap<>();
		parser = new PuppetParser();
		System.out.println(classpath);
		String rulePath = classpath+"rules.json";
		JSONTokener jsonTokener = null;
		try {
			jsonTokener = new JSONTokener(new FileReader(new File(rulePath)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  
        JSONArray jsonArray = new JSONArray(jsonTokener);
		for (int i = 0;i<jsonArray.length();i++) {
			Object object = jsonArray.get(i);
			Rule tmp = new Rule((JSONObject)object);
			ruleMap.put(tmp.genKey(), tmp);
		}
	}
	
	public Map<String,List<Smell>> analyzeAST(String module){
		FileUtil nalist = new FileUtil(BasePath,module,"filelist",true);
		Map<String, List<Smell>> result = new HashMap<>();
		String path = nalist.readLine();
		while (path != null) {
				System.out.println(module+" : "+path);
				ResouceType rt = getResourceTypeFromFile(BasePath,module,path+".ast");
				result.put(path.substring(10,path.length()-3), analyze(rt));
			path = nalist.readLine();	
		}
		return  result;
	}
	
	public ResouceType getResourceTypeFromFile(String path,String module,String filename){
		File file = FileUtil.GenerateFile(path,module+"/ast",filename);
		return parser.extractTree(file);
	}
	
	public List<Smell> analyze(ResouceType code){
		List<Smell> result = new ArrayList<>();
		if (code == null) {
			return result;
		}
		unitedParam(code);
		analyzeBlockExpression(code.getCode(),result);
		return result;
	}
	
	/**
	 * @param code
	 * @param class1
	 * @param result
	 */
	private void analyzeBlockExpression(ASTBlockExpression code,List<Smell> result) { 
		//compare blockExpression
		if (code == null || code instanceof ASTNop) {
			return ;
		}
		List<ASTBase> children = code.getChildren();
		for (ASTBase child : children) {
			analyzeBase(child,result);
		}
	}
	
	public void analyzeBase(ASTBase base,List<Smell> result){
		if (base instanceof ASTResource) {
			analyzeResource((ASTResource)base,result);
		}else if (base instanceof ASTRelationship) {
			analyzeRelationship((ASTRelationship)base,result);
		}else if (base instanceof ASTFunction) {
			analyzeFunction((ASTFunction)base,result);
		}else if (base instanceof ASTIfStatement) {
			analyzeIfStatement((ASTIfStatement)base,result);
		}else if (base instanceof ASTVarDef) {
			analyzeVarDef((ASTVarDef)base,result);
		}else if (base instanceof ASTCaseStatement) {
			analyzeCaseStatement((ASTCaseStatement)base,result);
//		}else if (base instanceof ASTNot) {
//			analyzeNot((ASTNot)base,result);
//		}else if (base instanceof ASTSelector) {
//			analyzeSelector((ASTSelector)base,result);
		}else {
			System.err.println("others:"+base.getClass().toString());
		}
		
	}
	
	/*private void analyzeSelector(ASTSelector after, List<Smell> result) {
		String aname = ASTAction.value;
		if (after.getParam() instanceof ElementTop ||before.getParam() instanceof ElementTop) {
			compareBase(after.getParam(), before.getParam(), result, afline, bfline);
			aname = after.getParam().elementName();
		}else{
			String astate = after.getParam().changeString();
			aname = after.getParam().elementName();
			String bstate = before.getParam().changeString();
			if (!astate.equals(bstate)) {
				Smell re = new Smell(ASTAction.selector, ASTAction.test,ASTAction.test,ASTAction.modify,
						new DiffDetail(ASTAction.string,bstate ,astate));
				result.add(re);
			}
		}
			DiffBuilder builder = Smell.builder();
			builder.type(ASTAction.selector)
				.position(ASTAction.value)
				.name(aname);
			ASTBase bvalue = before.getValues();
			ASTBase avalue = after.getValues();
			if (bvalue instanceof ASTASTArray && avalue instanceof ASTASTArray ) {
				Map<String,ASTBase> unvisited =new HashMap<>();
				for (ASTBase param : ((ASTASTArray)bvalue).getChildren()) {
					if (param instanceof ASTResourceParam) {
						ASTResourceParam pp = (ASTResourceParam)param;
						unvisited.put(pp.paramString(),pp.getValue());
					}
				}
				Smell re = null;
				for (ASTBase param :((ASTASTArray)avalue).getChildren()) {
					if (param instanceof ASTResourceParam) {
						ASTResourceParam pp = (ASTResourceParam)param;
						String key = pp.paramString();
						ASTBase target = unvisited.get(key);
						if (null != target && target instanceof ElementTop) {
							compareBase(pp.getValue(), target, result, afline, bfline);
						}else {
							re = compareValue(pp.getValue(), target, builder);
							if (re != null ) {
								re.setName(aname);
								result.add(re);
							}						
						}
						unvisited.remove(pp.paramString());
					}
				}
				for (String key:unvisited.keySet()) {
					re = compareString(null,key,builder);
					re.setName(aname);
					result.add(re);
				}
			}else {
				System.err.println("Selector Value diff: "+aname);
			}
	}

	private void analyzeDiffType(ASTBase after, ASTBase before, List<Smell> result) {
		Smell re = new Smell();
		re.setType(ASTAction.type);
		re.setPosition(getType(before));
		re.setName(getType(after));
		re.setAction(ASTAction.modify);
		re.setValue(new DiffDetail(ASTAction.string,before.getClass().getSimpleName(),after.getClass().getSimpleName()));
		result.add(re);
	}
	
	private String getType(ASTBase target) {
		if(target instanceof ASTResource){
			return ASTAction.resource;
		}else if (target instanceof ASTRelationship) {
			return ASTAction.relation;
		}else if (target instanceof ASTFunction) {
			return ASTAction.function;
		}else if (target instanceof ASTIfStatement) {
			return ASTAction.ifstatement;
		}else if (target instanceof ASTVarDef) {
			return ASTAction.vardef;
		}else if (target instanceof ASTCaseStatement) {
			return ASTAction.casestatement;
		}else if (target instanceof ASTNot) {
			return ASTAction.not;
		}else 
			return "";
	}

	public void analyzeNot(ASTNot after,ASTNot before,List<Smell> result,LinePair afline, LinePair bfline){
		compareBase(after.getValue(), before.getValue(), result, afline, bfline);
	}*/
	/** conpare resource
	 * @param after
	 * @param before
	 * @param result
	 */
	public void analyzeResource(ASTResource after,List<Smell> result){
		String target=ASTAction.resource+"-"+after.getType();
		List<ASTBase> achildren = after.getInstances().getChildren();
		int asize = achildren.size();
		for(int i = 0;i < asize ;i++){
			if (achildren.get(i) instanceof ASTResourceInstance ) {
				ASTResourceInstance ainstance = (ASTResourceInstance)achildren.get(i);
				//compare title start
				Smell re = null;
				Rule rule=ruleMap.get(target+"-"+ASTAction.title);
				if(rule != null){
					re = analyzeValue(ainstance.getTitle(),rule);
					if (re != null) {
						result.add(re);
					}
				}
				Set<String> unvisited =new HashSet<>();
				for (ASTBase param : ainstance.getParameters().getChildren()) {
					if (param instanceof ASTResourceParam) {
						ASTResourceParam pp = (ASTResourceParam)param;
						unvisited.add(pp.paramString());
					}
				}
				Rule abRule = ruleMap.get(target+"-absolute");
				if (abRule != null) {
					String[] paramNames = abRule.getRule().split(",");
					for (String str : paramNames) {
						if(!unvisited.contains(str)){
							Smell sm = abRule.parseSmell();
							sm.setAttribute(str);
							sm.setLine(after.getLine());
							result.add(sm);
						}
					}
				}
				Rule proRule = ruleMap.get(target+"-proposal");
				if (proRule != null) {
					String[] paramNames = proRule.getRule().split(",");
					for (String str : paramNames) {
						if(!unvisited.contains(str)){
							Smell sm = proRule.parseSmell();
							sm.setAttribute(str);
							sm.setLine(after.getLine());
							result.add(sm);
						}
					}
				}
				for (ASTBase param : ainstance.getParameters().getChildren()) {
					if (param instanceof ASTResourceParam) {
						ASTResourceParam pp = (ASTResourceParam)param;
						ASTBase tBase = pp.getValue();
						if (null != tBase && tBase instanceof ElementTop) {
							analyzeBase(pp.getValue(), result);
						}else {
							Rule subRule = ruleMap.get(target+"-"+pp.paramString());
							if(subRule != null){
								re = analyzeValue(pp.getValue(),subRule);
								if (re != null ) {
									result.add(re);
								}
							}						
						}
						unvisited.remove(pp.paramString());
					}
				}
				
			}else {
				System.err.println("Instance Smell: "+achildren.get(i).getLine());
			}
		}
		
	}
	/**
	 * @param after
	 * @param before
	 * @param result
	 * 
	 */
	 private void analyzeFunction(ASTFunction after, List<Smell> result) {
		String target= ASTAction.function+"-"+ASTAction.name;
		Rule funRule = ruleMap.get(target+"-proposal");
		if (funRule != null) {
			Smell sm = funRule.parseSmell();
			sm.setLine(after.getLine());
			result.add(sm);
		}
	}
	
	private void analyzeRelationship(ASTRelationship after, List<Smell> result) {
		String target= ASTAction.relation+"-"+ASTAction.order;
		Rule funRule = ruleMap.get(target+"-proposal");
		if (funRule != null) {
			Smell sm = funRule.parseSmell();
			sm.setLine(after.getLine());
			result.add(sm);
		}
	}
	/*
//	private void analyzeRelationship(ASTRelationship after, ASTRelationship before, List<Smell> result) {
//		System.err.println("relationship");
//	}
	*/
	private void analyzeIfStatement(ASTIfStatement after, List<Smell> result) {
		String target=ASTAction.ifstatement+"-"+ASTAction.iftest;
		if (after.getTest() instanceof ElementTop) {
			analyzeBase(after.getTest(), result);
		}else{
			Rule ifRule = ruleMap.get(target+"-"+ASTAction.test);
			if(ifRule != null){
				ASTBase bstate = after.getTest();
				Smell re = analyzeValue(bstate, ifRule);
				if (re!=null) {
					result.add(re);
				}
			}
		}
		analyzeBlockExpression(after.getStatements(),  result);
		if (null != after.getIfStatementElse()) {
			analyzeBlockExpression(after.getIfStatementElse().getStatements(), result);
		}
	}
	
	/**
	 * @param after
	 * @param before
	 * @param result
	 * @param afline
	 * @param bfline
	 *
	 */
	public void analyzeCaseStatement(ASTCaseStatement after,List<Smell> result){
		String target=ASTAction.casestatement+"-"+ASTAction.cases;
		if (after.getTest() instanceof ElementTop) {
			analyzeBase(after.getTest(), result);
		}else{
			String astate = after.getTest().changeString();
			Rule caseRule = ruleMap.get(target+"-"+astate);
			if(caseRule != null){
				ASTBase bstate = after.getTest();
				Smell re = analyzeValue(bstate, caseRule);
				if (re!=null) {
					result.add(re);
				}
			}
		}/*
			List<ASTBase> aopts =  after.getOptions().getChildren();
			List<ASTBase> bopts =  before.getOptions().getChildren();
			Map<String, ASTBlockExpression> unvisited = new HashMap<>();
			for (int i = 0; i < bopts.size() ; i++) {
				if (bopts.get(i) instanceof ASTCaseOpt) {
					ASTCaseOpt opt = (ASTCaseOpt)bopts.get(i);
					unvisited.put(opt.getValue().changeString(), opt.getStatements());
				}
			}
			DiffBuilder builder = Smell.builder();
			builder.type(ASTAction.casestatement).position(ASTAction.option);
			for (ASTBase param :  aopts) {
				if (param instanceof ASTCaseOpt) {
					ASTCaseOpt aco = (ASTCaseOpt)param;
					String key = aco.getValue().changeString();
					ASTBlockExpression target = unvisited.get(key);
					if (target == null) {
						Smell re =  compareString(key,null,builder);
						re.setName(atest);
						result.add(re);
						continue;
					}
					compareBlockExpression(((ASTCaseOpt) param).getStatements(), target, result, afline, bfline);
					unvisited.remove(key);
				}
			}
			for (String key:unvisited.keySet()) {
				Smell re =  compareString(null,atest,builder);
				re.setName(key);
				result.add(re);
			}*/
	}
	public void analyzeVarDef(ASTVarDef var,List<Smell> result){
		if (var.getName() instanceof ElementTop) {
			analyzeBase(var.getName(), result);
		}else {
			String aname = var.getName().changeString();
			if (var.getValue() instanceof ElementTop ) {
				analyzeBase(var.getValue(), result);
			}else{
				String avalue = var.getValue().changeString();
				paramMap.put(aname, avalue);
			}
		}
	}
	/**compare arguments of two resourcetype
	 * @param after
	 * @param before
	 * @param result
	*/
	public void unitedParam(ResouceType code){
		Map<String, ASTBase> map = code.getArguments();
		for (String arg: map.keySet()) {
			ASTBase target  = map.get(arg);
			paramMap.put(arg, target.changeString());
		}
	}
	
	
	/**
	 * ASTString
	 * @param after 之前
	 * @param before 之后
	 * @param builder builder 前缀
	 * @return
	 */
	public Smell analyzeValue(ASTBase value,Rule  pattern ){
		//check ASTString or ASTConcat
		if (value ==null) {
			return null;
		} 
		Pattern r = Pattern.compile(pattern.getRule());
		
		String astring = (null==value)?null:value.changeString();
		if (astring ==null) {
			return null;
		}
		Smell smell = null;
		Matcher m = r.matcher(astring); 
		if (!m.matches()) {
			smell = pattern.parseSmell();
			smell.setLine(value.getLine());
		}
		return smell;
	}
	/**
	 * ASTString
	 * @param after 之前
	 * @param before 之后
	 * @param builder builder 前缀
	 * @return
	 
	public Smell compareString(String after,String before){
		//check ASTString or ASTConcat
		if (after ==null && before ==null) {
			return null;
		}
		String astring = after;
		String bstring = before;
		Smell diff = null;
		if (null == bstring) {
			diff = builder.build();
			diff.setAction(ASTAction.add);
			diff.setValue(new DiffDetail(ASTAction.string, "",astring));
		}else if (null == astring) {
			diff = builder.build();
			diff.setAction(ASTAction.delete);
			diff.setValue(new DiffDetail(ASTAction.string, bstring,""));
		}else if (!astring.equals(bstring)) {
			diff = builder.build();
			diff.setAction(ASTAction.modify);
			diff.setValue(new DiffDetail(ASTAction.string, bstring,astring));
		}
		return diff;
	}
	*/
}
