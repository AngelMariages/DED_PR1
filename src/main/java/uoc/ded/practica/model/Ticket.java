package uoc.ded.practica.model;

public class Ticket {
    private final User user;
    private int seat;

    public Ticket(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getSeat() {
        return seat;
    }
}
