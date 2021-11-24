package uoc.ded.practica.model;

import uoc.ded.practica.SafetyActivities4Covid19;

public class Rating {
    private final SafetyActivities4Covid19.Rating rating;
    private final String message;
    private final User user;

    public Rating(SafetyActivities4Covid19.Rating rating, String message, User user) {
        this.rating = rating;
        this.message = message;
        this.user = user;
    }

    public SafetyActivities4Covid19.Rating getRating() {
        return rating;
    }

    public User getUser() {
        return user;
    }

    public int getValue() {
        return this.rating.getValue();
    }

    @Override
    public String toString() {
        return "Rating{" +
                "rating=" + rating +
                ", message='" + message + '\'' +
                ", user=" + user +
                '}';
    }
}
