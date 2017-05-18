package cn.ac.iscas.cmt.v2.util;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.jcabi.ssh.SSHByPassword;
import com.jcabi.ssh.Shell;
@Service
public class ShellCommandRunner implements CommandRunner{
	private Shell shell;
	public ShellCommandRunner() throws UnknownHostException {
		shell = new SSHByPassword("133.133.134.174", 22, "root", "centos");
	}
	/* (non-Javadoc)
	 * @see cn.ac.iscas.cmt.v2.util.CommandRunner#run(java.lang.String)
	 */
	@Override
	public Optional<InputStream> run(String command) {
		try {
			Pipe pipe = Pipe.open();
			OutputStream stdout = Channels.newOutputStream(pipe.sink());
			shell.exec(command, null, stdout, null);
			return Optional.of(Channels.newInputStream(pipe.source()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Optional.absent();
	}
	/**
	 * get run result by String List
	 * @return
	 */
	@Override
	public List<String> tidyRun(String command){
		return parseString(run(command));
	}
	/**
	 * get run result by String List
	 * @return
	 */
	@Override
	public String normalRun(String command){
		Optional<InputStream> re  = run(command);
		StringBuffer result = new StringBuffer();
		if(re.isPresent()){
			Scanner scanner = new Scanner(re.get());
			while(scanner.hasNext()){
				result.append(scanner.nextLine());
			}
			scanner.close();
		}else{
			System.err.println("shellCommandRunner: parseString: Optional is not present");
		}
		return result.toString();
	}
	/**
	 * change Optional-Stream to List Sting
	 * @param re
	 * @return
	 */
	private List<String> parseString(Optional<InputStream> re){
		List<String> result = new ArrayList<>();
		if(re.isPresent()){
			Scanner scanner = new Scanner(re.get());
			while(scanner.hasNext()){
				result.add(scanner.nextLine());
			}
			scanner.close();
		}else{
			System.err.println("shellCommandRunner: parseString: Optional is not present");
		}
		return result;
	}
}
