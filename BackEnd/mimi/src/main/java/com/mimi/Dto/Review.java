package com.mimi.Dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="review")
public class Review {
	@Id
	private String id;
	private int resId;
	private String resName;
	private String userId;
	private String userName;
	private double rating;
	private String review;
}
