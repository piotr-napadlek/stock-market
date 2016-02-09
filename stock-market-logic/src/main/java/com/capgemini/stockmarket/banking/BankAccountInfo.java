package com.capgemini.stockmarket.banking;


import java.io.*;
import java.util.*;

public interface BankAccountInfo {

	double getBalanceFor(Currency currency);

	List<Currency> getAvailableCurrencies();

	List<CompanyTo> getAvailableSharesCompanies();

	Set<ShareInfo> getShareInfos(CompanyTo company);

}
