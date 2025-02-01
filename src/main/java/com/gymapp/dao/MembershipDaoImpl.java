package com.gymapp.dao;

import com.gymapp.entity.Membership;
import com.gymapp.helpers.PropertiesHelper;
import com.gymapp.App;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;


public class MembershipDaoImpl implements Dao<Membership> {

    @PersistenceContext
    private EntityManager entityManager;

    public MembershipDaoImpl() {
        Properties prop = new Properties();
        PropertiesHelper.loadPropertiesFromFile(prop, App.CONFIG_FILE);

        Map<String, String> persistenceMap = new HashMap<String, String>();
        persistenceMap.put("javax.persistence.jdbc.url", "jdbc:sqlite:" + prop.getProperty("db.path"));
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.gymapp.gym_app", persistenceMap);
        entityManager = emf.createEntityManager();
    }

    @Override
    public Membership read(int id) {
        return entityManager.find(Membership.class, id);
    }

   @Override
    public List<Membership> readAll() {
        Query query = entityManager.createQuery("SELECT e FROM Membership e");
        return castList(Membership.class, query.getResultList());
    }

    @Override
    public void create(Membership membership) {
        executeInsideTansaction(entityManager, entityManager -> entityManager.persist(membership));
    }

    @Override
    public void update(Membership membership) {
        executeInsideTansaction(entityManager, entityManager -> entityManager.merge(membership));
    }

    @Override
    public void delete(Membership membership) {
        executeInsideTansaction(entityManager, entityManager -> entityManager.remove(membership));
    }

    @Override
    public Integer findLastInsertedId() {
        return (Integer)entityManager.createNativeQuery("SELECT next_val " +
                                                        "FROM Membership_SEQ ",
                                                        Integer.class)
                                     .getSingleResult();
    }

}
