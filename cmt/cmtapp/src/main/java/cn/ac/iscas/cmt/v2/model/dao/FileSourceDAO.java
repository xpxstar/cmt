package cn.ac.iscas.cmt.v2.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.ac.iscas.cmt.v2.model.entity.FileSource;

public interface FileSourceDAO extends
		JpaRepository<FileSource, Long> {
	@Query(value="select ff from FileSource ff where ff.md5 = ?")
	FileSource findFileByMd5(String md5);
}
