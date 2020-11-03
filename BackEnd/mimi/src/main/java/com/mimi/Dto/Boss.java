package com.mimi.Dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "boss")
public class Boss {

	@Id
	private String id;

	private String boPW;
	private int[] diningList;
	private String boAddress;
	private int[] storeList;

}
