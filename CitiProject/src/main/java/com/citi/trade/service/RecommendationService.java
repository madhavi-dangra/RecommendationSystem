package com.citi.trade.service;

import com.citi.trade.model.StockDetails;
import com.citi.trade.util.SectorCompanyList;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
@Service
public class RecommendationService {


	int noofDays=-3;
	Calendar from = Calendar.getInstance();
	Calendar to = Calendar.getInstance();

	Map<String, List<String>> sectorWiseCompany = SectorCompanyList.sectorWiseCompany();
	public List<StockDetails> getRecommendation(String sector, String parameter) { 

		from.add(Calendar.WEEK_OF_MONTH,noofDays);
		List<StockDetails> recommendedStocks = new ArrayList<>();
		try {
			List<String> companies = sectorWiseCompany.get(sector);

			String[] tmp = new String[companies.size()];
			for (int i = 0; i < tmp.length; i++)
				tmp[i] = companies.get(i); //get stock symbols

			Map<String, Stock> stocks = YahooFinance.get(tmp);
			List<Stock> sortedStocks = new ArrayList<>(stocks.values());

			if(!ObjectUtils.isEmpty(sortedStocks)) {
				sortedStocks.forEach(stock -> {
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
							e.printStackTrace();
						}  
					}
					stockDetail.setMovingAverage(ma);

					try {
						stockDetail.setVolume(stock.getHistory().get(0).getVolume());
					} catch (IOException e) {
						e.printStackTrace();
					}
					stockDetail.setGrowth(stock.getQuote().getChangeFromAvg50InPercent());

					recommendedStocks.add(stockDetail);

				});
				if("Growth".equalsIgnoreCase(parameter)) { // when user selects growth
					recommendedStocks.sort((s1, s2) -> s2.getGrowth().compareTo(s1.getGrowth()));}
				else if("Low".equalsIgnoreCase(parameter)) { // when user selects low
					recommendedStocks.sort((s1, s2) -> s2.getLow().compareTo(s1.getLow()));}
				else if("Volume".equalsIgnoreCase(parameter)) { // when user selects volume
					recommendedStocks.sort((s1, s2) -> s2.getVolume().compareTo(s1.getVolume()));}
				else if("High".equalsIgnoreCase(parameter)) { // when user selects high
					recommendedStocks.sort((s1, s2) -> s2.getHigh().compareTo(s1.getHigh()));}

				else if("MarketCap".equalsIgnoreCase(parameter)) { // when user selects high
					recommendedStocks.sort((s1, s2) -> s2.getMarketCap().compareTo(s1.getMarketCap()));}

				else if("SimpleMovingAverage".equalsIgnoreCase(parameter)) { // when user selects high
					recommendedStocks.sort((s1, s2) -> s2.getMovingAverage().compareTo(s1.getMovingAverage()));}

				else { // default case
					recommendedStocks.sort((s1, s2) -> s2.getGrowth().compareTo(s1.getGrowth()));}
			} else {
				System.out.print("Unable to fetch data from yahoo api.");
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return recommendedStocks;
	}
}


