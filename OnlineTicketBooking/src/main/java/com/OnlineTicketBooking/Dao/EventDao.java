package com.OnlineTicketBooking.Dao;

import com.OnlineTicketBooking.Connection.HibernateUtil;
import com.OnlineTicketBooking.Entity.Event;
import org.hibernate.Session;
import java.util.List;


public class EventDao {
    public void addEvent(Event event) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(event);
        session.getTransaction().commit();
        session.close();
    }

    public List<Event> listEvents() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Event> events = session.createQuery("FROM Event", Event.class).getResultList();

        session.close();
        return events;
    }
}

