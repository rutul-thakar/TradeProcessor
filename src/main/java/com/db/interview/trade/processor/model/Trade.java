package com.db.interview.trade.processor.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="trades")
@Data
public class Trade implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID",strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="id",updatable = false,nullable = false)
    private String id;

    @Column(name="TRADE_ID")
    private String tradeId;

    @Column(name="VERSION")
    private Integer version;

    @Column(name="CPTY_ID")
    private String cptyId;

    @Column(name="BOOK_ID")
    private String bookId;

    @Column(name="MATURITY_DATE")
    private Date maturityDate;

    @Column(name="CREATED_DATE")
    private Date createdDate;

    @Column(name="EXPIRED")
    private String expired;

}
