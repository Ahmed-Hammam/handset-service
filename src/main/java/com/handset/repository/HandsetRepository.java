package com.handset.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.handset.model.Handset;

@Repository
public interface HandsetRepository extends ElasticsearchRepository<Handset,String>{

}
