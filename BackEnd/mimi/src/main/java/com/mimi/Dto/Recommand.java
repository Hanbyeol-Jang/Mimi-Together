package com.mimi.Dto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Document(collection="recommand")
public class Recommand {
	@Id
	private String id;
	private String uid;
	private int rid;
	private Double rating;
	private String name;
	private String address;
	private String tel;
	private String category;
	private String mainMn;
	private String price;
	private String menu;
	private String img;
	private String tags;

	
}
