package com.citi.trade.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "UserHistory", uniqueConstraints = @UniqueConstraint(columnNames = "userId"))
@Data
@AllArgsConstructor
public class UserHistory {

    @Id
    @Column(name = "userId")
    long userId;
    @Column(name = "userName")
    String userName;
    @Column(name = "sector")
    String sector;
    @Column(name = "stockName")
    String stockName;
    @Column(name = "currentPrices")
    BigDecimal currentPrices;
    @Column(name = "buyPrice")
    BigDecimal buyPrice;
    @Column(name = "profit_loss")
    BigDecimal profitLoss;
    @Column(name = "quantity")
    int quantity;

    public UserHistory() {
        super();
    }


}
