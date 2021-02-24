package com.db.interview.trade.processor.service.helper;

import com.db.interview.trade.processor.exception.InvalidVersionException;
import com.db.interview.trade.processor.exception.MaturityDateException;
import com.db.interview.trade.processor.model.EnrichmentResponse;
import com.db.interview.trade.processor.model.EnrichmentResponseStatus;
import com.db.interview.trade.processor.model.Trade;
import com.db.interview.trade.processor.service.validator.DefaultValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@Log4j2
public class TradeEnrichmentHelper implements EnrichmentHelper{

    private DefaultValidator validator;
    private PersistanceHelper persistanceHelper;

    public TradeEnrichmentHelper(DefaultValidator validator,PersistanceHelper persistanceHelper){
        this.validator=validator;
        this.persistanceHelper=persistanceHelper;
    }

    @Override
    public EnrichmentResponse enrich(Trade trade) {

        EnrichmentResponse enrichmentResponse = new EnrichmentResponse(trade.getTradeId());
        try{
            log.info("Trade Processing Started...");
            Stream.of(trade).peek(entry -> validator.checkVersion(entry))
                    .peek(entry -> validator.validateMaturityDate(entry))
                    .forEach(entry -> persistanceHelper.persist(entry));

            enrichmentResponse.setStatus(EnrichmentResponseStatus.SUCCESS);
            return enrichmentResponse;
        } catch(InvalidVersionException e){
            log.warn(e.getErrorMessage());
            enrichmentResponse.setStatus(EnrichmentResponseStatus.INVALID_VERSION);
            enrichmentResponse.setErrorDescription(e.getErrorMessage());
            enrichmentResponse.setTradeId(trade.getTradeId());
            return enrichmentResponse;
        } catch(MaturityDateException e){
            log.warn(e.getErrorMessage());
            enrichmentResponse.setStatus(EnrichmentResponseStatus.INVALID_MATURITY_DATE);
            enrichmentResponse.setErrorDescription(e.getErrorMessage());
            enrichmentResponse.setTradeId(trade.getTradeId());
            return enrichmentResponse;
        } catch(Exception e){
            log.warn(e.getMessage());
            enrichmentResponse.setStatus(EnrichmentResponseStatus.FAILURE);
            enrichmentResponse.setErrorDescription(e.getLocalizedMessage());
            enrichmentResponse.setTradeId(trade.getTradeId());
            return enrichmentResponse;
        }
    }
}
