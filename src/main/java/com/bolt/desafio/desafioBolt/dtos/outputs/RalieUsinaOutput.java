package com.bolt.desafio.desafioBolt.dtos.outputs;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RalieUsinaOutput {
	
	private Long id;

	private String nomeEmpreendimento;

	private String uf;

	private String fonte;

	private BigDecimal potenciaMw;

	private LocalDate dataOperacao;

	private String classeConsumo;

	private String modalidadeGeracao;

	private String submercado;

}
