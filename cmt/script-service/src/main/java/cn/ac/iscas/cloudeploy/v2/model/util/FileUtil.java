package cn.ac.iscas.cloudeploy.v2.model.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileUtil {
	String fileName = null;
	BufferedReader reader = null; 
	BufferedWriter writer = null;
	ObjectOutputStream objOut = null;
	ObjectInputStream objIn = null;
	static String BasePath = System.getProperty("user.dir"); 
	boolean append = false;
	public static File GenerateFile(String directory,String fileName){
		File file=new File(BasePath+"/commit/"+directory,fileName);
		return file;
	}
	public static File GenerateFile(String base,String module,String fileName){
		File file=new File(base+"/"+module,fileName);
		return file;
	}
	public static File MakeDir(String directory,String fileName){
		File file=new File(System.getProperty("user.dir")+"/commit/"+directory,fileName);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}
	public static File MakeDir(String base,String module,String fileName){
		File file=new File(base+"/"+module,fileName);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}
	public FileUtil(String fileName) {
		super();
		this.fileName = fileName;
	}
	public FileUtil(String fileName,boolean append) {
		super();
		this.fileName = fileName;
		this.append = append;
	}
	public FileUtil(String basePath,String directory,String fileName,boolean absolute) {
		super();
		this.append = false;
		File file = null;
		if (absolute) {
			file=new File(basePath+"/"+directory);
		}else {
			file=new File(System.getProperty("user.dir")+"/"+basePath,directory);
		}
		if(!file.exists()){
			file.mkdirs();
		}
		this.fileName = file.getAbsolutePath()+"\\"+fileName;
	}
	public FileUtil(String directory,String fileName,boolean absolute) {
		super();
		this.append = false;
		File file = null;
		if (absolute) {
			file=new File(directory);
		}else {
			file=new File(System.getProperty("user.dir")+"\\commit",directory);
		
		}
		if(!file.exists()){
			file.mkdirs();
		}
		this.fileName = file.getAbsolutePath()+"\\"+fileName;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void close(){
		try {
			if(this.reader!=null)
				this.reader.close();
			if(this.writer!=null)
				this.writer.close();
			if(this.objOut!=null)
				this.objOut.close();
			if(this.objIn!=null)
				this.objIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//读取文件，一次读一行
	public String readLine(){
		if(this.reader == null){
			try {
				this.reader = new BufferedReader(new FileReader(this.fileName));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			return this.reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void writeLine(String str){
		if(this.writer == null){
			try {
				if(append) 
					this.writer = new BufferedWriter(new FileWriter(this.fileName,true));
				else
					this.writer = new BufferedWriter(new FileWriter(this.fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			this.writer.write(str);
			this.writer.newLine();
		} catch (IOException e) {
			try {
				this.writer.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	// 写入数据不换行
	public void write(String str){
		if(this.writer == null){
			try {
				if(append) 
					this.writer = new BufferedWriter(new FileWriter(this.fileName,true));
				else
					this.writer = new BufferedWriter(new FileWriter(this.fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			this.writer.write(str);
			//this.writer.newLine();
		} catch (IOException e) {
			try {
				this.writer.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	// 写入换行
	public void newLine(){
		if(this.writer == null){
			try {
				if(append) 
					this.writer = new BufferedWriter(new FileWriter(this.fileName,true));
				else
					this.writer = new BufferedWriter(new FileWriter(this.fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			//this.writer.write(str);
			this.writer.newLine();
			
		} catch (IOException e) {
			try {
				this.writer.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	//向文件中写入对象
	public void writeObj(Object obj){
		try {
			this.objOut = new ObjectOutputStream(new FileOutputStream(this.fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			objOut.writeObject(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//从文件中读取对象
	public Object readObj(){
		Object obj = null;
		try {
			this.objIn = new ObjectInputStream(new FileInputStream(this.fileName));
			obj=objIn.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return obj;
	}
	public void flush(){
		try {
			this.writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		FileUtil out = new FileUtil("test.txt",true);
		out.writeLine("adasf");
	}
}

