package com.citi.trade.service;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.citi.trade.model.StockDetails;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

@Service
public class DashBoardService {
	

	Map<String, List<String>> sectorWiseCompanies = Map.ofEntries(
			new AbstractMap.SimpleEntry<String, List<String>>("Automobile",
					new ArrayList<>(Arrays.asList("BAJAJ-AUTO.NS", "EICHERMOT.NS", "HEROMOTOCO.NS", "M&M.NS",
							"MARUTI.NS", "TATAMOTORS.NS"))),
			new AbstractMap.SimpleEntry<String, List<String>>("Banking",
					new ArrayList<>(Arrays.asList("AXISBANK.NS", "HDFCBANK.NS", "ICICIBANK.NS ", "INDUSINDBK.NS ",
							"KOTAKBANK.NS ", "SBIN.NS"))),
			new AbstractMap.SimpleEntry<String, List<String>>("Cement",
					new ArrayList<>(Arrays.asList("ASIANPAINT.NS", "BRITANNIA.NS", "HINDUNILVR.NS", "ITC.NS",
							"NESTLEIND.NS", "TITAN.NS"))),
			new AbstractMap.SimpleEntry<String, List<String>>("Energy",
					new ArrayList<>(Arrays.asList("BPCL.NS", "GAIL.NS", "IOC.NS", "ONGC.NS", "RELIANCE.NS", "NTPC.NS",
							"POWERGRID.NS"))),
			new AbstractMap.SimpleEntry<String, List<String>>("Information Technology",
					new ArrayList<>(Arrays.asList("HCLTECH.NS", "INFY.NS", "TCS.NS", "TECHM.NS", "WIPRO.NS"))));




	public List<StockDetails> getPriceShockers() throws IOException
	{

		List<StockDetails> priceShockers = new ArrayList<StockDetails>();
		Set<String> sectorList = sectorWiseCompanies.keySet();
		try {
		sectorList.forEach(sector -> {
			List<String> companyList = new ArrayList<>();
			companyList.addAll(sectorWiseCompanies.get(sector));
			String[] tmpStockList = new String[companyList.size()];
			for (int i = 0; i < companyList.size(); i++)
			{
				tmpStockList[i] = companyList.get(i) ;

			}
			Map<String, Stock> stocks;
			
				try {
					stocks = YahooFinance.get(tmpStockList);
					List<Stock> apiStockData = new ArrayList<>(stocks.values());
					if(!ObjectUtils.isEmpty(apiStockData)) {
						apiStockData.forEach(stock -> {
							StockDetails stockDetails = new StockDetails();
							stockDetails.setCompanyName(stock.getName());
							stockDetails.setCurrentPrice(stock.getQuote().getPrice());
							stockDetails.setPercentageChange(stock.getQuote().getChangeInPercent());
							stockDetails.setSector(sector);
							priceShockers.add(stockDetails);
							// priceShokers.add((StockDetails) sectorList);

						});
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		

		});


		if(priceShockers!= null && priceShockers.size() >0 ) {
			Collections.sort(priceShockers, new Comparator<StockDetails>() {
				public int compare(StockDetails s1, StockDetails s2) {
					if(s1!=null && s2!= null && s1.getPercentageChange() != null && s2.getPercentageChange()!=null)
						return s2.getPercentageChange().compareTo(s1.getPercentageChange());
					else
						return 0;
				}
			});}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return priceShockers;
	}


	public List<StockDetails> getVolumeShokers() throws IOException
	{

		List<StockDetails> volumeShockers = new ArrayList<StockDetails>();
		Set<String> sectorList = sectorWiseCompanies.keySet();
		try {
		sectorList.forEach(sector -> {
			List<String> companyList = new ArrayList<>();
			companyList.addAll(sectorWiseCompanies.get(sector));
			String[] tmpStockList = new String[companyList.size()];
			for (int i = 0; i < companyList.size(); i++)
			{
				tmpStockList[i] = companyList.get(i) ;

			}
			Map<String, Stock> stocks;
			
				try {
					stocks = YahooFinance.get(tmpStockList);
					List<Stock> apiStockData = new ArrayList<>(stocks.values());
					if(!ObjectUtils.isEmpty(apiStockData)) {
						apiStockData.forEach(stock -> {
							StockDetails stockDetails = new StockDetails();
							stockDetails.setCompanyName(stock.getName());
							stockDetails.setVolume(stock.getQuote().getVolume());
							stockDetails.setSector(sector);
							volumeShockers.add(stockDetails);
						});
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		

		});


		if(volumeShockers!= null && volumeShockers.size() >0 ) {
			Collections.sort(volumeShockers, new Comparator<StockDetails>() {
				public int compare(StockDetails s1, StockDetails s2) {
					if(s1!=null && s2!= null && s1.getVolume() != null && s2.getVolume()!=null)
						return s2.getVolume().compareTo(s1.getVolume());
					else
						return 0;
				}
			});}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return volumeShockers;

	}	
	public List<HistoricalQuote> getTopGainer() throws IOException
	{
		List<StockDetails> topGainer = new ArrayList<StockDetails>();
		Set<String> sectorList = sectorWiseCompanies.keySet();
		int noofWeeks = -2; // two weeks
		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		from.add(Calendar.WEEK_OF_MONTH,noofWeeks);
		sectorList.forEach(sector -> {
			List<String> companyList = new ArrayList<>();
			companyList.addAll(sectorWiseCompanies.get(sector));
			String[] tmp = new String[companyList.size()];
			for (int i = 0; i < companyList.size(); i++)
			{
				tmp[i] = companyList.get(i) ;

			}
			Map<String, Stock> stocks;
			try {
				stocks = YahooFinance.get(tmp);
				List<Stock> apiStockData = new ArrayList<>(stocks.values());
				if(!ObjectUtils.isEmpty(apiStockData)) {
					apiStockData.forEach(stock -> {
						StockDetails stockDetails = new StockDetails();
						stockDetails.setCompanyName(stock.getName());
						stockDetails.setCurrentPrice(stock.getQuote().getPrice());
						stockDetails.setSector(sector);
						stockDetails.setSymbol(stock.getSymbol());
						topGainer.add(stockDetails);
						//System.out.println(graph.get(0));
					});
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		if(topGainer!= null && topGainer.size() >0 ) {
			Collections.sort(topGainer, new Comparator<StockDetails>() {
				public int compare(StockDetails s1, StockDetails s2) {
					if(s1!=null && s2!= null && s1.getCurrentPrice() != null && s2.getCurrentPrice()!=null)
						return s2.getCurrentPrice().compareTo(s1.getCurrentPrice());
					else
						return 0;
				}
			});}
		String topGainerCompanySymbol = topGainer.get(0).getSymbol();
		Stock topGainerCompanyName = YahooFinance.get(topGainerCompanySymbol);
		List<HistoricalQuote> companyHistoricalData = topGainerCompanyName.getHistory(from,to, Interval.DAILY);	
		return companyHistoricalData;
    }
	
}

