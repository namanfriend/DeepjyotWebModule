package com.nas.app.dipjyot.model;

import java.util.Date;

public class SearchCriteria {
	private SubscriptionView view;
	private Integer subscriptionId;
	private String fullName;
	private String city;
	private String state;
	private String pincode;
	private Date fromDate;
	private Date toDate;
	
	public Integer getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(Integer subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public SubscriptionView getView() {
		return view;
	}
	public void setView(SubscriptionView view) {
		this.view = view;
	}
}
