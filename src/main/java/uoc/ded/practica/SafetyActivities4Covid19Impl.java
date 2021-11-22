package uoc.ded.practica;

import java.time.LocalDate;
import java.util.Date;

import uoc.ded.practica.exceptions.ActivityNotFoundException;
import uoc.ded.practica.exceptions.LimitExceededException;
import uoc.ded.practica.exceptions.NoActivitiesException;
import uoc.ded.practica.exceptions.NoRatingsException;
import uoc.ded.practica.exceptions.NoRecordsException;
import uoc.ded.practica.exceptions.OrganizationNotFoundException;
import uoc.ded.practica.exceptions.UserNotFoundException;
import uoc.ded.practica.exceptions.UserNotInActivityException;
import uoc.ded.practica.model.Activity;
import uoc.ded.practica.model.Organization;
import uoc.ded.practica.model.Record;
import uoc.ded.practica.model.Ticket;
import uoc.ded.practica.model.User;
import uoc.ded.practica.util.Log;
import uoc.ei.tads.Iterador;

public class SafetyActivities4Covid19Impl implements SafetyActivities4Covid19 {
    private static final String TAG = "SafetyActivities4Covid19Impl";

    private final Organization[] organizations = new Organization[this.O];

    public SafetyActivities4Covid19Impl() {
        Log.d(TAG, "constructor");
    }

    @Override
    public void addUser(String userId, String name, String surname, LocalDate birthday, boolean covidCertificate) {

    }

    @Override
    public void addOrganization(int organizationId, String name, String description) {

    }

    @Override
    public void addRecord(String recordId, String actId, String description, Date date, Mode mode, int num, int organizationId) throws OrganizationNotFoundException {

    }

    @Override
    public void updateRecord(Status status, Date date, String description) throws NoRecordsException {

    }

    @Override
    public void createTicket(String userId, String actId) throws UserNotFoundException, ActivityNotFoundException, LimitExceededException {

    }

    @Override
    public Ticket assignSeat(String actId) throws ActivityNotFoundException {
        return null;
    }

    @Override
    public void addRating(String actId, Rating rating, String message, String userId) throws ActivityNotFoundException, UserNotFoundException, UserNotInActivityException {

    }

    @Override
    public Iterador<uoc.ded.practica.model.Rating> getRatings(String actId) throws ActivityNotFoundException, NoRatingsException {
        return null;
    }

    @Override
    public Activity bestActivity() throws ActivityNotFoundException {
        return null;
    }

    @Override
    public User mostActiveUser() throws UserNotFoundException {
        return null;
    }

    @Override
    public double getInfoRejectedRecords() {
        return 0;
    }

    @Override
    public Iterador<Activity> getAllActivities() throws NoActivitiesException {
        return null;
    }

    @Override
    public Iterador<Activity> getActivitiesByOrganization(int organizationId) throws NoActivitiesException {
        return null;
    }

    @Override
    public Iterador<Activity> getActivitiesByUser(String userId) throws NoActivitiesException {
        return null;
    }

    @Override
    public User getUser(String userId) {
        return null;
    }

    @Override
    public Organization getOrganization(int organizationId) {
        return null;
    }

    @Override
    public Record currentRecord() {
        return null;
    }

    @Override
    public int numUsers() {
        return 0;
    }

    @Override
    public int numOrganizations() {
        return 0;
    }

    @Override
    public int numPendingRecords() {
        return 0;
    }

    @Override
    public int numRecords() {
        return 0;
    }

    @Override
    public int numRejectedRecords() {
        return 0;
    }

    @Override
    public int numActivities() {
        return 0;
    }

    @Override
    public int numActivitiesByOrganization(int organizationId) {
        return 0;
    }

    @Override
    public Activity getActivity(String actId) {
        return null;
    }

    @Override
    public int availabilityOfTickets(String actId) {
        return 0;
    }
}
