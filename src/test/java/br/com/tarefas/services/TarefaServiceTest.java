package br.com.tarefas.services;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.tarefas.exception.TarefaStatusException;
import br.com.tarefas.model.Tarefa;
import br.com.tarefas.model.TarefaStatus;
import br.com.tarefas.repository.TarefaRepository;

@ExtendWith(MockitoExtension.class)
public class TarefaServiceTest {

	@Mock
	private TarefaRepository tarefaRepository;
	
	@InjectMocks
	private TarefaService tarefaService;
	
	@Test
	void naoDeveConcluirTarefaCancelada() {
		Integer idExemplo = 1;
		
		Tarefa tarefa = new Tarefa();
		tarefa.setId(idExemplo);
		tarefa.setDescricao("Teste 01");
		tarefa.setStatus(TarefaStatus.CANCELADA);
		
		Mockito.when(tarefaRepository.findById(idExemplo)).thenReturn(Optional.of(tarefa));
		
		Assertions.assertThrows(TarefaStatusException.class, 
				() -> tarefaService.concluirTarefaPorId(idExemplo));
	}
	
	@Test
	void naoDeveCancelarTarefaConcluida() {
		Integer idExemplo = 1;
		
		Tarefa tarefa = new Tarefa();
		tarefa.setId(idExemplo);
		tarefa.setDescricao("Teste 01");
		tarefa.setStatus(TarefaStatus.CONCLUIDA);
		
		Mockito.when(tarefaRepository.findById(idExemplo)).thenReturn(Optional.of(tarefa));
		
		Assertions.assertThrows(TarefaStatusException.class, 
				() -> tarefaService.cancelarTarefaPorId(idExemplo));
	}
	
}
