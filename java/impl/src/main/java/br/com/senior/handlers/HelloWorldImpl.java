package br.com.senior.handlers;

import br.com.senior.erpx.trn.HelloWorld;
import br.com.senior.erpx.trn.HelloWorldOutput;
import br.com.senior.messaging.model.HandlerImpl;

@HandlerImpl
public class HelloWorldImpl implements HelloWorld {

    @Override
    public HelloWorldOutput helloWorld() {
        return new HelloWorldOutput("Seja bem vindo mundo ERPX!!!");
    }

}
