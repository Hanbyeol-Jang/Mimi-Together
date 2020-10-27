package com.mimi.Dto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
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
	@Override
	public String toString() {
		return "Recommand [id=" + id + ", uid=" + uid + ", rid=" + rid + ", rating=" + rating + ", name=" + name
				+ ", address=" + address + ", tel=" + tel + ", category=" + category + ", mainMn=" + mainMn + ", price="
				+ price + ", menu=" + menu + ", img=" + img + ", tags=" + tags + "]";
	}
	
	
}
