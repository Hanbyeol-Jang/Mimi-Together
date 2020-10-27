package com.mimi.mimi.store.Dto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Document(collection="store")
public class Store {
	@Id
	private int id;
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
	public Store(int id, String name, String address, String tel, String category, String mainMn, String price,
			String menu, Double rating, int rvwCnt, String img, String tags) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.tel = tel;
		this.category = category;
		this.mainMn = mainMn;
		this.price = price;
		this.menu = menu;
		this.rating = rating;
		this.rvwCnt = rvwCnt;
		this.img = img;
		this.tags = tags;
	}
	@Override
	public String toString() {
		return "Store [id=" + id + ", name=" + name + ", address=" + address + ", tel=" + tel + ", category=" + category
				+ ", mainMn=" + mainMn + ", price=" + price + ", menu=" + menu + ", rating=" + rating + ", rvwCnt="
				+ rvwCnt + ", img=" + img + ", tags=" + tags + "]";
	}
	
}
