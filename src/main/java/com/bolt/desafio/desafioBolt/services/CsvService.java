package com.bolt.desafio.desafioBolt.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bolt.desafio.desafioBolt.entities.RalieUsinaEntity;
import com.bolt.desafio.desafioBolt.repositories.RalieUsinaRepository;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CsvService {

	@Autowired
	private RalieUsinaRepository ralieUsinaRepository;

	private static final Logger log = LoggerFactory.getLogger(CsvService.class);

	@Transactional
	public void processarCsv(String path) {
		ralieUsinaRepository.deleteAll();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE; // Formato yyyy-MM-dd

		try (CSVReader reader = new CSVReaderBuilder(
				new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))
				.withCSVParser(new CSVParserBuilder().withSeparator(';').build()).build()) {

			reader.skip(1); // Pular cabeçalho
			String[] linha;

			while ((linha = reader.readNext()) != null) {
				try {
					if (isLinhaInvalida(linha)) {
						log.warn("Linha ignorada: {}", Arrays.toString(linha));
						continue;
					}

					RalieUsinaEntity entidade = construirEntidade(linha, dateFormatter);
					ralieUsinaRepository.save(entidade);
					log.debug("Entidade salva: {}", entidade.getNomeEmpreendimento());

				} catch (Exception e) {
					log.error("Erro na linha {}: {}", reader.getLinesRead(), e.getMessage(), e);
				}
			}

			log.info("CSV processado com sucesso. Total de registros: {}", ralieUsinaRepository.count());

		} catch (IOException | CsvValidationException e) {
			log.error("Erro ao processar o CSV: {}", e.getMessage(), e);
			throw new RuntimeException("Falha no processamento do CSV", e);
		}
	}

	private boolean isLinhaInvalida(String[] linha) {
		// Verifica se tem pelo menos 9 colunas e se não está totalmente em branco
		return linha.length < 9 || Arrays.stream(linha).allMatch(String::isBlank);
	}

	private RalieUsinaEntity construirEntidade(String[] linha, DateTimeFormatter formatter) {
		return RalieUsinaEntity.builder().nomeEmpreendimento(linha[7].trim()) // Coluna 8: NomEmpreendimento
				.uf(linha[4].trim()) // Coluna 5: SigUFPrincipal
				.fonte(linha[5].trim()) // Coluna 6: DscOrigemCombustivel
				.potenciaMw(parseBigDecimal(linha[8])) // Coluna 9: MdaPotenciaOutorgadaKw
				.dataOperacao(parseLocalDate(linha[1], formatter)) // Coluna 2: DatRalie
				.classeConsumo(linha.length > 10 ? linha[10].trim() : "") // Coluna 11: DscPropriRegimePariticipacao
				.modalidadeGeracao(linha[6].trim()) // Coluna 7: SigTipoGeracao
				.submercado(linha.length > 20 ? linha[20].trim() : "") // Coluna 21: DscSistema
				.build();
	}

	private BigDecimal parseBigDecimal(String valor) {
		if (valor == null || valor.isBlank()) {
			return BigDecimal.ZERO;
		}
		try {
			return new BigDecimal(valor.replace(",", ".").trim());
		} catch (NumberFormatException e) {
			log.warn("Valor numérico inválido: '{}'", valor);
			return BigDecimal.ZERO;
		}
	}

	private LocalDate parseLocalDate(String data, DateTimeFormatter formatter) {
		if (data == null || data.isBlank()) {
			return null;
		}
		try {
			return LocalDate.parse(data.trim(), formatter);
		} catch (DateTimeParseException e) {
			log.warn("Data inválida: '{}'", data);
			return null;
		}
	}

	public List<RalieUsinaEntity> buscaTop5Empresas() {
		Page<RalieUsinaEntity> page = ralieUsinaRepository
				.findAll(PageRequest.of(0, 5, Sort.by("potenciaMw").descending()));
		List<RalieUsinaEntity> top5 = page.getContent();
		return top5;
	}
}
