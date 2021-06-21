package com.citi.trade.controller;

import com.citi.trade.model.StockDetails;
import com.citi.trade.service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yahoofinance.histquotes.HistoricalQuote;

import java.util.List;



@RestController
@RequestMapping("/dashboard")
@Component
public class DashBoardController {
	
	
	@Autowired
	public DashBoardService dashboardService;
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/getPriceShokers")
	public List<StockDetails> getPriceShokers(){
		
		return  dashboardService.getPriceShockers();
	}
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/getVolumeShokers")
	public List<StockDetails> getVolumeShokers() {
		return  dashboardService.getVolumeShokers();
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/getTopGainers")
	public List<HistoricalQuote> getTopGainer(){
		return  dashboardService.getTopGainer();
	}
	
	
}
