package com.arauzo.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseMontoDTO {

    private Double monto;

    private Double montototaltipoCambio;

    private String monedadeOrigen;

    private String monedadeDestino;

    private Double tipodecambio;
}
