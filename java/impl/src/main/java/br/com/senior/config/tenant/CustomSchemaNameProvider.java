package br.com.senior.config.tenant;

import org.springframework.stereotype.Component;

import br.com.senior.erpx.trn.TrnConstants;
import br.com.senior.messaging.model.db.SchemaNameProvider;

@Component
public class CustomSchemaNameProvider implements SchemaNameProvider {

    @Override
    public String getSchemaName(String tenant) {
        return TrnConstants.DOMAIN + "_" + TrnConstants.SERVICE + "_" + tenant;
    }

}
