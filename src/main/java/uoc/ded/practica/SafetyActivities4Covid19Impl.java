package uoc.ded.practica;

import java.io.NotActiveException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

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
import uoc.ded.practica.util.VectorOrdenat;
import uoc.ei.tads.Iterador;
import uoc.ei.tads.PilaVectorImpl;

public class SafetyActivities4Covid19Impl implements SafetyActivities4Covid19 {
    private static final String TAG = "SafetyActivities4Covid19Impl";

    private final Organization[] organizations = new Organization[O];
    private final User[] users = new User[U];

    private final PilaVectorImpl<Record> recordStack = new PilaVectorImpl<>();
    private int rejectedRecords = 0;
    private int createdRecords = 0;

    private final VectorOrdenat<String, Activity> activities = new VectorOrdenat<>(A);

    public SafetyActivities4Covid19Impl() {
        Log.d(TAG, "constructor");
    }

    @Override
    public void addUser(String userId, String name, String surname, LocalDate birthday, boolean covidCertificate) {
        // Get the user if it exists
        User user = getUser(userId);
        // Get current number of users
        int numUsers = numUsers();

        if (user == null && numUsers < U) {
            // If it doesn't exist AND we didn't reach the max users, add it
            users[numUsers] = new User(userId, name, surname, birthday, covidCertificate);
        } else if (user != null) {
            // If it exists, update it's properties
            user.update(name, surname, birthday, covidCertificate);
        }
    }

    @Override
    public void addOrganization(int organizationId, String name, String description) {
        // Get the organization if exists
        Organization organization = getOrganization(organizationId);

        if (organization == null) {
            // If it doesn't exist, add it
            organizations[organizationId] = new Organization(name, description);
        } else {
            // If it exists, update it's properties
            organization.update(name, description);
        }
    }

    @Override
    public void addRecord(String recordId, String actId, String description, Date date, Mode mode, int num, int organizationId) throws OrganizationNotFoundException {
        // Find the organization or else throw an exception
        Organization organization = Optional.ofNullable(getOrganization(organizationId))
                .orElseThrow(() -> new OrganizationNotFoundException(organizationId));


        // Add the record to the stack
        recordStack.empilar(new Record(recordId, actId, description, date, mode, num, organizationId));

        // Add the count of created records
        this.createdRecords++;
    }

    @Override
    public void updateRecord(Status status, Date date, String description) throws NoRecordsException, LimitExceededException {
        if (recordStack.estaBuit()) {
            throw new NoRecordsException();
        }

        Record record = recordStack.desempilar();

        // If rejected, we just count it as a rejected record.
        if (status == Status.DISABLED) {
            this.rejectedRecords++;

            // Prematurely return because there's nothing more to do.
            return;
        }

        // If it's enabled, we will create the activity
        if (status == Status.ENABLED) {
            // If full we throw an exception
            if (activities.estaPle()) {
                throw new LimitExceededException("Can't add more than " + A + " elements to the dictionary.");
            }

            Activity activity = record.createActivity();

            activities.afegir(activity.getActId(), activity);

            Organization organization = getOrganization(record.getOrganizationId());

            organization.addActivity(activity);
        }
    }

    @Override
    public void createTicket(String userId, String actId) throws UserNotFoundException, ActivityNotFoundException, LimitExceededException {
        User user = Optional.ofNullable(getUser(userId))
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found."));

        if (!activities.hiEs(actId)) {
            throw new ActivityNotFoundException("Activity with id " + actId + " not found.");
        }

        Activity activity = getActivity(actId);

        if (activity.availableTickets() <= 0) {
            throw new LimitExceededException("No more tickets available");
        }

        activity.enqueueATicket(new Ticket(user));

        user.attendActivity(activity);
    }

    @Override
    public Ticket assignSeat(String actId) throws ActivityNotFoundException {
        if (!activities.hiEs(actId)) {
            throw new ActivityNotFoundException("Activity with id " + actId + " not found.");
        }

        Activity activity = getActivity(actId);

        return activity.pickATicket();
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
        return (numRejectedRecords() / (double) numRecords());
    }

    @Override
    public Iterador<Activity> getAllActivities() throws NoActivitiesException {
        if (activities.estaBuit()) {
            throw new NoActivitiesException("No activities found");
        }

        return activities.elements();
    }

    @Override
    public Iterador<Activity> getActivitiesByOrganization(int organizationId) throws NoActivitiesException {
        Organization organization = getOrganization(organizationId);

        if (organization == null) {
            throw new NoActivitiesException("No organization found with id " + organizationId);
        }

        if (organization.getNumActivities() == 0) {
            throw new NoActivitiesException("No activities found for org with id " + organizationId);
        }

        return organization.getOrgActivities();
    }

    @Override
    public Iterador<Activity> getActivitiesByUser(String userId) throws NoActivitiesException {
        User user = Optional.ofNullable(getUser(userId))
                .orElseThrow(() -> new NoActivitiesException("No user found with id " + userId));

        return user.getAttendedActivities();
    }

    @Override
    public User getUser(String userId) {
        // Iterate over all NonNull users
        for (int i = 0; i < numUsers(); i++) {
            User user = users[i];

            // Get the user matching the userId
            if (user.getId().equals(userId)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public Organization getOrganization(int organizationId) {
        return organizations[organizationId];
    }

    @Override
    public Record currentRecord() {
        return recordStack.cim();
    }

    @Override
    public int numUsers() {
        int count = 0;

        // Iterate over all the users
        // and count them.
        // Stop when there's a Null, because we know that they are ordered and all the rest will be Null.
        for (User user : users) {
            if (user == null) {
                break;
            }
            count++;
        }

        return count;
    }

    @Override
    public int numOrganizations() {
        return Math.toIntExact(Arrays.stream(organizations).filter(Objects::nonNull).count());
    }

    @Override
    public int numPendingRecords() {
        int numPendingRecords = 0;

        // Get the stack iterator
        Iterador<Record> recordIterador = recordStack.elements();

        // Iterate until there's no next element
        while (recordIterador.hiHaSeguent()) {
            Record record = recordIterador.seguent();

            Log.d(TAG, "record", record);

            // If status is PENDING, add it to the count
            if (record.getStatus() == Status.PENDING) {
                numPendingRecords++;
            }
        }

        return numPendingRecords;
    }

    @Override
    public int numRecords() {
        return createdRecords;
    }

    @Override
    public int numRejectedRecords() {
        return rejectedRecords;
    }

    @Override
    public int numActivities() {
        return activities.nombreElems();
    }

    @Override
    public int numActivitiesByOrganization(int organizationId) {
        Organization organization = getOrganization(organizationId);

        return organization.getNumActivities();
    }

    @Override
    public Activity getActivity(String actId) {
        return activities.consultar(actId);
    }

    @Override
    public int availabilityOfTickets(String actId) {
        if (!activities.hiEs(actId)) {
            return 0;
        }

        Activity activity = getActivity(actId);

        return activity.availableTickets();
    }
}
