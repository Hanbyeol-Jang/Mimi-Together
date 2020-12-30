package com.mimi.Dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "party")
public class PartyRequest {

	@Id
	private String userID;
	private String ptName;
	private String promiseLocation;

}
