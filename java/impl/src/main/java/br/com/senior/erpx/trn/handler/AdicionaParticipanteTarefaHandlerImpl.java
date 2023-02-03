package br.com.senior.erpx.trn.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.senior.erpx.trn.AdicionaParticipanteTarefa;
import br.com.senior.erpx.trn.AdicionaParticipanteTarefaInput;
import br.com.senior.erpx.trn.AdicionaParticipanteTarefaOutput;
import br.com.senior.erpx.trn.ParticipanteEntity;
import br.com.senior.erpx.trn.TarefaEntity;
import br.com.senior.erpx.trn.repositories.ParticipanteRepository;
import br.com.senior.erpx.trn.repositories.TarefaRepository;
import br.com.senior.messaging.model.HandlerImpl;

@HandlerImpl
public class AdicionaParticipanteTarefaHandler implements AdicionaParticipanteTarefa {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private ParticipanteRepository participanteRepository;

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public AdicionaParticipanteTarefaOutput adicionaParticipanteTarefa(AdicionaParticipanteTarefaInput request) {
        AdicionaParticipanteTarefaOutput ret = new AdicionaParticipanteTarefaOutput();

        try {
            TarefaEntity tarefa = new TarefaEntity();
            tarefa.setDescricao(request.descricao);
            ParticipanteEntity entity = new ParticipanteEntity();
            entity.setNome(request.participante.nome);

            if (tarefa.getParticipantes() == null) {
                List<ParticipanteEntity> participante = new ArrayList<ParticipanteEntity>();
                participante.add(participanteRepository.save(entity));
                tarefa.setParticipantes(participante);
            } else {
                tarefa.getParticipantes().add(participanteRepository.save(entity));
            }

            tarefa = tarefaRepository.save(tarefa);
            ret.result = "Participante adicionado à tarefa.";
        } catch (Exception e) {
            logger.severe("Erro:" + e.getMessage());
            e.printStackTrace();
            ret.result = "Participante não pôde ser adicionado à tarefa.";
        }

        return ret;
    }
}

