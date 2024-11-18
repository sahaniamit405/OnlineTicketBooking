package com.OnlineTicketBooking.Entity;
import java.util.Date;

import javax.persistence.Column;
//import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
//import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
//import javax.persistence.Table;
import javax.persistence.TemporalType;


@Entity
@Table(name = "Tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public Date getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

	@ManyToOne
	@JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;
    @Temporal(TemporalType.DATE)
    @Column(name="booking_date")
    private Date bookingDate;

    // Getters and Setters
}

