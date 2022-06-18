package com.scz.hibernateUtil;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory
{

    public static final SessionFactory sessionFactory;
    public static final ThreadLocal session = new ThreadLocal();

    public HibernateSessionFactory()
    {
    }

    public static Session currentSession()
        throws HibernateException
    {
        Session s = (Session)session.get();
        if(s == null)
        {
            s = sessionFactory.openSession();
            session.set(s);
        }
        return s;
    }

    public static void closeSession()
        throws HibernateException
    {
        Session s = (Session)session.get();
        if(s != null)
        {
            s.close();
        }
        session.set(null);
    }

    static 
    {
        try
        {
            sessionFactory = (new Configuration()).configure("hibernate.cfg.xml").buildSessionFactory();
        }
        catch(ExceptionInInitializerError ex)
        {
            throw new ExceptionInInitializerError(ex);
        }
    }
    
}
