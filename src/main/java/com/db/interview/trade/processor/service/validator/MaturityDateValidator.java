package com.db.interview.trade.processor.service.validator;

import com.db.interview.trade.processor.model.Trade;

public interface MaturityDateValidator {
    void validateMaturityDate(Trade trade);
}
