package com.db.interview.trade.processor.service.helper;

import com.db.interview.trade.processor.model.Trade;
import com.db.interview.trade.processor.repository.TradeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Component
@Log4j2
public class PersistanceHelper implements TradePersistanceHelper{

    private TradeRepository tradeRepository;

    @Autowired
    public PersistanceHelper(TradeRepository tradeRepository){
        this.tradeRepository=tradeRepository;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void persist(Trade trade) {
        tradeRepository.save(trade);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void update(){
        tradeRepository.updateTrade();
    }
}
