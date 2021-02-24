package com.db.interview.trade.processor.exception;

import com.db.interview.trade.processor.model.Trade;
import lombok.Getter;

@Getter
public class InvalidVersionException extends IllegalStateException{
    private Trade requestedTrade;
    private Trade matchedTrade;
    private String errorMessage;

    public InvalidVersionException(Trade requested, Trade duplicate, String errorMessage){
        this.requestedTrade=requested;
        this.matchedTrade=duplicate;
        this.errorMessage=errorMessage;
    }
}
