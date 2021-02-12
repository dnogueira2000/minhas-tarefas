package br.com.tarefas.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.tarefas.assembler.TarefaModelAssembler;
import br.com.tarefas.controller.request.TarefaRequest;
import br.com.tarefas.controller.response.TarefaResponse;
import br.com.tarefas.model.Tarefa;
import br.com.tarefas.services.TarefaService;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {

	@Autowired
	private TarefaService tarefaService;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private TarefaModelAssembler tarefaModelAssembler;
	
	@GetMapping
	public CollectionModel<EntityModel<TarefaResponse>> todasTarefas(@RequestParam Map<String, String> parametros) {
		
		List<Tarefa> tarefas = new ArrayList<>();
		
		if(parametros.isEmpty()) {
			tarefas = tarefaService.getTodasTarefas();
		} else {
			String descricao = parametros.get("descricao");
			tarefas = tarefaService.getTarefasPorDescricao("%" + descricao + "%");
		}
		
		List<EntityModel<TarefaResponse>> tarefasModel = tarefas
				.stream()
				.map(tarefaModelAssembler::toModel)
				.collect(Collectors.toList());
		
		return CollectionModel.of(tarefasModel, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TarefaController.class).todasTarefas(new HashMap<>()))
				.withSelfRel()
				);
	}
	
	@GetMapping("/{id}")
	public EntityModel<TarefaResponse> listarPorId(@PathVariable Integer id) {
		Tarefa tarefa = tarefaService.getTarefaPorId(id);
		
		return tarefaModelAssembler.toModel(tarefa);
	}
	
	@PostMapping
	public ResponseEntity<EntityModel<TarefaResponse>> salvarTarefa(@Valid @RequestBody TarefaRequest tarefaRequest) {
		Tarefa tarefa = mapper.map(tarefaRequest, Tarefa.class);
		
		Tarefa tarefaSalva = tarefaService.salvarTarefa(tarefa);
		
		EntityModel<TarefaResponse> tarefaModel = tarefaModelAssembler.toModel(tarefaSalva);
		
		return ResponseEntity
				.created(tarefaModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(tarefaModel);
				
	}
	
	@DeleteMapping("/{id}")
	public void deletarTarefa(@PathVariable Integer id) {
		tarefaService.deletePorId(id);
	}
	
	@PutMapping("/{id}/iniciar")
	public EntityModel<TarefaResponse> iniciarTarefa(@PathVariable Integer id) {
		Tarefa tarefa = tarefaService.iniciarTarefaPorId(id);
		
		return tarefaModelAssembler.toModel(tarefa);
	}
	
	@PutMapping("/{id}/concluir")
	public EntityModel<TarefaResponse> concluirTarefa(@PathVariable Integer id) {
		Tarefa tarefa = tarefaService.concluirTarefaPorId(id);
		
		return tarefaModelAssembler.toModel(tarefa);
	}
	
	@PutMapping("/{id}/cancelar")
	public EntityModel<TarefaResponse> cancelarTarefa(@PathVariable Integer id) {
		Tarefa tarefa = tarefaService.cancelarTarefaPorId(id);
		
		return tarefaModelAssembler.toModel(tarefa);
	}
}
