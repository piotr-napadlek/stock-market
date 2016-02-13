package com.capgemini.stockmarket.broker;


import com.capgemini.stockmarket.settings.BrokersOfficeSettings;

public interface BrokersOffice extends BrokersOfficeDesk {

	void applySettings(BrokersOfficeSettings settings);

}
