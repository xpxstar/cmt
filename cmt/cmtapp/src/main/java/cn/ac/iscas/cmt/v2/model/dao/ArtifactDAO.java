package cn.ac.iscas.cmt.v2.model.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import cn.ac.iscas.cmt.v2.model.entity.Artifact;
public interface ArtifactDAO extends PagingAndSortingRepository<Artifact, Long> {
	@Query(value="select art from Artifact art where art.category like %:cate% AND art.type = :type order by art.alldown desc")
	Page<Artifact> findByCategory(@Param("cate")String cate,@Param("type")String type, Pageable pageable);
//	Page<Artifact> findByName(Pageable pageable);

	@Query(value="select art from Artifact art where art.id in (:ids) and art.category like %:cate%")
	List<Artifact> findIdsInCategory(@Param("cate")String cate, @Param("ids")List<Long> ids);
	
	@Query(value="select art from Artifact art where art.type = :type ")
	Page<Artifact> findArtifactByType(@Param("type")String type,Pageable page);
	
	@Query(value="select art from Artifact art where art.type = :type ")
	List<Artifact> findArtifactByType(@Param("type")String type);
}
