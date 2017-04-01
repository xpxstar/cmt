package cn.ac.iscas.cmt.v2.model.service;
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
	@Override
	public Optional<InputStream> run(String command) {
		try {
			Pipe pipe = Pipe.open();
			OutputStream stdout = Channels.newOutputStream(pipe.sink());
			new Shell.Safe(shell).exec(command, null, stdout, null);
			return Optional.of(Channels.newInputStream(pipe.source()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Optional.absent();
	}
	public List<String> parseString(Optional<InputStream> re){
		List<String> result = new ArrayList<>();
		if(re.isPresent()){
			Scanner scanner = new Scanner(re.get());
			while(scanner.hasNext()){
				result.add(scanner.nextLine());
			}
			scanner.close();
		}
		return result;
	}
}
