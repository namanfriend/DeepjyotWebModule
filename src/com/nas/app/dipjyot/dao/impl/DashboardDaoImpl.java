package com.nas.app.dipjyot.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.nas.app.common.db.DBExecuter;
import com.nas.app.common.exception.NASApplicationException;
import com.nas.app.common.util.DateUtils;
import com.nas.app.dipjyot.model.SearchCriteria;
import com.nas.app.dipjyot.model.Subscription;

@Component
public class DashboardDaoImpl {
	@Autowired
	private DBExecuter executer;
	
	public List<Subscription> getSubscriptions(SearchCriteria searchCriteria){
		RowMapper<Subscription> rowMapper = new SubscriptionRowMapper();
		Map<String, Object> inputParameter = prepareSearchCriteria(searchCriteria);
		@SuppressWarnings("unchecked")
		List<Subscription> subscriptions = (List<Subscription>) this.executer.executeQuery(searchCriteria.getView().getSqlKey(), inputParameter, rowMapper);
		return subscriptions;
	}
	
	private Map<String, Object> prepareSearchCriteria(SearchCriteria searchCriteria) {
		Map<String, Object> inputParameters = new HashMap<>();
		inputParameters.put("subscriptionId", searchCriteria.getSubscriptionId());
		inputParameters.put("city", "%" + (StringUtils.isEmpty(searchCriteria.getCity()) ? "" : searchCriteria.getCity() + "%"));
		inputParameters.put("state", "%" + (StringUtils.isEmpty(searchCriteria.getState()) ? "" : searchCriteria.getState() + "%"));
		inputParameters.put("fromDate", searchCriteria.getFromDate());
		inputParameters.put("toDate", searchCriteria.getToDate());
		inputParameters.put("pincode", searchCriteria.getPincode());		
		return inputParameters;
	}

	public Subscription getSubscription(int subscriptionId){
		Map<String, Object> inputParameters = new HashMap<String, Object>();
		inputParameters.put("subscriptionId", subscriptionId);
		RowMapper<Subscription> rowMapper = new SubscriptionRowMapper();
		@SuppressWarnings("unchecked")
		List<Subscription> subscriptions = (List<Subscription>) this.executer.executeQuery("subscriptionDetails", inputParameters, rowMapper);
		return subscriptions.get(0);
	}
	
	public void updateSubscription(Subscription sub){
		Map<String, Object> inputParameters = new HashMap<String, Object>();
//		updateSubscription=update subscription set NAME = :fullName, ADDRESS_LINE1 = :addressLine1, ADDRESS_LINE2 = :addressLine2, 
//		ADDRESS_LINE3 = :addressLine3, CITY = :city, STATE = :state, PINCODE = :pincode, SUB_DATE = :subDate, SUB_END_DATE = :subEndDate where ID = :id

		inputParameters.put("subscriptionId", sub.getSubscriptionId());
		inputParameters.put("fullName", sub.getFullName());
		inputParameters.put("addressLine1", sub.getAddressLine1());
		inputParameters.put("addressLine2", sub.getAddressLine2());
		inputParameters.put("addressLine3", sub.getAddressLine3());
		inputParameters.put("city", sub.getCity());
		inputParameters.put("state", sub.getState());
		inputParameters.put("pincode", sub.getPincode());
		inputParameters.put("country", sub.getCountry());
		inputParameters.put("subDate", DateUtils.getDateString(sub.getSubStartDate(), DateUtils.YYYY_DD_MM));
		inputParameters.put("subEndDate", DateUtils.getDateString(sub.getSubEndDate(), DateUtils.YYYY_DD_MM));
		
		
		this.executer.executeUpdate("updateSubscription", inputParameters);
		this.executer.executeUpdate("insertAuditSubscription", inputParameters);
	}
	

	public void createSubscription(Subscription sub) {		
		@SuppressWarnings("unchecked")
		List<Integer> subscriptionIds = this.executer.executeQuery("generateSubscriptionNumber", new SingleColumnRowMapper<Integer>(Integer.class));
		sub.setSubscriptionId(subscriptionIds.get(0));
		Map<String, Object> inputParameters = new HashMap<String, Object>();
		inputParameters.put("subscriptionId", sub.getSubscriptionId());
		inputParameters.put("fullName", sub.getFullName());
		inputParameters.put("addressLine1", sub.getAddressLine1());
		inputParameters.put("addressLine2", sub.getAddressLine2());
		inputParameters.put("addressLine3", sub.getAddressLine3());
		inputParameters.put("city", sub.getCity());
		inputParameters.put("state", sub.getState());
		inputParameters.put("pincode", sub.getPincode());
		inputParameters.put("country", sub.getCountry());
		inputParameters.put("subDate", DateUtils.getDateString(sub.getSubStartDate(), DateUtils.YYYY_DD_MM));
		inputParameters.put("tenure", sub.getTenure().getCode());
		inputParameters.put("subEndDate", DateUtils.getDateString(sub.getSubEndDate(), DateUtils.YYYY_DD_MM));
		this.executer.executeUpdate("createSubscription", inputParameters);		
		this.executer.executeUpdate("insertAuditSubscription", inputParameters);
		
	}

	private class SubscriptionRowMapper implements RowMapper<Subscription>{

		@Override
		public Subscription mapRow(ResultSet rs, int arg1) throws SQLException {
        // [ID, SUB_ID, NAME, ADDRESS_LINE1, ADDRESS_LINE2, ADDRESS_LINE3, CITY, STATE, PINCODE, SUB_DATE, TENURE, SUB_END_DATE]
			Subscription subscription = new Subscription();
			subscription.setSubscriptionId(rs.getInt("SUB_ID"));
			subscription.setFullName(rs.getString("NAME"));
			subscription.setAddressLine1(rs.getString("ADDRESS_LINE1"));
			subscription.setAddressLine2(rs.getString("ADDRESS_LINE2"));
			subscription.setAddressLine3(rs.getString("ADDRESS_LINE3"));
			subscription.setCity(rs.getString("CITY"));
			subscription.setState(rs.getString("STATE"));
			subscription.setPincode(rs.getString("PINCODE"));
			subscription.setSpecialSub(rs.getString("IS_SPECIAL"));
			try {
				subscription.setSubStartDate(DateUtils.getDate(rs.getString("SUB_DATE"), DateUtils.YYYY_DD_MM));
			} catch (NASApplicationException e) {
				throw new SQLException(e.getMessage());
			}
			try {
				subscription.setSubEndDate(DateUtils.getDate(rs.getString("SUB_END_DATE"), DateUtils.YYYY_DD_MM));
			} catch (NASApplicationException e) {
				throw new SQLException(e.getMessage());
			}			
			return subscription;
		}
		
	}

	public void extendSubscription(Subscription sub) {
		Map<String, Object> inputParameters = new HashMap<String, Object>();

		inputParameters.put("subscriptionId", sub.getSubscriptionId());
		inputParameters.put("subEndDate", DateUtils.getDateString(sub.getSubEndDate(), DateUtils.YYYY_DD_MM));
		inputParameters.put("tenure", sub.getTenure().getCode());
		
		this.executer.executeUpdate("extendSubscription", inputParameters);
		this.executer.executeUpdate("insertAuditSubscription", inputParameters);
	}

	public List<Subscription> printStickers(SearchCriteria searchCriteria){
		RowMapper<Subscription> rowMapper = new SubscriptionRowMapper();
		Map<String, Object> inputParameter = prepareSearchCriteria(searchCriteria);
		@SuppressWarnings("unchecked")
		List<Subscription> subscriptions = (List<Subscription>) this.executer.executeQuery(searchCriteria.getView().getSqlKey(), inputParameter, rowMapper);
		return subscriptions;
	}
	
}
