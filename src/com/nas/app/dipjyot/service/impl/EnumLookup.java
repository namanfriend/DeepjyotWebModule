package com.nas.app.dipjyot.service.impl;

public class EnumLookup {
	private String code;
	private String desc;
	
	public EnumLookup(String code, String desc){
		this.code = code;
		this.desc = desc;
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
	
}
