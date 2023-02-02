package br.com.senior.handlers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.senior.erpx.trn.HealthcheckInput;
import br.com.senior.erpx.trn.HealthcheckOutput;
import br.com.senior.erpx.trn.UpDown;

@ExtendWith(MockitoExtension.class)
class HealthcheckHandlerImplTest {

    @InjectMocks
    private HealthcheckHandlerImpl handler;

    @Mock
    private MultiTenantConnectionProvider connectionProvider;

    DatabaseMetaData dbJDBCMetadata = mock(DatabaseMetaData.class);
    Connection c = Mockito.mock(Connection.class);
    Statement stmt = mock(Statement.class);
    ResultSet rs = mock(ResultSet.class);

    private void mockConnection() throws SQLException {
        when(connectionProvider.getAnyConnection()).thenReturn(c);
        when(c.createStatement()).thenReturn(stmt);
        when(c.getMetaData()).thenReturn(dbJDBCMetadata);
    }

    @Test
    public void healthcheck_sucesso() throws SQLException {
        HealthcheckInput request = new HealthcheckInput();

        mockConnection();

        when(c.getMetaData().getDatabaseProductName()).thenReturn("Oracle");
        when(stmt.executeQuery(Mockito.any(String.class))).thenReturn(rs);

        HealthcheckOutput output = handler.healthcheck(request);
        assertThat(UpDown.UP).isEqualTo(output.status);
    }

    @Test
    public void healthcheck_erro() {
        HealthcheckInput request = new HealthcheckInput();
        HealthcheckOutput output = handler.healthcheck(request);

        assertThat(UpDown.DOWN).isEqualTo(output.status);
    }

    @Test
    public void healthcheck_sucessoProductNameNulo() throws SQLException {
        HealthcheckInput request = new HealthcheckInput();

        mockConnection();

        when(c.getMetaData().getDatabaseProductName()).thenReturn(null);
        when(stmt.executeQuery(Mockito.any(String.class))).thenReturn(rs);

        HealthcheckOutput output = handler.healthcheck(request);
        assertThat(UpDown.UP).isEqualTo(output.status);
    }
}
