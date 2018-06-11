package com.nas.app.dipjyot.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nas.app.common.exception.NASApplicationException;
import com.nas.app.common.util.DateUtils;
import com.nas.app.dipjyot.controller.DipJyotActionController;
import com.nas.app.dipjyot.model.SearchCriteria;
import com.nas.app.dipjyot.model.Subscription;
import com.nas.app.dipjyot.model.SubscriptionTenure;
import com.nas.app.dipjyot.model.SubscriptionView;
import com.nas.app.dipjyot.service.DipJyotService;

@RestController
public class DipJyotServiceImpl implements DipJyotService {

	@Autowired
	private DipJyotActionController dipJyotActionController;
	
	@Override
	@GetMapping("/dipjyot/test")
	public String testGet() {
		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.setView(SubscriptionView.DEEPJYOT_SUB);
		printSticker(searchCriteria);
		return "Get Service Success111!!";
	}
	
	@Override
	@PostMapping("/dipjyot/initService")
	public ResponseEntity<Map<String, Object>> init(@RequestBody String searchCriteria) {
		Map<String, Object> map = new HashMap<>();
		map.put("VIEWS", getLookupList(SubscriptionView.values()));
		map.put("TENURE", getLookupList(SubscriptionTenure.values()));
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	private List<EnumLookup> getLookupList(SubscriptionTenure[] values) {
		List<EnumLookup> list = new ArrayList<>();
		for(SubscriptionTenure value : values){
			list.add(new EnumLookup(value.getCode(), value.getDesc()));
		}
		return list;
	}
	private List<EnumLookup> getLookupList(SubscriptionView[] values) {
		List<EnumLookup> list = new ArrayList<>();
		for(SubscriptionView value : values){
			list.add(new EnumLookup(value.getCode(), value.getDesc()));
		}
		return list;
	}

	@Override
	@PostMapping("/dipjyot/getList")
	public ResponseEntity<List<Subscription>> getList(@RequestBody SearchCriteria searchCriteria) {
		List<Subscription> list = dipJyotActionController.getList(searchCriteria);
		return new ResponseEntity<List<Subscription>>(list, HttpStatus.OK);
	}
	
	@Override
	@PostMapping("/dipjyot/subscriptionDetails")
	public ResponseEntity<Subscription> getSubscriptionDetails(@RequestBody Integer subscriptionId) {
		Subscription sub = dipJyotActionController.getDetails(subscriptionId);
		return new ResponseEntity<Subscription>(sub, HttpStatus.OK);
	}
	
	@Override
	@PostMapping("/dipjyot/updateSubscription")
	public ResponseEntity<Subscription> updateSubscription(@RequestBody Subscription subscription) {
		Subscription sub = dipJyotActionController.updateDetails(subscription);
		return new ResponseEntity<Subscription>(sub, HttpStatus.OK);
	}
	@Override
	@PostMapping("/dipjyot/createSubscription")
	public ResponseEntity<Subscription> createSubscription(@RequestBody Subscription subscription) throws NASApplicationException {
		subscription.setSubEndDate(DateUtils.getDate("2027-31-01", DateUtils.YYYY_DD_MM));
		Subscription sub = dipJyotActionController.createSubscription(subscription);
		return new ResponseEntity<Subscription>(sub, HttpStatus.OK);
	}
	@Override
	@PostMapping("/dipjyot/extendSubscription")
	public ResponseEntity<Subscription> extendSubscription(@RequestBody Subscription subscription) throws NASApplicationException {
//		subscription.setSubEndDate(DateUtils.getDate("2027-31-01", DateUtils.YYYY_DD_MM));
		Subscription sub = dipJyotActionController.extendSubscription(subscription);
		return new ResponseEntity<Subscription>(sub, HttpStatus.OK);
	}
	
	@Override
	@PostMapping("/dipjyot/printSticker")
	public ResponseEntity<String> printSticker(@RequestBody SearchCriteria searchCriteria) {
		String filePath = dipJyotActionController.printSticker(searchCriteria);
		return new ResponseEntity<String>(filePath, HttpStatus.OK);
	}	
}
