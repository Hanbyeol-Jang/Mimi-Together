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

	private String uiName;
	private String uiProfile;
	private String uiThumb;
	private String uiToken;
	private List<String> diningList;
	private List<String> partyList;
	private String device;
	private String isSurvey;

}
