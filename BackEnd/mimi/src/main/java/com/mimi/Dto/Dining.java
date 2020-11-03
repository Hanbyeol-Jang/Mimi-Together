package com.mimi.Dto;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "dining")
public class Dining {

	@Id
	private String id;

	private int dnStatus;
	private String dnName;
	private String dnLocation;
	private Date dnDate;
	private int dnPeople;
	private int dnPrice;
	private String[] dnMenu;
	private int uiID;
	private String uiName;
	private String uiEmail;
	private String uiProfile;
	private String uiThumb;
	private String uiToken;
	private int[] storeList;
	private String soldID;

	@Data
	private static class TenderInfo {
		private String boID;
		private int tiPrice;
		private String tiInfo;
	}

}
