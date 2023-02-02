package br.com.senior;

import java.util.function.Function;

import br.com.senior.config.service.DomainServiceRunner;
import br.com.senior.erpx.trn.TrnConstants;
import br.com.senior.messaging.model.Server;
import br.com.senior.messaging.model.Service;
import br.com.senior.messaging.model.ServiceDescription;
import br.com.senior.messaging.model.ServiceRunner;

@ServiceDescription(domain=TrnConstants.DOMAIN, name=TrnConstants.SERVICE, packages={
	"br.com.senior", "br.com.senior.custom.fieldcustomization.event.handler"  })
public class TreinoDbServer extends Server {

	public static void main(String[] args) {
		bootstrap(new TreinoDbServer());
	}
	
	@Override
    protected Function<Service, ServiceRunner> getServiceRunnerFactory() {
        return DomainServiceRunner::createServiceRunner;
    }
}
