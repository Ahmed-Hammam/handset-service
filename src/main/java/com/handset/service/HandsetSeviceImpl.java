package com.handset.service;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.http.client.ClientProtocolException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.handset.model.Handset;
import com.handset.repository.HandsetRepository;

@Service
public class HandsetSeviceImpl implements HandsetService {

	@Autowired
	private ElasticsearchOperations esOperations;
	
	@Autowired
	private HandsetRepository repo;

	@Override
	public List<Handset> doSearch(Map<String, Object> params) {
		if (Objects.isNull(params) || params.isEmpty())
			return new ArrayList<>();

		if (notZeroPredicate.test(repo.count()))
			dataProvider.run();			
		
		return queryProviderFunction.andThen(searchFunction).andThen(resultExtractorFunction).apply(params);

	}

	private Function<NativeSearchQuery, SearchHits<Handset>> searchFunction = (query) -> {
		return esOperations.search(query, Handset.class);
	};

	private Function<SearchHits<Handset>, List<Handset>> resultExtractorFunction = (hits) -> {
		List<Handset> handsets = new ArrayList<>();
		if (Objects.nonNull(hits) && hits.hasSearchHits()) {
			hits.forEach(e -> handsets.add(e.getContent()));
		}
		return handsets;
	};

	private Predicate<String> isReleaseNestedType = x -> x.equals(SearchQueryFields.RELEASE_PRICE_EUR)
			|| x.equals(SearchQueryFields.RELEASE_ANNOUNCE_DATE);

	private Predicate<String> isHardwareNestedType = x -> x.equals(SearchQueryFields.HARDWARE_AUDIO_JACK)
			|| x.equals(SearchQueryFields.HARDWARE_BATTERY) || x.equals(SearchQueryFields.HARDWARE_GPS);

	private Predicate<Long> notZeroPredicate = x -> x == 0;

	private Function<Map<String, Object>, NativeSearchQuery> queryProviderFunction = (map) -> {

		BoolQueryBuilder builder = boolQuery();

		map.forEach((k, v) -> {
			if (isReleaseNestedType.test(k)) {
				builder.must(matchQuery(SearchQueryFields.RELEASE_ATTRIBUTE + k, v));
			}
			if (isHardwareNestedType.test(k)) {
				builder.must(matchQuery(SearchQueryFields.HARDWARE_ATTRIBUTE + k, v));
			}
			if (isReleaseNestedType.or(isHardwareNestedType).negate().test(k)) {
				builder.must(matchQuery(k, v));
			}

		});

		NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(builder).build();
		return searchQuery;
	};

	private Runnable dataProvider = () -> {
		try {
			loadData();
		} catch (IOException e) {
			e.printStackTrace();
		}
	};

	public void loadData() throws ClientProtocolException, IOException {
		Gson gson = new Gson();
		Handset[] data;
		try {
			data = gson.fromJson(
					new FileReader(new File(getClass().getClassLoader().getResource(SearchQueryFields.DATA_FILE_NAME).getFile())),
					Handset[].class);
			Arrays.stream(data).forEach(e -> repo.save(e));
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e1) {
			e1.printStackTrace();
		}

	}

}
