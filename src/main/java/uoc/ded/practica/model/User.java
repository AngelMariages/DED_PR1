package uoc.ded.practica.model;

import uoc.ei.tads.Iterador;
import uoc.ei.tads.LlistaEncadenada;

import java.time.LocalDate;

public class User {
    private final String userId;
    private String name;
    private String surname;
    private LocalDate birthdate;
    private boolean covidCertificate;
    private final LlistaEncadenada<Activity> attendedActivities = new LlistaEncadenada<>();

    public User(String userId, String name, String surname, LocalDate birthdate, boolean covidCertificate) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.covidCertificate = covidCertificate;
    }

    public void update(String name, String surname, LocalDate birthdate, boolean covidCertificate) {
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.covidCertificate = covidCertificate;
    }

    public String getId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public boolean hasCovidCertificate() {
        return covidCertificate;
    }

    public void attendActivity(Activity activity) {
        attendedActivities.afegirAlFinal(activity);
    }

    public Iterador<Activity> getAttendedActivities() {
        return attendedActivities.elements();
    }

    public int numAttendedActivities() {
        int count = 0;

        Iterador<Activity> elements = attendedActivities.elements();
        while (elements.hiHaSeguent()) {
            elements.seguent();
            count++;
        }

        return count;
    }
}
