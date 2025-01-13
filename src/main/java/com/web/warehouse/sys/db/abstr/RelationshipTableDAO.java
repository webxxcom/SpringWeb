package com.web.warehouse.sys.db.abstr;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface RelationshipTableDAO<R, V> extends Processable<V> {
    Optional<V> getById(long firstId, long secId);
    List<V> getAll(long firstId);
    List<R> getAllRelations(long firstId);

    void insert(long firstId, R rec);
    void insertAll(long firstId, List<R> recs);
    long generateId(Connection con);
}
