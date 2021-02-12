package br.com.tarefas.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.tarefas.assembler.UsuarioModelAssembler;
import br.com.tarefas.controller.request.UsuarioRequest;
import br.com.tarefas.controller.response.UsuarioResponse;
import br.com.tarefas.model.Usuario;
import br.com.tarefas.services.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping("/{id}")
	public EntityModel<UsuarioResponse> listarPorId(@PathVariable Integer id) {
		Usuario usuario = usuarioService.getUsuarioPorId(id);
		
		EntityModel<UsuarioResponse> usuarioResponse = usuarioModelAssembler.toModel(usuario);
		
		return usuarioResponse;
	}
	
	@PostMapping
	public ResponseEntity<EntityModel<UsuarioResponse>> salvarUsuario(@RequestBody @Valid UsuarioRequest usuarioRequest) {
		
		Usuario usuario = mapper.map(usuarioRequest, Usuario.class);
		
		Usuario usuarioSalvo = usuarioService.salvar(usuario);
		
		EntityModel<UsuarioResponse> usuarioResponse = usuarioModelAssembler.toModel(usuarioSalvo);
		
		return ResponseEntity
				.created(usuarioResponse.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(usuarioResponse);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<UsuarioResponse>> atualizarUsuario(@PathVariable Integer id, @Valid @RequestBody UsuarioRequest usuarioRequest) {
		
		Usuario usuario = mapper.map(usuarioRequest, Usuario.class);
		Usuario usuarioSalvo = usuarioService.atualizar(id, usuario);
		EntityModel<UsuarioResponse> usuarioResponse = usuarioModelAssembler.toModel(usuarioSalvo);
		
		return ResponseEntity.ok().body(usuarioResponse);		
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluirUsuario(@PathVariable Integer id) {
		usuarioService.deleteById(id);
	}
	
}
