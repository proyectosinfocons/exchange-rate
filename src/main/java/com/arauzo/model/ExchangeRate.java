package com.arauzo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "exchange_rates")
public class ExchangeRate {
    @Id
    private String id;
    @Field(name = "sourceCurrency")
    private String sourceCurrency;
    @Field(name = "targetCurrency")
    private String targetCurrency;
    @Field(name = "rate")
    private Double rate;

    private String createdBy;
    private String createdDate;
    private String LastModifiedBy;
}
