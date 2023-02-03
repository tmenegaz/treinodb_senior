package br.com.senior.erpx.trn.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.senior.erpx.trn.ObterParticipanteTarefa;
import br.com.senior.erpx.trn.ObterParticipanteTarefaInput;
import br.com.senior.erpx.trn.ObterParticipanteTarefaOutput;
import br.com.senior.erpx.trn.Participante;
import br.com.senior.erpx.trn.ParticipanteEntity;
import br.com.senior.erpx.trn.TarefaEntity;
import br.com.senior.erpx.trn.repositories.TarefaRepository;
import br.com.senior.messaging.model.HandlerImpl;

@HandlerImpl
public class ObterParticipanteTarefaHandlerImpl implements ObterParticipanteTarefa {

    @Autowired
    private TarefaRepository repository = null;

    @Override
    public ObterParticipanteTarefaOutput obterParticipanteTarefa(ObterParticipanteTarefaInput request) {
        List<ParticipanteEntity> ret = new ArrayList<ParticipanteEntity>();
        List<TarefaEntity> tarefas = repository.findAll();

        if (tarefas.isEmpty()) {
            return null;
        }

        for (TarefaEntity tarefaEntity : tarefas) {
            if (request.descricao == null //
                || request.descricao.isEmpty() // 
                || (tarefaEntity.getDescricao() != null && tarefaEntity.getDescricao().contains(request.descricao))) {
                ret.addAll(tarefaEntity.getParticipantes());
            }
        }

        return new ObterParticipanteTarefaOutput(ret.stream().map(p -> new Participante(p.getNome())).collect(Collectors.toList()));
    }


}
