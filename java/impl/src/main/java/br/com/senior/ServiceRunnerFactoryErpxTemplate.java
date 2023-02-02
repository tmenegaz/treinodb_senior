package br.com.senior;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import br.com.senior.config.service.DomainServiceRunner;
import br.com.senior.messaging.model.ServiceRunner;

@Component
public class ServiceRunnerFactoryErpxTemplate implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        applicationContext = appContext;
    }

    public static ServiceRunner createRunner(br.com.senior.messaging.model.Service service) {
        return applicationContext.getBean(DomainServiceRunner.class, service);
    }

}
