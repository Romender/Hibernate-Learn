package org.rome.hibernate.example;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;

public class SesssionFactoryHelper {

    public final static SesssionFactoryHelper instance = new SesssionFactoryHelper();

    private SessionFactory sessionFactory;

    private SesssionFactoryHelper(){
        Configuration cfg = new Configuration().configure();
        sessionFactory =  cfg.buildSessionFactory();
    }

   public  EntityManager createSesssion() {
        return instance.sessionFactory.createEntityManager();
   }


}
