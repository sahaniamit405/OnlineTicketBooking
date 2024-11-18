package com.OnlineTicketBooking.Connection;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.OnlineTicketBooking.Entity.User;
import com.OnlineTicketBooking.Entity.Event;
import com.OnlineTicketBooking.Entity.Ticket;

public class HibernateUtil {

    private static SessionFactory factory;

    static {
        try {
            factory = new Configuration().configure("hibernate.cfg.xml")
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Event.class)
                    .addAnnotatedClass(Ticket.class)
                    .buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Failed to create session factory");
        }
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
