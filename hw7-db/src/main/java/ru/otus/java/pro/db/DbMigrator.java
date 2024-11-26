package ru.otus.java.pro.db;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class DbMigrator {
    private final DataSource dataSource;

    private final String MIGRATION_FILE_NAME = "dbinit.sql";

    Set<String> completedScripts;

    public DbMigrator(DataSource dataSource) {
        this.dataSource = dataSource;
        completedScripts = new HashSet<>();
    }

    public void migrate() throws SQLException {
        createMigrationTable();
        findAllCompletedScripts();

        try {
            if (!completedScripts.contains(MIGRATION_FILE_NAME)) {
                for (String query : readFile(MIGRATION_FILE_NAME)) {
                    dataSource.getStatement().executeUpdate(query);
                }
                addMigrationScriptInHistory(MIGRATION_FILE_NAME);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createMigrationTable() throws SQLException {
        dataSource.getStatement().executeUpdate("""
                create table if not exists migration_history_tab
                (
                    filename varchar(100),
                    created_at timestamp default current_timestamp
                )
                """);
    }

    private void addMigrationScriptInHistory(String script) {
        try(PreparedStatement ps = dataSource.getConnection().prepareStatement("""
                insert into migration_history_tab (filename) values (?);
                """)) {
            ps.setString(1, script);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void findAllCompletedScripts() throws SQLException {
        try(ResultSet rs = dataSource.getStatement().executeQuery("""
                select filename from migration_history_tab;
                """)) {
            while (rs.next()) {
                completedScripts.add(rs.getString(1));
            }
        }
    }

    private String[] readFile(String fileName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
        String data = FileUtils.readFileToString(file, "UTF-8");

        String[] rawQueries = data.split(";");
        String[] queries = new String[rawQueries.length];

        for (int i = 0; i < rawQueries.length; i++) {
            queries[i] = rawQueries[i].trim();
        }

        return queries;
    }
}
