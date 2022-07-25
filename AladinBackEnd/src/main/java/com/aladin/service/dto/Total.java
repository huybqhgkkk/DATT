package com.aladin.service.dto;

public class Total {
	private Integer value;
	private String relation;

	public Total() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Total(Integer value, String relation) {
		super();
		this.value = value;
		this.relation = relation;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}


}
