package com.mimi.mimi.Temp.Dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Document(collection="TempMimi")
public class Temp {
	@Id
	private String id;
	private String temp;
	private String temp2;
	@Override
	public String toString() {
		return "Temp [id=" + id + ", temp=" + temp + ", temp2=" + temp2 + "]";
	}
}
