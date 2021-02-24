package com.db.interview.trade.processor.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrichmentResponse {

    private String tradeId;
    private EnrichmentResponseStatus status = EnrichmentResponseStatus.SUCCESS;
    private String errorDescription;

    public EnrichmentResponse(String id){
        this.tradeId = id;
    }

    public EnrichmentResponse(String id, EnrichmentResponseStatus status, String errorDescription){
        this.status=status;
        this.errorDescription=errorDescription;
        this.tradeId=id;
    }
}
