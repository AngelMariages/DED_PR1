package uoc.ded.practica.model;

import uoc.ded.practica.SafetyActivities4Covid19;
import uoc.ei.tads.CuaVectorImpl;
import uoc.ei.tads.Iterador;
import uoc.ei.tads.LlistaEncadenada;

import java.util.Date;

public class Activity {
    private final String actId;
    private final String description;
    private final Date date;
    private final SafetyActivities4Covid19.Mode mode;
    private final int organizationId;

    private final CuaVectorImpl<Ticket> ticketQueue;
    private final int places;
    private int givenTickets = 0;

    private final LlistaEncadenada<Rating> ratingLinkedList = new LlistaEncadenada<>();
    private double rating = 0.0;

    public Activity(String actId, String description, Date date, SafetyActivities4Covid19.Mode mode, int places, int organizationId) {
        this.actId = actId;
        this.description = description;
        this.date = date;
        this.mode = mode;
        this.places = places;
        this.ticketQueue = new CuaVectorImpl<>(places);
        this.organizationId = organizationId;
    }

    public String getActId() {
        return actId;
    }

    public void enqueueATicket(Ticket ticket) {
        ticketQueue.encuar(ticket);

        givenTickets++;

        ticket.setSeat(givenTickets);
    }

    public Ticket pickATicket() {
        return ticketQueue.desencuar();
    }

    public int availableTickets() {
        return places - givenTickets;
    }

    public void addRating(Rating rating) {
        ratingLinkedList.afegirAlFinal(rating);

        // Whenever there's a new rating, we calculate the ranking again
        this.rating = calculateRating();
    }

    private double calculateRating() {
        Iterador<Rating> elements = ratingLinkedList.elements();

        double ratingSum = 0.0;
        int numRatings = 0;

        while (elements.hiHaSeguent()) {
            Rating rating = elements.seguent();

            ratingSum += rating.getValue();
            numRatings++;
        }

        return ratingSum / numRatings;
    }

    public double rating() {
        return this.rating;
    }

    public Iterador<Rating> getRatings() {
        return this.ratingLinkedList.elements();
    }

    @Override
    public String toString() {
        return "Activity{" +
                "actId='" + actId + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", mode=" + mode +
                ", organizationId=" + organizationId +
                ", ticketQueue=" + ticketQueue +
                ", places=" + places +
                ", givenTickets=" + givenTickets +
                ", ratingLinkedList=" + ratingLinkedList +
                ", rating=" + rating +
                '}';
    }
}
