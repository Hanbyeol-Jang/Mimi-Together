package com.mimi.Dto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.annotations.ThreadSafe;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Data
@Document(collection="recommand")
public class Recommand implements Comparable<Recommand> {
	
	
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
	
	@Override
	public int compareTo(Recommand o) {
		if(this.rating-o.rating>0)
			return -1;
		return 1;
	}
	
}
