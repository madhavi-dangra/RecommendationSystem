package com.citi.trade.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.citi.trade.model.StockDetails;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

import com.citi.trade.util.SectorCompanyList;
@Service
public class RecommendationService {


	int noofDays=-3;
	Calendar from = Calendar.getInstance();
	Calendar to = Calendar.getInstance();
	
	SectorCompanyList sectorCompanyList;
	//Map<String, List<String>> sector_wise_companie = sectorCompanyList.sectorWiseCompany();
	Map<String, List<String>> sector_wise_companies = Map.ofEntries(
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
	public List<StockDetails> getRecommendation(String sector, String parameter) { 

		from.add(Calendar.WEEK_OF_MONTH,noofDays);
		List<StockDetails> recommendedStocks = new ArrayList<StockDetails>();
		try {
			List<String> companies = sector_wise_companies.get(sector);

			String[] tmp = new String[companies.size()];
			for (int i = 0; i < tmp.length; i++)
				tmp[i] = companies.get(i); //get stock symbols

			Map<String, Stock> stocks = YahooFinance.get(tmp);
			List<Stock> sorted_stocks = new ArrayList<>(stocks.values());

			if(!ObjectUtils.isEmpty(sorted_stocks)) {
				sorted_stocks.forEach(stock -> {

					StockDetails stockDetail = new StockDetails();
					stockDetail.setCompanyName(stock.getName());
					stockDetail.setCurrentPrice(stock.getQuote().getPrice());
					stockDetail.setMarketCap(stock.getStats().getMarketCap());
					stockDetail.setPercentageChange(stock.getQuote().getChangeInPercent());
					stockDetail.setBidPrice(stock.getQuote().getBid());
					stockDetail.setClose(stock.getQuote().getPreviousClose());
					stockDetail.setOpen(stock.getQuote().getOpen());
					stockDetail.setHigh(stock.getQuote().getDayHigh());
					stockDetail.setLow(stock.getQuote().getDayLow());
					BigDecimal ma=new BigDecimal(0);
					for(int i=0;i<14;++i)
					{
						try {

							ma=ma.add(stock.getHistory(from, to, Interval.DAILY).get(i).getClose());

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
					}
					stockDetail.setMovingAverage(ma);

					try {
						stockDetail.setVolume(stock.getHistory().get(0).getVolume());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					stockDetail.setGrowth(stock.getQuote().getChangeFromAvg50InPercent());

					recommendedStocks.add(stockDetail);

				});
				if("Growth".equalsIgnoreCase(parameter)) { // when user selects growth
					Collections.sort(recommendedStocks, new Comparator<StockDetails>() {
						public int compare(StockDetails s1, StockDetails s2) {
							return s2.getGrowth().compareTo(s1.getGrowth());
						}
					});}
				if("Low".equalsIgnoreCase(parameter)) { // when user selects low
					Collections.sort(recommendedStocks, new Comparator<StockDetails>() {
						public int compare(StockDetails s1, StockDetails s2) {
							return s2.getLow().compareTo(s1.getLow());
						}
					});}
				if("Volume".equalsIgnoreCase(parameter)) { // when user selects volume
					Collections.sort(recommendedStocks, new Comparator<StockDetails>() {
						public int compare(StockDetails s1, StockDetails s2) {
							return s2.getVolume().compareTo(s1.getVolume());
						}
					});}
				if("High".equalsIgnoreCase(parameter)) { // when user selects high
					Collections.sort(recommendedStocks, new Comparator<StockDetails>() {
						public int compare(StockDetails s1, StockDetails s2) {
							return s2.getHigh().compareTo(s1.getHigh());
						}
					});}

				if("MarketCap".equalsIgnoreCase(parameter)) { // when user selects high
					Collections.sort(recommendedStocks, new Comparator<StockDetails>() {
						public int compare(StockDetails s1, StockDetails s2) {
							return s2.getMarketCap().compareTo(s1.getMarketCap());
						}
					});}

				if("SimpleMovingAverage".equalsIgnoreCase(parameter)) { // when user selects high
					Collections.sort(recommendedStocks, new Comparator<StockDetails>() {
						public int compare(StockDetails s1, StockDetails s2) {
							return s2.getMovingAverage().compareTo(s1.getMovingAverage());
						}
					});}

				if(parameter.equalsIgnoreCase(null)) { // default case
					Collections.sort(recommendedStocks, new Comparator<StockDetails>() {
						public int compare(StockDetails s1, StockDetails s2) {
							return s2.getGrowth().compareTo(s1.getGrowth());
						}
					});}
			} else {
				System.out.print("Unable to fetch data from yahoo api.");
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return recommendedStocks;
	}
}


