package com.OnlineTicketBooking.Dao;

import com.OnlineTicketBooking.Connection.HibernateUtil;
import com.OnlineTicketBooking.Entity.Event;
import com.OnlineTicketBooking.Entity.Ticket;
import com.OnlineTicketBooking.Entity.User;

import org.hibernate.Session;
import java.util.List;


//public class TicketDao {
//    public void createTicket(Ticket ticket) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            session.beginTransaction();
//            session.save(ticket);
//            session.getTransaction().commit();
//        }
//    }
//
//    public List<Ticket> listTickets() {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            return session.createQuery("from Ticket", Ticket.class).list();
//        }
//    }
//}


public class TicketDao {
    public void bookTicket(User user, Event event) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setEvent(event);
        ticket.setBookingDate(new java.sql.Date(System.currentTimeMillis()));

        session.save(ticket);
        session.getTransaction().commit();
        session.close();
    }

    public List<User> getUsersByEvent(Event event) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session.createQuery("SELECT t.user FROM Ticket t WHERE t.event.id = :eventId", User.class)
                      .setParameter("eventId", event.getId())
                      .list();
    }

    public List<Event> getEventsByUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session.createQuery("SELECT t.event FROM Ticket t WHERE t.user.id = :userId", Event.class)
                      .setParameter("userId", user.getId())
                      .list();
    }
}
