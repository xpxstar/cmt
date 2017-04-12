package cn.ac.iscas.cmt.v2.model.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import cn.ac.iscas.cmt.v2.model.entity.Synonyms;


public interface SynonymsDao extends PagingAndSortingRepository<Synonyms, Long> {

	List<Synonyms> findByName(String name);
	
	Synonyms findBySynonyms(String synonyms);
}
