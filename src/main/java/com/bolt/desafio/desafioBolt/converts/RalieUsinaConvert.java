package com.bolt.desafio.desafioBolt.converts;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bolt.desafio.desafioBolt.dtos.outputs.RalieUsinaOutput;
import com.bolt.desafio.desafioBolt.entities.RalieUsinaEntity;

@Component
public class RalieUsinaConvert {

	@Autowired
	private ModelMapper model;
	
	public RalieUsinaOutput EntityToOutput(RalieUsinaEntity ralieUsinaEntity) {
		return model.map(ralieUsinaEntity, RalieUsinaOutput.class);
	}

	public List<RalieUsinaOutput> ListEntityToListOutput(List<RalieUsinaEntity> entidades) {
		return entidades.stream().map(a -> this.EntityToOutput(a)).collect(Collectors.toList());
	}
	
}
