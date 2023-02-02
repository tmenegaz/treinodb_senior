package br.com.senior.handlers;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

import javax.inject.Inject;

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.senior.erpx.trn.CheckData;
import br.com.senior.erpx.trn.Healthcheck;
import br.com.senior.erpx.trn.HealthcheckInput;
import br.com.senior.erpx.trn.HealthcheckOutput;
import br.com.senior.erpx.trn.UpDown;
import br.com.senior.messaging.model.HandlerImpl;
import br.com.senior.messaging.security.AnonymousAction;

@HandlerImpl
public class HealthcheckHandlerImpl implements Healthcheck {
    private static final Logger LOGGER = LoggerFactory.getLogger(HealthcheckHandlerImpl.class);

    @Inject
    private MultiTenantConnectionProvider connectionProvider;

    @Override
    @AnonymousAction
    public HealthcheckOutput healthcheck(HealthcheckInput request) {
        HealthcheckOutput output = new HealthcheckOutput();
        output.checks = new ArrayList<>();

        try (Connection conn = connectionProvider.getAnyConnection(); Statement smt = conn.createStatement()) {
            StringBuilder sql = new StringBuilder("select 1");

            String dbName = conn.getMetaData()
                    .getDatabaseProductName();
            if ("Oracle".equalsIgnoreCase(dbName)) {
                sql.append(" from dual");
            }

            smt.executeQuery(sql.toString());

            output.status = UpDown.UP;
        } catch (Exception e) {
            output.status = UpDown.DOWN;

            CheckData check = new CheckData();
            check.name = "db.conn";
            check.status = UpDown.DOWN;

            output.checks.add(check);

            LOGGER.error(e.getMessage(), e);
        }

        return output;
    }
}
