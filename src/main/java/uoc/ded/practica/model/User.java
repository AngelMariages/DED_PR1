package uoc.ded.practica.model;

import java.time.LocalDate;

public class User {
    private final String userId;
    private String name;
    private String surname;
    private LocalDate birthdate;
    private boolean covidCertificate;

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

    public String getUserId() {
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
}
