package com.web.warehouse.sys.db.mysql.dao;

import com.web.warehouse.sys.db.abstr.ConnectionManager;
import com.web.warehouse.sys.db.abstr.SupplyJournalDAO;
import com.web.warehouse.sys.entity.Journal;
import org.jetbrains.annotations.Contract;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public final class MySqlSupplyJournalDAO implements SupplyJournalDAO {
    private final ConnectionManager connectionManager;

    public MySqlSupplyJournalDAO(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Optional<Journal> getById(long id) {
        return Optional.empty();
    }

    @Contract(pure = true)
    @Override
    public List<Journal> getByName(String name) {
        return List.of();
    }

    @Override
    public List<Journal> getByNameLike(String pattern) {
        return List.of();
    }

    @Override
    public List<Journal> getAll() {
        return List.of();
    }

    @Override
    public void addAll(Journal[] entities) {
        throw new RuntimeException();
    }

    @Override
    public void add(Journal entity) {
            throw new RuntimeException();
    }

    @Override
    public void remove(long id) {
        throw new RuntimeException();
    }

    @Override
    public void update(Journal updated) {
        throw new RuntimeException();
    }

    @Override
    public Journal map(ResultSet rs) throws SQLException {
        throw new RuntimeException();
    }
}
