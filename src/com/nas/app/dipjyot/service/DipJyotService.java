package com.nas.app.dipjyot.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.nas.app.common.exception.NASApplicationException;
import com.nas.app.dipjyot.model.SearchCriteria;
import com.nas.app.dipjyot.model.Subscription;

public interface DipJyotService {
	String testGet();
	ResponseEntity<List<Subscription>> getList(SearchCriteria searchCriteria);
	ResponseEntity<Subscription> getSubscriptionDetails(Integer subscriptionId);
	ResponseEntity<Subscription> updateSubscription(Subscription subscription);
	ResponseEntity<Subscription> createSubscription(Subscription subscription) throws NASApplicationException;
	ResponseEntity<Subscription> extendSubscription(Subscription subscription) throws NASApplicationException;
	ResponseEntity<String> printSticker(SearchCriteria searchCriteria) throws NASApplicationException;
	ResponseEntity<Map<String, Object>> init(String str);
}
