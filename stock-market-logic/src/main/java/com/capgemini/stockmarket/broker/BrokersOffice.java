package com.capgemini.stockmarket.broker;


import com.capgemini.stockmarket.common.DateAware;
import com.capgemini.stockmarket.settings.BrokersOfficeSettings;

public interface BrokersOffice extends DateAware, BrokersOfficeDesk {

	String getPublicSecuritySignature();

	void applySettings(BrokersOfficeSettings settings);

}
