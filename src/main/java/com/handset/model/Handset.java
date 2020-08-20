package com.handset.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Document(indexName = "handset", shards = 1, replicas = 1 )
@Data
public class Handset {

	@Id
	private String id;

	@Field(type = FieldType.Text, fielddata = true)
	private String brand;

	@Field(type = FieldType.Text, fielddata = true)
	private String phone;

	@Field(type = FieldType.Text, fielddata = true)
	private String picture;

	@Field(type = FieldType.Text, fielddata = true)
	private String sim;

	@Field(type = FieldType.Text, fielddata = true)
	private String resolution;

	// The following fields are of type nested to be used in nestedQuery search
	@Field(type = FieldType.Nested, includeInParent = true)
	private HandsetRelease release;

	@Field(type = FieldType.Nested, includeInParent = true)
	private HandsetHardware hardware;

}
