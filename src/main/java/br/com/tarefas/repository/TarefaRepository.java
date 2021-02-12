package br.com.tarefas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.tarefas.model.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Integer> {

	public List<Tarefa> findByDescricaoIgnoreCaseLike(String descricao);
	
	// as duas abaixo fazem a mesma coisa, a segunda necessita criar
	//@NamedQuery na entitade Tarefa
	
	//faz o inner join e usa como parametro de pesquisa a variavel nomeCategoria
	@Query("select t from Tarefa t inner join t.categoria c where c.nome = ?1")
	public List<Tarefa> findByNomeCategoria(String nomeCategoria);
	
	public List<Tarefa> tarefasPorCategoria(String nomeCategoria);
	
}
