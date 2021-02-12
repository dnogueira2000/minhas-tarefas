package br.com.tarefas.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.tarefas.exception.TarefaStatusException;
import br.com.tarefas.model.Tarefa;
import br.com.tarefas.model.TarefaStatus;

@SpringBootTest
public class TarefaServiceIntegrationTest {

	@Autowired
	private TarefaService tarefaService;
	
	@Test
	void deveIniciarTarefa() {
		Tarefa tarefa = tarefaService.iniciarTarefaPorId(3);
		Assertions.assertEquals(TarefaStatus.EM_ANDAMENTO, tarefa.getStatus());
	}
	
	@Test
	void naoDeveIniciarTarefaConcluida() {
		Tarefa tarefa = tarefaService.getTarefaPorId(3);
		tarefa.setStatus(TarefaStatus.CONCLUIDA);
		tarefaService.salvarTarefa(tarefa);
		
		Assertions.assertThrows(TarefaStatusException.class, () -> tarefaService.iniciarTarefaPorId(3));
	}
	
	@Test
	void naoDeveConcluirTarefaCancelada() {
		Tarefa tarefa = tarefaService.getTarefaPorId(3);
		tarefa.setStatus(TarefaStatus.CANCELADA);
		tarefaService.salvarTarefa(tarefa);
		
		Assertions.assertThrows(TarefaStatusException.class, () -> tarefaService.iniciarTarefaPorId(3));
	}
	
	@Test
	void naoDeveCancelarTarefaConcluida() {
		Tarefa tarefa = tarefaService.getTarefaPorId(3);
		tarefa.setStatus(TarefaStatus.CONCLUIDA);
		tarefaService.salvarTarefa(tarefa);
		
		Assertions.assertThrows(TarefaStatusException.class, () -> tarefaService.iniciarTarefaPorId(3));
	}
	
}
