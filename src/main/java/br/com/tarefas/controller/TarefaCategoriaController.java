package br.com.tarefas.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.tarefas.assembler.TarefaCategoriaModelAssembler;
import br.com.tarefas.controller.request.TarefaCategoriaRequest;
import br.com.tarefas.controller.response.TarefaCategoriaResponse;
import br.com.tarefas.model.TarefaCategoria;
import br.com.tarefas.repository.TarefaCategoriaRepository;

@RestController
public class TarefaCategoriaController {

	@Autowired
	private TarefaCategoriaRepository tarefaCategoriaRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private TarefaCategoriaModelAssembler tarefaCategoriaModelAssembler;
	
	@GetMapping("/categoria")
	public CollectionModel<EntityModel<TarefaCategoriaResponse>> todasCategorias(@RequestParam Map<String, String> parametro) {
		List<TarefaCategoria> tarefaCategorias = new ArrayList<>();
		
		if(parametro.isEmpty()) {
			tarefaCategorias = tarefaCategoriaRepository.findAll();
		} else {
			String descricao = parametro.get("nome");
			tarefaCategorias = tarefaCategoriaRepository.findByNomeIgnoreCaseLike("%" + descricao + "%");
		}
		
		List<EntityModel<TarefaCategoriaResponse>> tarefaModelCategoria = tarefaCategorias
				.stream()
				.map(tarefaCategoriaModelAssembler::toModel)
				.collect(Collectors.toList());
		
		return CollectionModel.of(tarefaModelCategoria, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TarefaCategoriaController.class).todasCategorias(parametro))
				.withSelfRel()
				);
	}
	
	@GetMapping("/categoria/{id}")
	public EntityModel<TarefaCategoriaResponse> listarPorId(@PathVariable Integer id) {
		Optional<TarefaCategoria> tarefaCategoria = tarefaCategoriaRepository.findById(id);
		
		return tarefaCategoriaModelAssembler.toModel(tarefaCategoria.get());
	}
	
	@PostMapping("/categoria")
	public TarefaCategoriaResponse salvarCategoria(@RequestBody TarefaCategoriaRequest tarefaCategoriaRequest) {
		TarefaCategoria tarefaCategoria = mapper.map(tarefaCategoriaRequest, TarefaCategoria.class);
		
		return mapper.map(tarefaCategoriaRepository.save(tarefaCategoria), TarefaCategoriaResponse.class);
	}
	
	@DeleteMapping("/categoria/{id}")
	public void deletarCategoria(@PathVariable Integer id) {
		tarefaCategoriaRepository.deleteById(id);
	}
	
}
