package com.db.interview.trade.processor;

import com.db.interview.trade.processor.model.EnrichmentResponse;
import com.db.interview.trade.processor.model.EnrichmentResponseStatus;
import com.db.interview.trade.processor.model.Trade;
import com.db.interview.trade.processor.repository.TradeRepository;
import com.db.interview.trade.processor.service.helper.TradeEnrichmentHelper;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Log4j2
class TradeProcessorApplicationTests {

	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Resource
	private TradeRepository tradeRepository;

	@Resource
	private TradeEnrichmentHelper tradeEnrichmentHelper;

	@Test
	@Order(1)
	void sendTrade1ToProcess() throws ParseException{
		Trade trade = createTrade1();
		EnrichmentResponse response = tradeEnrichmentHelper.enrich(trade);
		assertEquals(EnrichmentResponseStatus.INVALID_MATURITY_DATE,response.getStatus());
	}

	@Test
	@Order(2)
	void sendTrade2ToProcess() throws ParseException{
		Trade trade = createTrade2();
		EnrichmentResponse response = tradeEnrichmentHelper.enrich(trade);
		assertEquals(EnrichmentResponseStatus.SUCCESS,response.getStatus());
	}

	@Test
	@Order(3)
	void sendTrade3ToProcess() throws ParseException{
		Trade trade = createTrade3();
		EnrichmentResponse response = tradeEnrichmentHelper.enrich(trade);
		assertEquals(EnrichmentResponseStatus.INVALID_VERSION,response.getStatus());
	}

	@Test
	@Order(4)
	void sendTrade4ToProcess() throws ParseException{
		Trade trade = createTrade4();
		EnrichmentResponse response = tradeEnrichmentHelper.enrich(trade);
		assertEquals(EnrichmentResponseStatus.INVALID_MATURITY_DATE,response.getStatus());
		checkCurrentTradesAtEnd();
	}

	private void checkCurrentTradesAtEnd() {
		log.info("Destroying Tests...");
		Iterable<Trade> fetchedTrade = tradeRepository.findAll();
		for(Trade trade:fetchedTrade){
			log.info(trade);
		}
	}

	private Trade createTrade1() throws ParseException {
		Date today = new Date();

		Trade trade = new Trade();
		trade.setTradeId("T1");
		trade.setVersion(1);
		trade.setCptyId("CP-1");
		trade.setBookId("B1");
		trade.setMaturityDate(sdf.parse("20/05/2020"));
		trade.setCreatedDate(sdf.parse(sdf.format(today)));
		trade.setExpired("N");

		return trade;
	}

	private Trade createTrade2() throws ParseException {
		Date today = new Date();

		Trade trade = new Trade();
		trade.setTradeId("T2");
		trade.setVersion(2);
		trade.setCptyId("CP-2");
		trade.setBookId("B1");
		trade.setMaturityDate(sdf.parse("20/05/2021"));
		trade.setCreatedDate(sdf.parse(sdf.format(today)));
		trade.setExpired("N");

		return trade;
	}

	private Trade createTrade3() throws ParseException {
		Date today = new Date();

		Trade trade = new Trade();
		trade.setTradeId("T2");
		trade.setVersion(1);
		trade.setCptyId("CP-1");
		trade.setBookId("B1");
		trade.setMaturityDate(sdf.parse("20/05/2021"));
		trade.setCreatedDate(sdf.parse("14/02/2015"));
		trade.setExpired("N");

		return trade;
	}

	private Trade createTrade4() throws ParseException {
		Date today = new Date();

		Trade trade = new Trade();
		trade.setTradeId("T3");
		trade.setVersion(3);
		trade.setCptyId("CP-3");
		trade.setBookId("B2");
		trade.setMaturityDate(sdf.parse("20/05/2014"));
		trade.setCreatedDate(sdf.parse(sdf.format(today)));
		trade.setExpired("Y");

		return trade;
	}

}
