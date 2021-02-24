package com.db.interview.trade.processor.service.helper;

import com.db.interview.trade.processor.model.EnrichmentResponse;
import com.db.interview.trade.processor.model.Trade;

public interface EnrichmentHelper {
    EnrichmentResponse enrich(Trade trade);
}
