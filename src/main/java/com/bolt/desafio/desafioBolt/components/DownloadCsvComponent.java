package com.bolt.desafio.desafioBolt.components;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bolt.desafio.desafioBolt.services.CsvService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class DownloadCsvComponent {

	@Autowired
	private CsvService csvService;

	private static final Logger log = LoggerFactory.getLogger(DownloadCsvComponent.class);
	private static final String LINK_ARQUIVO = "https://dadosabertos.aneel.gov.br/dataset/57e4b8b5-a5db-40e6-9901-27ca629d0477/resource/4a615df8-4c25-48fa-bbea-873a36a79518/download/ralie-usina.csv";
	private static final String NOME_ARQUIVO = "ralie-usina.csv";

	@Scheduled(cron = "0 */10 * * * *", zone = "America/Sao_Paulo")
	public void downloadCsv() {
		Path path = Paths.get(NOME_ARQUIVO).toAbsolutePath();

		try (BufferedInputStream in = new BufferedInputStream(new URL(LINK_ARQUIVO).openStream());

				FileOutputStream fileOutputStream = new FileOutputStream(path.toFile())) {

			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) != -1) {
				fileOutputStream.write(buffer, 0, bytesRead);
			}

			log.info("Arquivo salvo em: {}", path);
			csvService.processarCsv(path.toString());

		} catch (IOException e) {
			log.error("Erro ao baixar o CSV: {}", e.getMessage(), e);
			throw new RuntimeException("Falha no download do CSV", e);
		}
	}
}
