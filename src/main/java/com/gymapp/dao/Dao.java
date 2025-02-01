package com.gymapp.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.function.Consumer;
import java.util.Collection;
import java.util.ArrayList;

public interface Dao<T> {
    
    T read(int id);

    List<T> readAll();

    void create(T t);

    void update(T t);

    void delete(T t);
    
    Integer findLastInsertedId();

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
