package br.com.tarefas.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tarefas.exception.TarefaStatusException;
import br.com.tarefas.model.Tarefa;
import br.com.tarefas.model.TarefaStatus;
import br.com.tarefas.repository.TarefaRepository;

@Service
public class TarefaService {

	@Autowired
	private TarefaRepository tarefaRepository;
	
	public List<Tarefa> getTodasTarefas() {
		return tarefaRepository.findAll();
	}
	
	public List<Tarefa> getTarefasPorDescricao(String descricao) {
		return tarefaRepository.findByDescricaoIgnoreCaseLike("%" + descricao + "%");
	}
	
	public Tarefa getTarefaPorId(Integer id) {
		return tarefaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
	}
	
	public Tarefa salvarTarefa(Tarefa tarefa) {
		return tarefaRepository.save(tarefa);
	}
	
	public void deletePorId(Integer id) {
		tarefaRepository.deleteById(id);
	}
	
	public Tarefa iniciarTarefaPorId(Integer id) {
		Tarefa tarefa = getTarefaPorId(id);
		
		if(!TarefaStatus.ABERTO.equals(tarefa.getStatus()))
			throw new TarefaStatusException("Não é possível iniciar a tarefa com status " 
					+ tarefa.getStatus().name() );
		
		tarefa.setStatus(TarefaStatus.EM_ANDAMENTO);
		
		tarefaRepository.save(tarefa);
		return tarefa;
	}
	
	public Tarefa concluirTarefaPorId(Integer id) {
		Tarefa tarefa = getTarefaPorId(id);
		
		if(TarefaStatus.CANCELADA.equals(tarefa.getStatus()))
			throw new TarefaStatusException();
		
		tarefa.setStatus(TarefaStatus.CONCLUIDA);
		
		tarefaRepository.save(tarefa);
		return tarefa;
	}
	
	public Tarefa cancelarTarefaPorId(Integer id) {
		Tarefa tarefa = getTarefaPorId(id);
		
		if(TarefaStatus.CONCLUIDA.equals(tarefa.getStatus()))
			throw new TarefaStatusException("Tarefa não pode ser cancelada, pois, seu status é Concluída");
		
		tarefa.setStatus(TarefaStatus.CANCELADA);
		
		tarefaRepository.save(tarefa);
		return tarefa;
	}
}
