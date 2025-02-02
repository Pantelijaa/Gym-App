package com.gymapp.dao;

import com.gymapp.entity.GymMember;
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

public class GymMemberDaoImpl implements Dao<GymMember> {

    @PersistenceContext
    private EntityManager entityManager;

    public GymMemberDaoImpl() {
        Properties prop = new Properties();
        PropertiesHelper.loadPropertiesFromFile(prop, App.CONFIG_FILE);

        Map<String, String> persistenceMap = new HashMap<String, String>();
        persistenceMap.put("javax.persistence.jdbc.url", "jdbc:sqlite:" + prop.getProperty("db.path"));
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.gymapp.gym_app", persistenceMap);
        entityManager = emf.createEntityManager();
    }

    /**
     * {@InheritDoc}
     * 
     * @return Single match of type {@code GymMember}
     */
    @Override
    public GymMember read(int id) {
        return entityManager.find(GymMember.class, id);
    }

    /**
     *  {@inheritDoc} 
     *
     *  @return {@code List<GymMember>} populated with all enitites from <b>Members</b> table
     */ 
    @Override
    public List<GymMember> readAll() {
        Query query = entityManager.createQuery("SELECT e FROM GymMember e");
        return castList(GymMember.class, query.getResultList());
    }

    /**
     * {@InheritDoc}
     * 
     * @param gymMember         Entity of type {@code GymMember}
     */
    @Override
    public void create(GymMember gymMember) {
        executeInsideTansaction(entityManager, entityManager -> entityManager.persist(gymMember));
    }

    /**
     * {@InheritDoc}
     * 
     * @param gymMember         Entity of type {@code GymMember}
     */
    @Override
    public void update(GymMember gymMember) {
        executeInsideTansaction(entityManager, entityManager -> entityManager.merge(gymMember));
    }

    @Override
    public void delete(GymMember gymMember) {
        executeInsideTansaction(entityManager, entityManager -> entityManager.remove(gymMember));
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public Integer nextInsertId() {
        return (Integer)entityManager.createNativeQuery("SELECT next_val " +
                                                        "FROM Members_SEQ ",
                                                        Integer.class)
                                     .getSingleResult();
    }


}
