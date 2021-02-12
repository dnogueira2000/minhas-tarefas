package br.com.tarefas.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import br.com.tarefas.controller.UsuarioController;
import br.com.tarefas.controller.response.UsuarioResponse;
import br.com.tarefas.model.Usuario;


@Component
public class UsuarioModelAssembler  implements RepresentationModelAssembler<Usuario, EntityModel<UsuarioResponse>> {

	@Autowired
	private ModelMapper mapper;

	@Override
	public EntityModel<UsuarioResponse> toModel(Usuario usuario) {
		UsuarioResponse usuarioResponse = mapper.map(usuario, UsuarioResponse.class);

		EntityModel<UsuarioResponse> usuarioModel = EntityModel.of(usuarioResponse,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarPorId(usuarioResponse.getId())).withSelfRel()
				);
		
		return usuarioModel;
	}
	
}
