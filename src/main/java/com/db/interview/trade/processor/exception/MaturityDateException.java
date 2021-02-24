package com.db.interview.trade.processor.exception;

import com.db.interview.trade.processor.model.Trade;
import lombok.Getter;

@Getter
public class MaturityDateException extends IllegalStateException{
    private Trade requestedTrade;
    private String errorMessage;

    public MaturityDateException(Trade requested, String errorMessage){
        this.requestedTrade=requested;
        this.errorMessage=errorMessage;
    }
}
