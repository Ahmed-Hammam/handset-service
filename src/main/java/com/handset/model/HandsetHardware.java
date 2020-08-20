package com.handset.model;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Data
public class HandsetHardware {

	@Field(type = FieldType.Text,fielddata = true)
	private String audioJack;
	
	@Field(type = FieldType.Text,fielddata = true)
	private String gps;
	
	@Field(type = FieldType.Text,fielddata = true)
	private String battery;
}
