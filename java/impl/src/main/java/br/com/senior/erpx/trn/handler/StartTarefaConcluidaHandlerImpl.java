package br.com.senior.erpx.trn.handler;

import javax.inject.Inject;

import br.com.senior.erpx.trn.StartTarefaConcluida;
import br.com.senior.erpx.trn.StartTarefaConcluidaPayload;
import br.com.senior.erpx.trn.TarefaConcluida;
import br.com.senior.erpx.trn.TarefaConcluidaPayload;
import br.com.senior.erpx.trn.service.TarefaService;
import br.com.senior.erpx.trn.service.TarefaServiceImpl;
import br.com.senior.messaging.model.HandlerImpl;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@HandlerImpl
public class StartTarefaConcluidaHandlerImpl implements StartTarefaConcluida, TarefaConcluida {
    
    @Inject
    private TarefaService tarefaService;

    @Override
    public void startTarefaConcluida(StartTarefaConcluidaPayload payload) {
        log.debug("Tarefa id: " + payload.id + " recebeu a mensagem");
        tarefaService.concluirTarefa(payload.id);

    }

    @Override
    public void tarefaConcluida(TarefaConcluidaPayload payload) {
        log.debug("Tarefa id: " + payload.id + " concluído com sucesso");
        
    }

}
