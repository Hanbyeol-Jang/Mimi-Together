package com.mimi.Dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "party")
public class Party {

	@Id
	private String id;

	private int[] userList;
	private String pName;
	private int[] recoList;

}
