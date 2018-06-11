package com.nas.app.dipjyot.controller;

import java.util.List;

import com.nas.app.dipjyot.model.SearchCriteria;
import com.nas.app.dipjyot.model.Subscription;

public interface DipJyotActionController {
	List<Subscription> getList(SearchCriteria searchCriteria);
	Subscription getDetails(int subscriptionId);
	Subscription updateDetails(Subscription sub);
	Subscription createSubscription(Subscription subscription);
	Subscription extendSubscription(Subscription subscription);
	String printSticker(SearchCriteria searchCriteria);
}
