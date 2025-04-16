package com.bolt.desafio.desafioBolt.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_RalieUsina")
public class RalieUsinaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nomeEmpreendimento")
	private String nomeEmpreendimento;

	@Column(name = "uf")
	private String uf;

	@Column(name = "fonte")
	private String fonte;

	@Column(name = "potenciaMw", precision = 15, scale = 2)
	private BigDecimal potenciaMw;

	@Column(name = "dataOperacao")
	private LocalDate dataOperacao;

	@Column(name = "classeConsumo")
	private String classeConsumo;

	@Column(name = "modalidadeGeracao")
	private String modalidadeGeracao;

	@Column(name = "submercado")
	private String submercado;
}
