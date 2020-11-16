package com.mimi.Dto;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "user")
public class User {

	@Id
	private String id;

	private String uName;
	private String uEmail;
	private String uProfile;
	private String uThumb;
	private String uToken;
	private List<Integer> diningList;
	private List<Integer> partyList;
}
