package com.mimi.Dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RecommandRequest {

	@ApiModelProperty(required = true)
	private List<String> user_list;
	private String target_location;
	private Survey[] survey_list;

	@Data
	public static class Survey {
		private String uid;
		private int rid;
		private double rating;
	}
}
