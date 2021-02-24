package com.db.interview.trade.processor.service.validator;

import com.db.interview.trade.processor.exception.InvalidVersionException;
import com.db.interview.trade.processor.exception.MaturityDateException;
import com.db.interview.trade.processor.model.Trade;
import com.db.interview.trade.processor.repository.TradeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import static java.util.Objects.nonNull;

@Component
@Log4j2
public class DefaultValidator implements MaturityDateValidator,VersionValidator{

    private TradeRepository tradeRepository;

    @Autowired
    DefaultValidator(TradeRepository tradeRepository){this.tradeRepository=tradeRepository;}

    @Override
    public void checkVersion(Trade currentTrade) {
        Trade persistedTrade = getTrade(currentTrade);
        if(nonNull(persistedTrade) && currentTrade.getVersion()<persistedTrade.getVersion()){
            log.error("Ignoring Trade with old version: \n Ignored Trade: {}\n Existing Trade: {}",currentTrade,persistedTrade);
            throw new InvalidVersionException(currentTrade, persistedTrade,"Duplicate Trade with TradeId - "+currentTrade.getTradeId()+" and version - "+currentTrade.getVersion());
        }

    }

    private Trade getTrade(Trade trade){
        return tradeRepository.findByTradeId(trade.getTradeId());
    }

    @Override
    public void validateMaturityDate(Trade trade) {
        if(trade.getMaturityDate().before(new Date())){
            log.error("Maturity Date is before current date for {}",trade);
            throw new MaturityDateException(trade, "Trade with TradeId - "+trade.getTradeId()+" and maturity date - "+trade.getMaturityDate());
        }
    }
}
