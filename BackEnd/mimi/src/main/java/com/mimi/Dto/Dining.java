package com.mimi.Dto;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "dining")
public class Dining {

	@Id
	private int id;

	private int dStatus;
	private String dName;
	private String dLocation;
	private Date dDate;
	private int dPeople;
	private int dPrice;
	private String[] dMenu;
	private int uID;
	private String uName;
	private String uEmail;
	private String uProfile;
	private String uThumb;
	private String uToken;
	private int[] storeList;

}
