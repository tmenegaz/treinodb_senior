package br.com.senior.migration;

import org.springframework.stereotype.Component;

import br.com.senior.config.migration.DatabaseMigration;
import br.com.senior.config.migration.DatabaseMigratorModules;
import br.com.senior.messaging.model.db.PersistenceManager;

@Component
public class DatabaseMigratorModulesComum extends DatabaseMigration implements DatabaseMigratorModules {

    @Override
    public String getScriptsLocation(PersistenceManager persistence) {
        return localDefault + "global/" + persistence.getAdapter().getDbType() + "/";
    }

    @Override
    public String getSchemaVersionTableName() {
        return "schema_version";
    }

    @Override
    public boolean migrate(PersistenceManager persistence, String schema, boolean forceMigration) {
        return super.migrateCurrentTenant(persistence, schema, forceMigration);
    }

    @Override
    public boolean migrateModules() {
        return false;
    }
}
