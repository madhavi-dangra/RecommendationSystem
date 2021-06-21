package com.citi.trade.service;

import com.citi.trade.model.UserHistory;
import com.citi.trade.repository.UserHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserHistoryService {

    @Autowired
    private UserHistoryRepository repository;

    public boolean saveUserHistory(UserHistory s) {
        UserHistory history = new UserHistory();
        if (s != null)
            history = repository.save(s);
        return history != null;
    }

    public List<UserHistory> getSavedStocks(String userName) {
        List<UserHistory> historyList = new ArrayList<>();
        if (!userName.isEmpty())
            historyList = repository.findByUserName(userName);
        return historyList;
    }


}
