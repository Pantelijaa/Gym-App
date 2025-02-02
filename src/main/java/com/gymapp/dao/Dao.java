package com.gymapp.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.function.Consumer;
import java.util.Collection;
import java.util.ArrayList;

/**
 * Interface for <b>Data Access Objects</b> perofrming {@code CRUD} statement
 * on Java Object representations of SQLite database. 
 * <p>
 * Each <b>Entity</b> has it's own implementation of such interface.
 * </p>
 * @param <T>           the type of the element
 */
public interface Dao<T> {
    /**
     * Performs {@code SELECT * FROM table_name WHERE id = ID} SQL statement
     * where <b>ID</b> is taken as parameter and returns single <b>Entity</b> match.
     * 
     * @param id        of searched element
     * @return          Single match of generic type {@code T}
     */
    T read(int id);

    /**
     * Performs {@code SELECT * FROM table_name} SQL statement
     * returning List of all <b>Entities</b> as an result. 
     * 
     * @return         {@code List<T>} populated with all <b>enitites</b> from <b>table_name</b>
     */
    List<T> readAll();

    /**
     * Performs {@code INSERT INTO table_name VALUES ...} SQL statement.
     * <b>table_name</b> is determined by class in implementation.
     * 
     * @param t         Entity of generic type {@code T}
     */
    void create(T t);
    
    /**
     * Performs {@code UPDATE table_name SET .... WHERE id = ID} SQL statement.
     * Data from {@code Object} passed as parameter will replace current data in database.
     * <p>
     * <b>table_name</b> is determined by class in implementation.
     * </p>
     * <p>
     * <b>ID</b> is determined by {@code id} field of {@code Object} passed as parameter.
     * </p>
     * 
     * @param t         Entity of generic type T
     */
    void update(T t);

    /**
     * Performs {@code DELETE FROM table_name WHERE condition} SQL statement.
     * <p>
     * <b>condition</b> is determined by {@code Object} passed as parameter.
     * </p>
     * 
     * @param t         Entity of generic type T
     */
    void delete(T t);
    

    /**
     * Performs {@code SELECT} SQL statement on {@code _SEQ} table of <b>Entity</b>.
     * 
     * @return Index of next elemented to be inserted
     */
    Integer nextInsertId();

    default void executeInsideTansaction(EntityManager entityManager, Consumer<EntityManager> action) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit();
        } catch (RuntimeException re) {
            tx.rollback();
            throw re;
        }
    }

    default List<T> castList(Class<? extends T> className, Collection<?> c) {
        List<T> r = new ArrayList<T>(c.size());
        for (Object o : c)
            r.add(className.cast(o));
        return r;
    }
}
