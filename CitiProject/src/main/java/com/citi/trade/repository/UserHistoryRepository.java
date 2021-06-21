package com.citi.trade.repository;

import com.citi.trade.model.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
    List<UserHistory> findByUserName(String userName);

    @SuppressWarnings("unchecked")
    UserHistory save(UserHistory s);

}
