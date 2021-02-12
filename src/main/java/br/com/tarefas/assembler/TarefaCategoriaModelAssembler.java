package br.com.tarefas.assembler;

import java.util.HashMap;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import br.com.tarefas.controller.TarefaCategoriaController;
import br.com.tarefas.controller.response.TarefaCategoriaResponse;
import br.com.tarefas.model.TarefaCategoria;

@Component
public class TarefaCategoriaModelAssembler implements RepresentationModelAssembler<TarefaCategoria, EntityModel<TarefaCategoriaResponse>> {

	@Autowired
	private ModelMapper mapper;
	
	@Override
	public EntityModel<TarefaCategoriaResponse> toModel(TarefaCategoria tarefaCategoria) {
		TarefaCategoriaResponse tarefaCategoriaResponse = mapper.map(tarefaCategoria, TarefaCategoriaResponse.class);
		
		EntityModel<TarefaCategoriaResponse> tarefaCategoriaModel = EntityModel.of(tarefaCategoriaResponse,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TarefaCategoriaController.class).listarPorId(tarefaCategoriaResponse.getId())).withRel("categoria"),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TarefaCategoriaController.class).todasCategorias(new HashMap<>())).withRel("categoriaGeral")
				);
		
		return tarefaCategoriaModel;
	}

}
