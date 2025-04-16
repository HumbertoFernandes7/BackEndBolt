package com.bolt.desafio.desafioBolt.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolt.desafio.desafioBolt.components.DownloadCsvComponent;
import com.bolt.desafio.desafioBolt.converts.RalieUsinaConvert;
import com.bolt.desafio.desafioBolt.dtos.outputs.RalieUsinaOutput;
import com.bolt.desafio.desafioBolt.entities.RalieUsinaEntity;
import com.bolt.desafio.desafioBolt.services.CsvService;

@RestController
@RequestMapping("/usina")
@CrossOrigin("*")
public class UsinaController {

	@Autowired
	private CsvService csvService;

	@Autowired
	private DownloadCsvComponent downloadCsv;
	
	@Autowired
	private RalieUsinaConvert ralieUsinaConvert;

	@GetMapping("/importaManualmente")
	public void importarCsv() {
		downloadCsv.downloadCsv();
	}

	@GetMapping("/lista/top5")
	public List<RalieUsinaOutput> listaTop5Empresas() {
		List<RalieUsinaEntity> entidades = csvService.buscaTop5Empresas();
		return ralieUsinaConvert.ListEntityToListOutput(entidades);
		
	}
}
