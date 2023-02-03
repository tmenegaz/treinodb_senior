package br.com.senior.erpx.trn.service;

import java.util.UUID;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import br.com.senior.erpx.trn.Situacao;
import br.com.senior.erpx.trn.StartTarefaConcluidaPayload;
import br.com.senior.erpx.trn.TarefaConcluidaPayload;
import br.com.senior.erpx.trn.TarefaEntity;
import br.com.senior.erpx.trn.TrnConstants;
import br.com.senior.erpx.trn.repositories.TarefaRepository;
import br.com.senior.messaging.BrokerException;
import br.com.senior.messaging.ErrorCategory;
import br.com.senior.messaging.Message;
import br.com.senior.messaging.context.functional.MessengerSupplier;
import br.com.senior.messaging.model.ServiceContext;
import br.com.senior.messaging.model.ServiceException;
import br.com.senior.messaging.utils.DtoJsonConverter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TarefaServiceImpl implements TarefaService {

    @Inject
    private MessengerSupplier messenger;

    @Inject
    private TarefaRepository tarefaRepository;

    @Override
    public void startConcluirTarefa(String id) {
        publishMessage(new StartTarefaConcluidaPayload(id), TrnConstants.Events.START_TAREFA_CONCLUIDA);
    }

    @Override
    public void concluirTarefa(String id) {
        TarefaEntity tarefa = tarefaRepository.findById(UUID.fromString(id))
                .orElse(null);
        if (tarefa == null) {
            log.error("Impossível concluir a tarefa. ID: {}. id não encontrado ", id);
            return;
        }

        tarefa.setSituacao(Situacao.CONCLUIDA);
        tarefa = tarefaRepository.save(tarefa);
        publishMessage(new TarefaConcluidaPayload(tarefa.getId().toString(), false, "Tarefa concluída com sucesso"), TrnConstants.Events.TAREFA_CONCLUIDA);

    }

    private void publishMessage(Object payload, String primitive) {

        Message message = new Message(ServiceContext.get()
                .getCurrentTenant(), TrnConstants.DOMAIN, TrnConstants.SERVICE, primitive, DtoJsonConverter.toJSON(payload));
        message.setUsername(ServiceContext.get()
                .getCurrentUsername());

        try {
            ServiceContext.get()
                    .getCurrentMessenger()
                    .publish(message);
        } catch (BrokerException e) {
            log.error("Error: " + e.getMessage());
            throw new ServiceException(ErrorCategory.INTERNAL_ERROR, "Erro quando tentou publicar a primitiva: " + primitive);
        }

    }

}
