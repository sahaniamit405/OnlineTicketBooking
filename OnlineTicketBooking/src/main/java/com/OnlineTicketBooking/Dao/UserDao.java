package com.OnlineTicketBooking.Dao;

import com.OnlineTicketBooking.Connection.HibernateUtil;
import com.OnlineTicketBooking.Entity.User;
import org.hibernate.Session;
//import java.util.List;

//public class UserDao {
//    public void createUser(User user) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            session.beginTransaction();
//            session.save(user);
//            session.getTransaction().commit();
//        }
//    }
//
//    public User readUser(int id) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            return session.get(User.class, id);
//        }
//    }
//
//    public void updateUser(User user) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            session.beginTransaction();
//            session.update(user);
//            session.getTransaction().commit();
//        }
//    }
//
//    public void deleteUser(int id) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            User user = session.get(User.class, id);
//            if (user != null) {
//                session.beginTransaction();
//                session.delete(user);
//                session.getTransaction().commit();
//            }
//        }
//    }
//
//    public List<User> listUsers() {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            return session.createQuery("from User", User.class).list();
//        }
//    }
//}


public class UserDao {
    public void registerUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    public User loginUser(String username, String password) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        User user = (User) session.createQuery("FROM User WHERE username=:username AND password=:password")
                                  .setParameter("username", username)
                                  .setParameter("password", password)
                                  .uniqueResult();
        session.close();
        return user;
    }
}

