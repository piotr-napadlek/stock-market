package com.capgemini.stockmarket.broker;


import java.io.*;
import java.util.*;

public interface BrokersOffice extends DateAware, BrokersOfficeDesk {

	String getPublicSecuritySignature();

	void applySettings(BrokersOfficeSettings settings);

}
