package cn.ac.iscas.cmt.v2.model.service;

import java.io.InputStream;

import com.google.common.base.Optional;

public interface CommandRunner {
	public Optional<InputStream> run(String command);
}
