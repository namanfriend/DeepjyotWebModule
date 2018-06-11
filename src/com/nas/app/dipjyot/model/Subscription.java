package com.nas.app.dipjyot.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nas.app.common.util.DateUtils;

public class Subscription {
	
	private int subscriptionId;
	private String fullName;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String city;
	private String state;
	private String country;
	private String pincode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtils.YYYY_DD_MM)
	private Date subStartDate = new Date();
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtils.YYYY_DD_MM)
	private Date subEndDate;
	private int subscriptionEndNumber;
	private SubscriptionTenure tenure = SubscriptionTenure.YR_10;
	private String specialSub;
	
	public int getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(int subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getAddressLine3() {
		return addressLine3;
	}
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Date getSubStartDate() {
		return subStartDate;
	}
	public void setSubStartDate(Date subStartDate) {
		this.subStartDate = subStartDate;
	}
	public Date getSubEndDate() {
		return subEndDate;
	}
	public void setSubEndDate(Date subEndDate) {
		this.subEndDate = subEndDate;
	}
	public int getSubscriptionEndNumber() {
		return subscriptionEndNumber;
	}
	public void setSubscriptionEndNumber(int subscriptionEndNumber) {
		this.subscriptionEndNumber = subscriptionEndNumber;
	}

	@Override
	public String toString() {
		return "FullName: " + this.fullName 
				+ "   Subscription Id: " + this.subscriptionId
				+ "   AddressLine1: " + this.addressLine1
				+ "   AddressLine2: " + this.addressLine2
				+ "   AddressLine3: " + this.addressLine3
				+ "   City: " + this.city
				+ "   State: " + this.state
				+ "   pincode: " + this.getPincode()
				+ "   Sub Start Date: " + this.subStartDate
				+ "   Sub End Date: " + this.subEndDate
				+ "   Subscripiton End Number: " + this.subscriptionEndNumber;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public SubscriptionTenure getTenure() {
		return tenure;
	}
	public void setTenure(SubscriptionTenure tenure) {
		this.tenure = tenure;
	}
	public String getSpecialSub() {
		return specialSub;
	}
	public void setSpecialSub(String specialSub) {
		this.specialSub = specialSub;
	}
}
