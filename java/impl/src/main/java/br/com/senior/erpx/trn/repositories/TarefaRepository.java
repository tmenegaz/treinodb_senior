package br.com.senior.erpx.trn.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.senior.erpx.trn.TarefaBaseRepository;
import br.com.senior.erpx.trn.TarefaEntity;

@Repository
public interface TarefaRepository extends TarefaBaseRepository {

    @Transactional
    List<TarefaEntity> findAllAllByDescricao(String descricao);
}
