package cn.ac.iscas.cmt.v2.util;

import java.io.InputStream;
import java.util.List;

import com.google.common.base.Optional;

public interface CommandRunner {
	/**
	 * get origin result of runner
	 * @param command
	 * @return
	 */
	public Optional<InputStream> run(String command);
	/**
	 * get tidy result of command
	 * @param command
	 * @return
	 */
	public List<String> tidyRun(String command);
	/**
	 * get 
	 * @param command
	 * @return
	 */
	public String normalRun(String command);}
