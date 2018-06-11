package com.nas.app.dipjyot.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SubscriptionView {
	ALL("ALL", "All", "allSubscriptions"),
	DEEPJYOT_SUB("DEEPJYOT", "Deepjyot Subscriptions", "deepjyotSubscriptions"),
	SPECIAL_SUB("SPECIAL", "Special Subscriptions", "specialSubscriptions");

	private String code;
	private String desc;
	private String sqlKey;
	
	private SubscriptionView(String code, String desc,  String sqlKey) {
		this.setCode(code);
		this.setDesc(desc);
		this.setSqlKey(sqlKey);
	}

	@JsonCreator
	public static SubscriptionView forValue(String value) { 
		for(SubscriptionView tenure: SubscriptionView.values()){
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

	public String getSqlKey() {
		return sqlKey;
	}

	public void setSqlKey(String sqlKey) {
		this.sqlKey = sqlKey;
	}


}
