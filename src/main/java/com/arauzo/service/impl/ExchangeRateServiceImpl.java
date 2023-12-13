package com.arauzo.service.impl;

import com.arauzo.controller.ExchangeRateController;
import com.arauzo.dto.RequestMontoDTO;
import com.arauzo.dto.ResponseMontoDTO;
import com.arauzo.model.ExchangeRate;
import com.arauzo.repo.IExchangeRateRepo;
import com.arauzo.service.IExchangeRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ExchangeRateServiceImpl implements IExchangeRateService {

	@Autowired
	private IExchangeRateRepo repo;

	private static final Logger log = LoggerFactory.getLogger(ExchangeRateController.class);


	@Override
	public Mono<ExchangeRate> save(ExchangeRate exchangeRate) {
		return this.repo.save(exchangeRate);
	}

	@Override
	public Mono<ResponseMontoDTO> changeCurrency(RequestMontoDTO requestMontoDTO) {
		return this.repo.findBySourceCurrencyAndTargetCurrency(requestMontoDTO.getMonedadeOrigen(), requestMontoDTO.getMonedadeDestino())
				.map(exchangeRate -> {
					log.info("exchangeRate: " + exchangeRate);
					ResponseMontoDTO responseMontoDTO = new ResponseMontoDTO();
					responseMontoDTO.setMonto(requestMontoDTO.getMonto());
					responseMontoDTO.setMontototaltipoCambio(requestMontoDTO.getMonto() * exchangeRate.getRate());
					responseMontoDTO.setMonedadeOrigen(requestMontoDTO.getMonedadeOrigen());
					responseMontoDTO.setMonedadeDestino(requestMontoDTO.getMonedadeDestino());
					responseMontoDTO.setTipodecambio(exchangeRate.getRate());
					return responseMontoDTO;
				});
	}

	@Override
	public Flux<ExchangeRate> findAll() {
		return repo.findAll();
	}

	@Override
	public Mono<ExchangeRate> update(ExchangeRate exchangeRate) {
		return repo.save(exchangeRate);
	}

	@Override
	public Mono<ExchangeRate> findById(String id) {
		return this.repo.findById(id);
	}
}
