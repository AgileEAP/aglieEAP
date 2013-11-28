package com.agileEAP.portal.mongodb;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import org.springframework.transaction.annotation.Transactional;  
import org.springframework.stereotype.Repository;

//加自定义方法了启用事物报错，spring.data.mongodb暂不支持事物？？
//@Transactional  
@Repository
public interface ProcessDefineRepository extends MongoRepository<ProcessDefine, String>{
	@Query("{'name':{ '$regex': \"*?0*\", '$options': 'i'}}")
	List<ProcessDefine> searchByNameLike(String like);
	
	@Query("{ 'name':{'$regex':?2,'$options':'i'}}")
	public Page<ProcessDefine> findByName(String name,Pageable page);
	
	@Query("{name:{ $regex: \"*\"+?0+\"*\", $options: 'i'}}")
	public List<ProcessDefine> searchByName(String name);	
	
	List<ProcessDefine> findByName(String name);
}
