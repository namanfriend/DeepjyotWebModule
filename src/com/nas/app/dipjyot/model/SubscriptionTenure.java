package com.nas.app.dipjyot.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SubscriptionTenure {
	YR_10("10_YR", "10 Years", 10),
	YR_5("5_YR", "5 Years", 5),
	YR_1("1_YR", "1 Year", 1);

	private String code;
	private String desc;
	private int years;
	
	private SubscriptionTenure(String code, String desc, int years) {
		this.code = code;
		this.desc = desc;
		this.years = years;
	}

	@JsonCreator
	public static SubscriptionTenure forValue(String value) { 
		for(SubscriptionTenure tenure: SubscriptionTenure.values()){
			if(tenure.getCode().equalsIgnoreCase(value)){
				return tenure;
			}
		}
		return null;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getYears() {
		return years;
	}

	public void setYears(int years) {
		this.years = years;
	}
	
}
