package com.gymapp.dao;

import com.gymapp.entity.History;
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

public class HistoryDaoImpl implements Dao<History> {

    @PersistenceContext
    private EntityManager entityManager;

    public HistoryDaoImpl() {
        Properties prop = new Properties();
        PropertiesHelper.loadPropertiesFromFile(prop, App.CONFIG_FILE);

        Map<String, String> persistenceMap = new HashMap<String, String>();
        persistenceMap.put("javax.persistence.jdbc.url", "jdbc:sqlite:" + prop.getProperty("db.path"));
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.gymapp.gym_app", persistenceMap);
        entityManager = emf.createEntityManager();
    }

    /**
     * {@inheritDoc}
     * @return Single match of type {@code History}
     */
    @Override
    public History read(int id) {
        return entityManager.find(History.class, id);
    }

    /**
    *  {@inheritDoc}
    * 
    *  @return {@code List<History>} populated with all enitites from <b>History</b> table
    */ 
   @Override
    public List<History> readAll() {
        Query query = entityManager.createQuery("SELECT e FROM History e");
        return castList(History.class, query.getResultList());
    }

    /**
     * {@InheritDoc}
     * 
     * @param history       Entity of type {@code History}
     */
    @Override
    public void create(History history) {
        executeInsideTansaction(entityManager, entityManager -> entityManager.persist(history));
    }

    /**
     * {@InheritDoc}
     * 
     * @param history         Entity of type {@code History}
     */
    @Override
    public void update(History history) {
        executeInsideTansaction(entityManager, entityManager -> entityManager.merge(history));
    }

    /**
     * {@InheritDoc}
     * 
     * @param history         Entity of type {@code History}
     */
    @Override
    public void delete(History history) {
        executeInsideTansaction(entityManager, entityManager -> entityManager.remove(history));
    }    
    
    /**
     * {@InheritDoc}
     */
    @Override
    public Integer nextInsertId() {
        return (Integer)entityManager.createNativeQuery("SELECT next_val " +
                                                        "FROM History_SEQ ",
                                                        Integer.class)
                                     .getSingleResult(); 
    }
}
