package stock-market.stock-market-logic;

import java.io.*;
import java.util.*;

public interface BrokersOffice extends DateAware, BrokersOfficeDesk {

	String getPublicSecuritySignature();

	void applySettings(BrokersOfficeSettings settings);

}
