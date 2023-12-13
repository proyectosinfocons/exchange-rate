package com.arauzo.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestMontoDTO {

    private Double monto;

    private String monedadeOrigen;

    private String monedadeDestino;

}
