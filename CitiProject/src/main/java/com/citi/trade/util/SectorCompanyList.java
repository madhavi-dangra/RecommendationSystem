package com.citi.trade.util;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SectorCompanyList {
	
	public static Map<String,List<String>> sectorWiseCompany()
	{
	return Map.ofEntries(
			new AbstractMap.SimpleEntry<String, List<String>>("Automobile",
					new ArrayList<>(Arrays.asList("BAJAJ-AUTO.NS", "EICHERMOT.NS", "HEROMOTOCO.NS", "M&M.NS",
							"MARUTI.NS", "TATAMOTORS.NS"))),
			new AbstractMap.SimpleEntry<String, List<String>>("Banking",
					new ArrayList<>(Arrays.asList("AXISBANK.NS", "HDFCBANK.NS", "ICICIBANK.NS", " INDUSINDBK.NS",
							"KOTAKBANK.NS", "SBIN.NS"))),
			new AbstractMap.SimpleEntry<String, List<String>>("Cement",
					new ArrayList<>(Arrays.asList("ASIANPAINT.NS", "BRITANNIA.NS", "HINDUNILVR.NS", "ITC.NS",
							"NESTLEIND.NS", "TITAN.NS"))),
			new AbstractMap.SimpleEntry<String, List<String>>("Energy",
					new ArrayList<>(Arrays.asList("BPCL.NS", "GAIL.NS", "IOC.NS", "ONGC.NS", "RELIANCE.NS", "NTPC.NS",
							"POWERGRID.NS"))),
			new AbstractMap.SimpleEntry<String, List<String>>("Information Technology",
					new ArrayList<>(Arrays.asList("HCLTECH.NS", "INFY.NS", "TCS.NS", "TECHM.NS", "WIPRO.NS","TATAELXSI.NS"))));

	}


}
