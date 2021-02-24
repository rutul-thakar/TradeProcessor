package com.db.interview.trade.processor.repository;

import com.db.interview.trade.processor.model.Trade;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends CrudRepository<Trade,String> {

    Trade findByTradeId(String tradeId);

    @Modifying
    @Query(value="UPDATE TRADES SET EXPIRED='Y' WHERE MATURITY_DATE<CURRENT_DATE()",nativeQuery = true)
    void updateTrade();
}
