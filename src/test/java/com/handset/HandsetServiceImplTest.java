package com.handset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.handset.model.Handset;
import com.handset.service.HandsetService;

@SpringBootTest
public class HandsetServiceImplTest {

	@Autowired
	private HandsetService service;
	
	Supplier<Map<String, Object>> mapSupplier = () -> new HashMap<String, Object>(1);

	@Test
	public void testFindByPrice() {
		Map<String, Object> params = mapSupplier.get();
		params.put("priceEur", "200");
		List<Handset> handsets = service.doSearch(params);
		Assert.assertTrue(handsets.size() == 10);
	}
	
	@Test
	public void testFindBySim() {
		Map<String, Object> params = mapSupplier.get();
		params.put("sim", "eSim");
		List<Handset> handsets = service.doSearch(params);
		Assert.assertTrue(handsets.size() == 18);
	}
	
	@Test
	public void testFindByDateAndPrice() {
		Map<String, Object> params = mapSupplier.get();
		params.put("priceEur", "200");
		params.put("announceDate", "1999");
		List<Handset> handsets = service.doSearch(params);
		Assert.assertTrue(handsets.size() == 2);
	}
}
