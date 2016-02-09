package com.capgemini.stockmarket.initializer;


import java.io.*;
import java.util.*;

public interface CSVHandler {

	List<StockPriceRecordTo> parseCSV(String csv);

}
