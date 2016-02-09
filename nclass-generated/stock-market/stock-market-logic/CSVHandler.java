package stock-market.stock-market-logic;

import java.io.*;
import java.util.*;

public interface CSVHandler {

	List<StockPriceRecordTo> parseCSV(String csv);

}
