package br.com.senior.erpx.trn.handler;

import javax.inject.Inject;

import br.com.senior.erpx.trn.ConcluirTarefa;
import br.com.senior.erpx.trn.ConcluirTarefaInput;
import br.com.senior.erpx.trn.ConcluirTarefaOutput;
import br.com.senior.erpx.trn.service.TarefaService;
import br.com.senior.messaging.model.HandlerImpl;

@HandlerImpl
public class ConcluirTarefaHandlerImpl implements ConcluirTarefa {
    
    @Inject
    private TarefaService tarefaService;

    @Override
    public ConcluirTarefaOutput concluirTarefa(ConcluirTarefaInput request) {
        tarefaService.startConcluirTarefa(request.id);
        
        return new ConcluirTarefaOutput();
    }

}
