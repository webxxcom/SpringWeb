package ua.nure.st.kpp.example.demo.dao.mysql.dao;

import org.jetbrains.annotations.Contract;
import ua.nure.st.kpp.example.demo.dao.abstr.*;
import ua.nure.st.kpp.example.demo.entity.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public final class MySqlSupplyJournalDAO implements SupplyJournalDAO {
    private final ConnectionManager cm;

    public MySqlSupplyJournalDAO(ConnectionManager cm) {
        this.cm = cm;
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
    public void insertAll(Journal[] entities) {
        throw new RuntimeException();
    }

    @Override
    public void insert(Journal entity) {
            throw new RuntimeException();
    }

    @Override
    public void delete(long id) {
        throw new RuntimeException();
    }

    @Override
    public void edit(long id, Journal editedProduct) {
        throw new RuntimeException();
    }

    @Override
    public Journal map(ResultSet rs) throws SQLException {
        throw new RuntimeException();
    }
}
