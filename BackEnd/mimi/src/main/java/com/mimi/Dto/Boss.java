package com.mimi.Dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "boss")
public class Boss {

	@Id
	private String id;

	private String bPW;
	private int[] diningList;
	private int[] storeList;
}
