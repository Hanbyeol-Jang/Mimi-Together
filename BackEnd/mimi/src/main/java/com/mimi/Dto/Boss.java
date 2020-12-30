package com.mimi.Dto;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "boss")
public class Boss {

	@Id
	private String id;

	private String boPW;
	private List<TenderInfo> diningList;
	private String boAddress;
	private List<String> storeList;

}
