package cn.ac.iscas.cmt.v2.model.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import cn.ac.iscas.cmt.v2.model.entity.Artifact;
public interface ArtifactDAO extends PagingAndSortingRepository<Artifact, Long> {
//	Page<Artifact> findByName(Pageable pageable);
}
