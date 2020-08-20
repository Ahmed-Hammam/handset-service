package com.handset.service;

import java.util.List;
import java.util.Map;

import com.handset.model.Handset;

public interface HandsetService {

	List<Handset> doSearch(Map<String,Object> params);
}
