package com.db.interview.trade.processor.scheduler;

import com.db.interview.trade.processor.service.helper.PersistanceHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class TradeScheduler {

    private PersistanceHelper persistanceHelper;

    @Autowired
    public TradeScheduler(PersistanceHelper persistanceHelper){
        this.persistanceHelper=persistanceHelper;
    }

    @Scheduled(fixedDelay = 3600000)
    public void maturityDateScheduler(){
        persistanceHelper.update();
    }
}
