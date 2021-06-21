package com.citi.trade.controller;

import com.citi.trade.model.StockDetails;
import com.citi.trade.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendation")
@Component
public class RecommendationController {

    @Autowired
    public RecommendationService recommendationService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/getRecommendation")
    public List<StockDetails> getRecommendation(@RequestParam("sector") String sector, @RequestParam("parameter") String parameter) {
        return recommendationService.getRecommendation(sector, parameter);
    }


}
