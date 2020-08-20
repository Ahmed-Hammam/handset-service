package com.handset.model;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Data
public class HandsetRelease {
	
	@Field(type = FieldType.Text,fielddata = true)
	private String announceDate;
	
	@Field(type = FieldType.Text, fielddata = true)
	private String priceEur; 
}
