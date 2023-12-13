package com.arauzo.controller;

import com.arauzo.dto.RequestMontoDTO;
import com.arauzo.dto.ResponseMontoDTO;
import com.arauzo.model.ExchangeRate;
import com.arauzo.service.IExchangeRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/exchange-rate")
public class ExchangeRateController {
	
	private static final Logger log = LoggerFactory.getLogger(ExchangeRateController.class);
	
	@Autowired
	private IExchangeRateService service;

	@PostMapping("/change-currency")
	public Mono<ResponseEntity<ResponseMontoDTO>> cambiar(@Valid @RequestBody RequestMontoDTO requestMontoDTO, final ServerHttpRequest req){
		Mono<ResponseMontoDTO> montoDTOMono = service.changeCurrency(requestMontoDTO);

		return montoDTOMono
				.map(p -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(p));

	}
	
	@GetMapping
	public Mono<ResponseEntity<Flux<ExchangeRate>>> listar(){
		
		Flux<ExchangeRate> rateFlux = service.findAll();
		
		return Mono.just(ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(rateFlux)
				);
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<ExchangeRate>> listarPorId(@PathVariable("id") String id){
		return service.findById(id)
				.map(p -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(p)
						)
				.defaultIfEmpty(ResponseEntity.notFound().build());
		
	}
	
	@PostMapping
	public Mono<ResponseEntity<ExchangeRate>> registrar(@Valid @RequestBody ExchangeRate exchangeRate, final ServerHttpRequest req){

		return ReactiveSecurityContextHolder.getContext()
				.map(SecurityContext::getAuthentication)
				.flatMap(authentication -> {
					String username = authentication.getName();
					exchangeRate.setCreatedBy(username);
					exchangeRate.setCreatedDate(LocalDateTime.now().toString());
					return service.save(exchangeRate)
							.map(p -> ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(p.getId())))
									.contentType(MediaType.APPLICATION_JSON)
									.body(p)
							);
				});

	}
	
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<ExchangeRate>> modificar(@PathVariable("id") String id, @Valid @RequestBody ExchangeRate exchangeRate){
		
		Mono<ExchangeRate> monoBody = Mono.just(exchangeRate);
		Mono<ExchangeRate> monoBD = service.findById(id);

		return ReactiveSecurityContextHolder.getContext()
				.map(SecurityContext::getAuthentication)
				.flatMap(authentication -> {
					String username = authentication.getName();
					return monoBD
							.zipWith(monoBody, (bd, er) -> {
								bd.setId(id);
								bd.setSourceCurrency(er.getSourceCurrency());
								bd.setTargetCurrency(er.getTargetCurrency());
								bd.setRate(er.getRate());
								bd.setLastModifiedBy(username);
								return bd;
							})
							.flatMap(service::update)
							.map(pl -> ResponseEntity.ok()
									.contentType(MediaType.APPLICATION_JSON)
									.body(pl))
							.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
				});
	}
	


}
