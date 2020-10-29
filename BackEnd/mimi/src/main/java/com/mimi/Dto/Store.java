package com.mimi.Dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
@Document(collection="store")
public class Store {
	@Id
	private int id;
	private int bID;
	private String name;
	private String address;
	private String tel;
	private String category;
	private String mainMn;
	private String price;
	private String menu;
	private Double rating;
	private int rvwCnt;
	private String img;
	private String tags;

	
}
