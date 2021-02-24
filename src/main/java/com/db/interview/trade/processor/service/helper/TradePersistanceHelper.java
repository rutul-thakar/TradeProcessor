package com.db.interview.trade.processor.service.helper;

import com.db.interview.trade.processor.model.Trade;


public interface TradePersistanceHelper {

    void persist(Trade trade);

    void update();
}
