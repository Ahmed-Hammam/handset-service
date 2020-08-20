package com.handset.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.handset.model.Handset;
import com.handset.repository.HandsetRepository;
import com.handset.service.HandsetService;

@RestController
@RequestMapping("/handsets")
public class HandSetController {

	@Autowired
	private HandsetService handsetService;
	@Autowired private HandsetRepository repo;
	
	@GetMapping("/search")
	public ResponseEntity<List<Handset>> search(@RequestParam Map<String,Object> params){
		return ResponseEntity.ok(handsetService.doSearch(params));
	}
	
	@PostMapping
	public ResponseEntity<Handset> add(@RequestBody Handset handset){
		return ResponseEntity.ok(repo.save(handset));
	}
}
