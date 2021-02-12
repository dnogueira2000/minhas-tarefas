package br.com.tarefas.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.tarefas.model.Tarefa;

@SpringBootTest
public class TarefaRepositoryTest {

	@Autowired
	private TarefaRepository tarefaRepository;
	
	@Test
	void testFindByNomeCategoria() {
		List<Tarefa> tarefas = tarefaRepository.findByNomeCategoria("Estudos");
		Assertions.assertEquals(2, tarefas.size());
	}
	
	@Test
	void testTarefasPorCategoria() {
		List<Tarefa> tarefas = tarefaRepository.tarefasPorCategoria("Estudos");
		Assertions.assertEquals(2, tarefas.size());
	}
}
