package com.nas.app.dipjyot.controller.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.nas.app.common.util.DateUtils;
import com.nas.app.dipjyot.controller.DipJyotActionController;
import com.nas.app.dipjyot.dao.impl.DashboardDaoImpl;
import com.nas.app.dipjyot.model.SearchCriteria;
import com.nas.app.dipjyot.model.Subscription;

@Component
public class DipJyotActionControllerImpl implements DipJyotActionController{

	@Autowired
	private DashboardDaoImpl dashboardDaoImpl;
	@Resource(name="stickerHTMLProperties")
    private Properties stickerHTMLProperties;
	
	@Override
	public List<Subscription> getList(SearchCriteria searchCriteria) {
		List<Subscription> subscriptions = dashboardDaoImpl.getSubscriptions(searchCriteria);
		List<Subscription> finalResult = filterResultsWithNameCriteria(searchCriteria, subscriptions);
		Collections.sort(finalResult, new SubscriptionComparator());
		return finalResult;
	}
	
	private class SubscriptionComparator implements Comparator<Subscription>{
		@Override
		public int compare(Subscription sub1, Subscription sub2) {
			return ((Integer) sub2.getSubscriptionId()).compareTo(sub1.getSubscriptionId());
		}
		
	}
	
	private List<Subscription> filterResultsWithNameCriteria(SearchCriteria searchCriteria, List<Subscription> subscriptions) {
		List<Subscription> finalResults = new ArrayList<>();
		String[] fullNameSplit = {};
		if(searchCriteria.getFullName() != null && !StringUtils.isEmpty(searchCriteria.getFullName())){
			fullNameSplit = searchCriteria.getFullName().split(" ");
		}
		if(fullNameSplit.length < 1){
			return subscriptions;
		}
		for(Subscription sub: subscriptions){
			boolean isAcceptedResult = true;
			for(String name : fullNameSplit){
				if(!sub.getFullName().toLowerCase().contains(name.toLowerCase())){
					isAcceptedResult = false;
				}
			}
			if(isAcceptedResult){
				finalResults.add(sub);
			}
		}
		return finalResults;
	}

	@Override
	public Subscription getDetails(int subscriptionId) {
		return dashboardDaoImpl.getSubscription(subscriptionId);
	}

	@Override
	public Subscription updateDetails(Subscription sub) {
		dashboardDaoImpl.updateSubscription(sub);
		return dashboardDaoImpl.getSubscription(sub.getSubscriptionId());
	}

	@Override
	public Subscription createSubscription(Subscription sub) {
		dashboardDaoImpl.createSubscription(sub);
		return dashboardDaoImpl.getSubscription(sub.getSubscriptionId());
	}

	@Override
	public Subscription extendSubscription(Subscription subscription) {
		Subscription sub = dashboardDaoImpl.getSubscription(subscription.getSubscriptionId());
		sub.setTenure(subscription.getTenure());
		sub.setSubEndDate(calculateNewSubscriptionEndDate(sub));
		dashboardDaoImpl.extendSubscription(sub);
		return dashboardDaoImpl.getSubscription(sub.getSubscriptionId());
	}

	private Date calculateNewSubscriptionEndDate(Subscription sub) {
		Date subEndDate = sub.getSubEndDate();
		if(subEndDate == null || new Date().after(subEndDate)){
			subEndDate = new Date();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(subEndDate);
		cal.add(Calendar.MONTH, sub.getTenure().getYears() * 12);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	@Override
	public String printSticker(SearchCriteria searchCriteria) {
		List<Subscription> subscriptions = dashboardDaoImpl.printStickers(searchCriteria);
		return generatePDF(subscriptions);
	}

	private String generatePDF(List<Subscription> subscriptions) {
		try {
		    String k = generateHTMLText(subscriptions);
		    System.out.println(k);
		    String temDirLocation = System.getProperty("java.io.tmpdir");
		    String fileName = "\\stickerDownload_" + DateUtils.getDateString(new Date(), DateUtils.FILE_DATE_EXT);
		    File file2 = File.createTempFile(fileName, ".pdf");
		    OutputStream file = new FileOutputStream(file2);
		    Document document = new Document();
		    document.setMargins(19f, 19f, 19f, 19f);
		    PdfWriter writer = PdfWriter.getInstance(document, file);
		    document.open();
		    InputStream is = new ByteArrayInputStream(k.getBytes());
		    
		    InputStream cis = this.getClass().getResourceAsStream("/config/pdf/sticker.css");
		    XMLWorkerHelper.getInstance().parseXHtml(writer, document, is, cis);
		    document.close();
		    file.close();
		    cis.close();
		    return file2.getName();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return null;
	}

	private String generateHTMLText(List<Subscription> subscriptions) {
		int rows = Integer.parseInt(stickerHTMLProperties.getProperty("rows"));
		int columns = Integer.parseInt(stickerHTMLProperties.getProperty("columns"));
		String stickerDynamicHTML = stickerHTMLProperties.getProperty("stickerDynamicHTML");
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(stickerHTMLProperties.getProperty("stickerPreHTML"));
		int counter = 0;
		for(Subscription subscription: subscriptions){
			counter++;
			String stickerHTML = generateSticker(subscription, stickerDynamicHTML);
			if(counter % columns ==  0){
				strBuilder.append(stickerHTML).append("</tr>");
			}else if(counter % columns ==  1){
				strBuilder.append("<tr>").append(stickerHTML);
			}else {
				strBuilder.append(stickerHTML);
			}
		}
		if(counter % columns !=  0){
			strBuilder.append("</tr>");
		}
		strBuilder.append(stickerHTMLProperties.getProperty("stickerPostHTML"));
		return strBuilder.toString();
	}

	private String generateSticker(Subscription subscription, String stickerDynamicHTML) {
		stickerDynamicHTML = stickerDynamicHTML.replaceAll("\\{\\{fullName\\}\\}", subscription.getFullName());
		stickerDynamicHTML = stickerDynamicHTML.replaceAll("\\{\\{addressLine1\\}\\}", subscription.getAddressLine1());
		stickerDynamicHTML = stickerDynamicHTML.replaceAll("\\{\\{addressLine2\\}\\}", subscription.getAddressLine2());
		stickerDynamicHTML = stickerDynamicHTML.replaceAll("\\{\\{addressLine3\\}\\}", subscription.getAddressLine3());
		stickerDynamicHTML = stickerDynamicHTML.replaceAll("\\{\\{city\\}\\}", subscription.getCity());
		stickerDynamicHTML = stickerDynamicHTML.replaceAll("\\{\\{state\\}\\}", subscription.getState());
		stickerDynamicHTML = stickerDynamicHTML.replaceAll("\\{\\{pincode\\}\\}", subscription.getPincode());
		stickerDynamicHTML = stickerDynamicHTML.replaceAll("\\{\\{subscriptionId\\}\\}", String.valueOf(subscription.getSubscriptionId()));
		stickerDynamicHTML = stickerDynamicHTML.replaceAll("\\{\\{subEndDate\\}\\}", DateUtils.getDateString(subscription.getSubEndDate(), DateUtils.MMM_YYYY));
		return stickerDynamicHTML;
	}

}
