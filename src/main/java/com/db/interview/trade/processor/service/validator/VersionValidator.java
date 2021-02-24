package com.db.interview.trade.processor.service.validator;

import com.db.interview.trade.processor.model.Trade;

public interface VersionValidator {
    void checkVersion(Trade trade);
}
