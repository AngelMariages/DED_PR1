package uoc.ded.practica;

import java.time.LocalDate;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uoc.ded.practica.exceptions.DEDException;
import uoc.ded.practica.exceptions.LimitExceededException;
import uoc.ded.practica.exceptions.NoActivitiesException;
import uoc.ded.practica.exceptions.OrganizationNotFoundException;
import uoc.ded.practica.model.Activity;
import uoc.ded.practica.model.Rating;
import uoc.ded.practica.model.Record;
import uoc.ded.practica.model.Ticket;
import uoc.ded.practica.util.DateUtils;
import uoc.ded.practica.util.Log;
import uoc.ei.tads.Iterador;

public class SafetyActivities4Covid19PR1Test {

    private SafetyActivities4Covid19 safetyActivities4Covid19;

    @Before
    public void setUp() throws Exception {
        this.safetyActivities4Covid19 = FactorySafetyActivities4Covid19.getSafetyActivities4Covid19();
    }

    @After
    public void tearDown() {
        this.safetyActivities4Covid19 = null;
    }


    /**
     * *feature*: (sobre la qual fem @Test): addUser del TAD SafetyActivities4Covid19
     * *given*: Hi ha 10 usuaris en el sistema
     * *scenario*:
     * - S'afegeix un nou usuari en el sistema
     * - S'afegeix un segon usuari en el sistema
     * - Es modifiquen les dades del segon usuari inserir
     */
    @Test
    public void testAddUser() {

        // GIVEN:
        Assert.assertEquals(10, this.safetyActivities4Covid19.numUsers());
        //

        this.safetyActivities4Covid19.addUser("idUser1000", "Robert", "Lopez", createLocalDate("02-01-1942"), false);
        Assert.assertEquals("Robert", this.safetyActivities4Covid19.getUser("idUser1000").getName());
        Assert.assertEquals(11, this.safetyActivities4Covid19.numUsers());

        this.safetyActivities4Covid19.addUser("idUser9999", "XXXXX", "YYYYY", createLocalDate("12-11-1962"), true);
        Assert.assertEquals("XXXXX", this.safetyActivities4Covid19.getUser("idUser9999").getName());
        Assert.assertEquals(12, this.safetyActivities4Covid19.numUsers());

        this.safetyActivities4Covid19.addUser("idUser9999", "Lluis", "Casals", createLocalDate("22-07-1938"), true);
        Assert.assertEquals("Lluis", this.safetyActivities4Covid19.getUser("idUser9999").getName());
        Assert.assertEquals("Casals", this.safetyActivities4Covid19.getUser("idUser9999").getSurname());
        Assert.assertEquals(12, this.safetyActivities4Covid19.numUsers());
    }


    /**
     * *feature*: (sobre la qual fem @Test): addOrganization del TAD SafetyActivities4Covid19
     * *given*: Hi ha 10 usuaris en el sistema i 5 organitzacions
     * *scenario*:
     * - S'afegeix una nova organització en el sistema
     * - S'afegeix una segona organització en el sistema
     * - Es modifiquen les dades de la segona organització
     */
    @Test
    public void testAddOrganization() {

        // GIVEN:
        Assert.assertEquals(10, this.safetyActivities4Covid19.numUsers());
        Assert.assertEquals(5, this.safetyActivities4Covid19.numOrganizations());
        //

        this.safetyActivities4Covid19.addOrganization(15, "ORG_VDA", "description VDA" );
        Assert.assertEquals("ORG_VDA", this.safetyActivities4Covid19.getOrganization(15).getName());
        Assert.assertEquals("description VDA", this.safetyActivities4Covid19.getOrganization(15).getDescription());
        Assert.assertEquals(6, this.safetyActivities4Covid19.numOrganizations());

        this.safetyActivities4Covid19.addOrganization(17, "ORG_XXX", "description XXX" );
        Assert.assertEquals("ORG_XXX", this.safetyActivities4Covid19.getOrganization(17).getName());
        Assert.assertEquals("description XXX", this.safetyActivities4Covid19.getOrganization(17).getDescription());
        Assert.assertEquals(7, this.safetyActivities4Covid19.numOrganizations());

        this.safetyActivities4Covid19.addOrganization(17, "ORG_AWS", "description AW" );
        Assert.assertEquals("ORG_AWS", this.safetyActivities4Covid19.getOrganization(17).getName());
        Assert.assertEquals("description AW", this.safetyActivities4Covid19.getOrganization(17).getDescription());
        Assert.assertEquals(7, this.safetyActivities4Covid19.numOrganizations());
    }


    /**
     * *feature*: (sobre la qual fem @Test): addRecord del TAD SafetyActivities4Covid19
     * *given*: Hi ha:
     *   - 10 usuaris en el sistema
     *   - 5 organitzacions
     *   - 5 Expedients en el sistema
     *   - 1 Expedient pendent de validar
     *   - 1 Expedient rebutjat
     *   - 3 Activitats
     *
     * *scenario*:
     * - S'afegeix un nou expedient en el sistema
     * - S'afegeix un segon expedient en el sistema
     */
    @Test
    public void testAddRecord() throws DEDException {
        // GIVEN:
        Assert.assertEquals(10, this.safetyActivities4Covid19.numUsers());
        Assert.assertEquals(5, this.safetyActivities4Covid19.numOrganizations());
        Assert.assertEquals(5, this.safetyActivities4Covid19.numRecords() );
        Assert.assertEquals(1, this.safetyActivities4Covid19.numPendingRecords() );
        Assert.assertEquals(1, this.safetyActivities4Covid19.numRejectedRecords() );
        Assert.assertEquals(3, this.safetyActivities4Covid19.numActivities());
        //
        Record record=null;

        this.safetyActivities4Covid19.addRecord("XXX-001", "ACT-010", "description ACT-010" ,
                createDate("22-11-2021 23:00:00"), SafetyActivities4Covid19.Mode.FACE2FACE, 100, 10);

        Assert.assertEquals(2, this.safetyActivities4Covid19.numPendingRecords() );

        record = this.safetyActivities4Covid19.currentRecord();
        Assert.assertEquals("XXX-001", record.getRecordId() );
        Assert.assertEquals(SafetyActivities4Covid19.Status.PENDING, record.getStatus() );


        this.safetyActivities4Covid19.addRecord("XXX-002", "ACT-011", "description ACT-011",
                createDate("25-11-2021 23:00:00"), SafetyActivities4Covid19.Mode.FACE2FACE, 100, 10);

        Assert.assertEquals(3, this.safetyActivities4Covid19.numPendingRecords() );

        record = this.safetyActivities4Covid19.currentRecord();
        Assert.assertEquals("XXX-002", record.getRecordId() );
        Assert.assertEquals(SafetyActivities4Covid19.Status.PENDING, record.getStatus() );

    }


    /**
     * *feature*: (sobre la qual fem @Test): addRecord del TAD SafetyActivities4Covid19
     * *given*: Hi ha:
     *   - 10 usuaris en el sistema
     *   - 5 organitzacions
     *   - 5 Expedients en el sistema
     *   - 1 Expedient pendent de validar
     *   - 1 Expedient rebutjat
     *   - 3 Activitats
     * *scenario*:
     * - S'afegeix un nou expedient en el sistema sobre una organització inexistent
     */
    @Test(expected = OrganizationNotFoundException.class)
    public void testAddRecordAndOrganizationNotFound() throws DEDException {
        // GIVEN:
        Assert.assertEquals(10, this.safetyActivities4Covid19.numUsers());
        Assert.assertEquals(5, this.safetyActivities4Covid19.numOrganizations());
        Assert.assertEquals(5, this.safetyActivities4Covid19.numRecords() );
        Assert.assertEquals(1, this.safetyActivities4Covid19.numPendingRecords() );
        Assert.assertEquals(1, this.safetyActivities4Covid19.numRejectedRecords() );
        Assert.assertEquals(3, this.safetyActivities4Covid19.numActivities());
        //

        this.safetyActivities4Covid19.addRecord("XXX-002", "ACT-011", "description ACT-011",
                createDate("25-11-2021 23:00:00"), SafetyActivities4Covid19.Mode.FACE2FACE, 100, 2);

    }


    /**
     * *feature*: (sobre la qual fem @Test): addRecord & update del TAD SafetyActivities4Covid19
     * *given*: Hi ha:
     *   - 10 usuaris en el sistema
     *   - 5 organitzacions
     *   - 5 Expedients en el sistema
     *   - 1 Expedient pendent de validar
     *   - 1 Expedient rebutjat
     *   - 3 Activitats
     *
     * *scenario*:
     *  - S'afegeixen "records" del test testAddRecord
     */
    @Test
    public void testAddRecordAndUpdate() throws DEDException {

        // GIVEN:
        Assert.assertEquals(10, this.safetyActivities4Covid19.numUsers());
        Assert.assertEquals(5, this.safetyActivities4Covid19.numOrganizations());
        Assert.assertEquals(5, this.safetyActivities4Covid19.numRecords() );
        Assert.assertEquals(1, this.safetyActivities4Covid19.numPendingRecords() );
        Assert.assertEquals(1, this.safetyActivities4Covid19.numRejectedRecords() );
        Assert.assertEquals(3, this.safetyActivities4Covid19.numActivities());
        //

        this.testAddRecord();


        Assert.assertEquals(10, this.safetyActivities4Covid19.numUsers());
        Assert.assertEquals(5, this.safetyActivities4Covid19.numOrganizations());
        Assert.assertEquals(7, this.safetyActivities4Covid19.numRecords() );
        Assert.assertEquals(3, this.safetyActivities4Covid19.numPendingRecords() );
        Assert.assertEquals(1, this.safetyActivities4Covid19.numRejectedRecords() );
        Assert.assertEquals(3, this.safetyActivities4Covid19.numActivities());
        //
        Record record=null;

        Assert.assertEquals(0.17, this.safetyActivities4Covid19.getInfoRejectedRecords(),0.03);

        this.safetyActivities4Covid19.updateRecord(SafetyActivities4Covid19.Status.DISABLED,
                createDate("25-11-2021 23:00:00"), "KO X1");

        Assert.assertEquals(2, this.safetyActivities4Covid19.numRejectedRecords() );
        Assert.assertEquals(7, this.safetyActivities4Covid19.numRecords() );


        Assert.assertEquals(0.28, this.safetyActivities4Covid19.getInfoRejectedRecords(),0.03);

    }


    /**
     * *feature*: (sobre la qual fem @Test): getActivitiesByOrganization del TAD SafetyActivities4Covid19
     * *given*: Hi ha:
     *   - 10 usuaris en el sistema
     *   - 5 organitzacions
     *   - 5 Expedients en el sistema
     *   - 1 Expedient pendent de validar
     *   - 1 Expedient rebutjat
     *   - 3 Activitats
     *
     * *scenario*:
     *  - Es consulten les activitats d'una organització
     */
    @Test
    public void testGetActivitiesByOrganization() throws DEDException {
        // GIVEN:
        Assert.assertEquals(10, this.safetyActivities4Covid19.numUsers());
        Assert.assertEquals(5, this.safetyActivities4Covid19.numOrganizations());
        Assert.assertEquals(5, this.safetyActivities4Covid19.numRecords() );
        Assert.assertEquals(1, this.safetyActivities4Covid19.numPendingRecords() );
        Assert.assertEquals(1, this.safetyActivities4Covid19.numRejectedRecords() );
        Assert.assertEquals(3, this.safetyActivities4Covid19.numActivities());
        //


        Assert.assertEquals(2, this.safetyActivities4Covid19.numActivitiesByOrganization(10));

        Iterador<Activity> it = this.safetyActivities4Covid19.getActivitiesByOrganization(10);
        Activity activity1 = it.seguent();
        Assert.assertEquals("ACT-1104", activity1.getActId());

        Activity activity2 = it.seguent();
        Assert.assertEquals("ACT-1102", activity2.getActId());
    }

    /**
     * *feature*: (sobre la qual fem @Test): getActivitiesByOrganization del TAD SafetyActivities4Covid19
     * *given*: Hi ha:
     *   - 10 usuaris en el sistema
     *   - 5 organitzacions
     *   - 5 Expedients en el sistema
     *   - 1 Expedient pendent de validar
     *   - 1 Expedient rebutjat
     *   - 3 Activitats
     *
     * *scenario*:
     *  - Es consulten les activitats d'una organització que NO té activitats (3)
     */
    @Test(expected = NoActivitiesException.class)
    public void testGetActivitiesByOrganizationAndNOActiviesException() throws DEDException {
        // GIVEN:
        Assert.assertEquals(10, this.safetyActivities4Covid19.numUsers());
        Assert.assertEquals(5, this.safetyActivities4Covid19.numOrganizations());
        Assert.assertEquals(5, this.safetyActivities4Covid19.numRecords() );
        Assert.assertEquals(1, this.safetyActivities4Covid19.numPendingRecords() );
        Assert.assertEquals(1, this.safetyActivities4Covid19.numRejectedRecords() );
        Assert.assertEquals(3, this.safetyActivities4Covid19.numActivities());
        //
        Iterador<Activity> it = this.safetyActivities4Covid19.getActivitiesByOrganization(3);
    }


    /**
     * *feature*: (sobre la qual fem @Test): getAllActivities del TAD SafetyActivities4Covid19
     * *given*: Hi ha:
     *   - 10 usuaris en el sistema
     *   - 5 organitzacions
     *   - 5 Expedients en el sistema
     *   - 1 Expedient pendent de validar
     *   - 1 Expedient rebutjat
     *   - 3 Activitats
     *
     * *scenario*:
     *  - Es consulten totes les activitats
     */
    @Test
    public void testGetAllActivities() throws DEDException {
        // GIVEN:
        Assert.assertEquals(10, this.safetyActivities4Covid19.numUsers());
        Assert.assertEquals(5, this.safetyActivities4Covid19.numOrganizations());
        Assert.assertEquals(5, this.safetyActivities4Covid19.numRecords() );
        Assert.assertEquals(1, this.safetyActivities4Covid19.numPendingRecords() );
        Assert.assertEquals(1, this.safetyActivities4Covid19.numRejectedRecords() );
        Assert.assertEquals(3, this.safetyActivities4Covid19.numActivities());
        //
        Iterador<Activity> it = this.safetyActivities4Covid19.getAllActivities();

        Activity activity1 = it.seguent();
        Assert.assertEquals("ACT-1102", activity1.getActId());

        Activity activity2 = it.seguent();
        Assert.assertEquals("ACT-1104", activity2.getActId());

        Activity activity3 = it.seguent();
        Assert.assertEquals("ACT-1105", activity3.getActId());

    }

    /**
     * *feature*: (sobre la qual fem @Test): getAllActivitiesByUser del TAD SafetyActivities4Covid19
     * *given*: Hi ha:
     *   - 10 usuaris en el sistema
     *   - 5 organitzacions
     *   - 5 Expedients en el sistema
     *   - 1 Expedient pendent de validar
     *   - 1 Expedient rebutjat
     *   - 3 Activitats
     *
     * *scenario*:
     *  - Es consulten les activitats d'un usuari i no existeix cap
     */
    @Test(expected = NoActivitiesException.class)
    public void testGetAllActivitiesByUserAndNoActivitiesException() throws DEDException {
        // GIVEN:
        Assert.assertEquals(10, this.safetyActivities4Covid19.numUsers());
        Assert.assertEquals(5, this.safetyActivities4Covid19.numOrganizations());
        Assert.assertEquals(5, this.safetyActivities4Covid19.numRecords() );
        Assert.assertEquals(1, this.safetyActivities4Covid19.numPendingRecords() );
        Assert.assertEquals(1, this.safetyActivities4Covid19.numRejectedRecords() );
        Assert.assertEquals(3, this.safetyActivities4Covid19.numActivities());
        //
        Iterador<Activity> it = this.safetyActivities4Covid19.getActivitiesByUser("idUser9");

    }

    /**
     * *feature*: (sobre la qual fem @Test): addRating & getRatings del TAD SafetyActivities4Covid19
     * *given*: Hi ha:
     *   - 10 usuaris en el sistema
     *   - 5 organitzacions
     *   - 5 Expedients en el sistema
     *   - 1 Expedient pendent de validar
     *   - 1 Expedient rebutjat
     *   - 3 Activitats
     *
     * *scenario*:
     *  - S'afegeixen valoracions sobre un parell d'activitats
     *  culturals que van alternant ser la millor activitat cultural
     */
    @Test
    public void testAddRating() throws DEDException {
        // GIVEN:
//        Assert.assertEquals(10, this.safetyActivities4Covid19.numUsers());
//        Assert.assertEquals(5, this.safetyActivities4Covid19.numOrganizations());
//        Assert.assertEquals(5, this.safetyActivities4Covid19.numRecords() );
//        Assert.assertEquals(1, this.safetyActivities4Covid19.numPendingRecords() );
//        Assert.assertEquals(1, this.safetyActivities4Covid19.numRejectedRecords() );
//        Assert.assertEquals(3, this.safetyActivities4Covid19.numActivities());
//        //
//
//        Activity activity1105 = this.safetyActivities4Covid19.getActivity("ACT-1105");
//
//        Assert.assertEquals(0, activity1105.rating(),0);
//
//        this.safetyActivities4Covid19.addRating("ACT-1105",
//                SafetyActivities4Covid19.Rating.FIVE, "Very good", "idUser1");
//
//        Assert.assertEquals(5, activity1105.rating(),0);
//
//        this.safetyActivities4Covid19.addRating("ACT-1105",
//                SafetyActivities4Covid19.Rating.FOUR, "Good", "idUser2");
//
//        Assert.assertEquals(4.5, activity1105.rating(),0);
//
//        this.safetyActivities4Covid19.addRating("ACT-1105",
//                SafetyActivities4Covid19.Rating.TWO, "CHIPI - CHAPI", "idUser3");
//        Assert.assertEquals(3.6, activity1105.rating(),0.09);
//
//        Activity bestActivity = this.safetyActivities4Covid19.bestActivity();
//        Assert.assertEquals("ACT-1105", bestActivity.getActId());
//        //
//        //
//
//        Activity activity1102 = this.safetyActivities4Covid19.getActivity("ACT-1102");
//
//        this.safetyActivities4Covid19.addRating("ACT-1102",
//                SafetyActivities4Covid19.Rating.FOUR, "Good!!!", "idUser4");
//        Assert.assertEquals(4, activity1102.rating(),0);
//        Assert.assertEquals(3.6, activity1105.rating(),0.09);
//
//        bestActivity = this.safetyActivities4Covid19.bestActivity();
//        Assert.assertEquals("ACT-1102", bestActivity.getActId());
//
//
//
//        this.safetyActivities4Covid19.addRating("ACT-1102",
//                SafetyActivities4Covid19.Rating.ONE, "Bad!!!", "idUser5");
//        Assert.assertEquals(2.5, activity1102.rating(),0.09);
//        Assert.assertEquals(3.6, activity1105.rating(),0.09);
//
//        bestActivity = this.safetyActivities4Covid19.bestActivity();
//        Assert.assertEquals("ACT-1105", bestActivity.getActId());
//
//        this.safetyActivities4Covid19.addRating("ACT-1102",
//                SafetyActivities4Covid19.Rating.FOUR, "Good!!!", "idUser6");
//        Assert.assertEquals(3, activity1102.rating(),0.09);
//        Assert.assertEquals(3.6, activity1105.rating(),0.09);
//
//        bestActivity = this.safetyActivities4Covid19.bestActivity();
//        Assert.assertEquals("ACT-1105", bestActivity.getActId());
//
//        this.safetyActivities4Covid19.addRating("ACT-1102",
//                SafetyActivities4Covid19.Rating.FIVE, "Very Good!!!", "idUser7");
//        Assert.assertEquals(3.5, activity1102.rating(),0);
//        Assert.assertEquals(3.6, activity1105.rating(),0.09);
//
//        bestActivity = this.safetyActivities4Covid19.bestActivity();
//        Assert.assertEquals("ACT-1105", bestActivity.getActId());
//
//        this.safetyActivities4Covid19.addRating("ACT-1102",
//                SafetyActivities4Covid19.Rating.FIVE, "Very Good!!!", "idUser7");
//        Assert.assertEquals(3.8, activity1102.rating(),0);
//        Assert.assertEquals(3.6, activity1105.rating(),0.09);
//
//        bestActivity = this.safetyActivities4Covid19.bestActivity();
//        Assert.assertEquals("ACT-1102", bestActivity.getActId());
//
//
//        Iterador<Rating> it = this.safetyActivities4Covid19.getRatings("ACT-1102");
//        Rating rating = it.seguent();
//        Assert.assertEquals(SafetyActivities4Covid19.Rating.FOUR, rating.getRating());
//        Assert.assertEquals("idUser4", rating.getUser().getId());



    }




    /**
     * *feature*: (sobre la qual fem @Test): createTicket & assignSeat del TAD SafetyActivities4Covid19
     * *given*: Hi ha:
     *   - 10 usuaris en el sistema
     *   - 5 organitzacions
     *   - 5 Expedients en el sistema
     *   - 1 Expedient pendent de validar
     *   - 1 Expedient rebutjat
     *   - 3 Activitats
     *   - 4 entrades comprades sobre una activitat
     *   - 4 seients assignats
     *
     * *scenario*:
     *  - Es compra 1 entrada i s'assigna el seu seient
     */
    @Test
    public void testCreateTicketAndAssign() throws DEDException {

        // GIVEN:
        Assert.assertEquals(10, this.safetyActivities4Covid19.numUsers());
        Assert.assertEquals(5, this.safetyActivities4Covid19.numOrganizations());
        Assert.assertEquals(5, this.safetyActivities4Covid19.numRecords() );
        Assert.assertEquals(1, this.safetyActivities4Covid19.numPendingRecords() );
        Assert.assertEquals(1, this.safetyActivities4Covid19.numRejectedRecords() );
        Assert.assertEquals(3, this.safetyActivities4Covid19.numActivities());
        Assert.assertEquals(3, this.safetyActivities4Covid19.availabilityOfTickets("ACT-1102"));
        //

        this.safetyActivities4Covid19.createTicket("idUser8", "ACT-1102");
        Assert.assertEquals(2, this.safetyActivities4Covid19.availabilityOfTickets("ACT-1102"));

        this.safetyActivities4Covid19.createTicket("idUser9", "ACT-1102");
        Assert.assertEquals(1, this.safetyActivities4Covid19.availabilityOfTickets("ACT-1102"));

        this.safetyActivities4Covid19.createTicket("idUser10", "ACT-1102");
        Assert.assertEquals(0, this.safetyActivities4Covid19.availabilityOfTickets("ACT-1102"));

        Ticket Ticket5 = this.safetyActivities4Covid19.assignSeat("ACT-1102");

        Assert.assertEquals(5, Ticket5.getSeat());
        Assert.assertEquals("idUser8", Ticket5.getUser().getId());


        Ticket Ticket6 = this.safetyActivities4Covid19.assignSeat("ACT-1102");
        Assert.assertEquals(6, Ticket6.getSeat());
        Assert.assertEquals("idUser9", Ticket6.getUser().getId());

        Ticket Ticket7 = this.safetyActivities4Covid19.assignSeat("ACT-1102");
        Assert.assertEquals(7, Ticket7.getSeat());
        Assert.assertEquals("idUser10", Ticket7.getUser().getId());


    }


    /**
     * *feature*: (sobre la qual fem @Test): createTicket del TAD SafetyActivities4Covid19
     * *given*: Hi ha:
     *   - 10 usuaris en el sistema
     *   - 5 organitzacions
     *   - 5 Expedients en el sistema
     *   - 1 Expedient pendent de validar
     *   - 1 Expedient rebutjat
     *   - 3 Activitats
     *   - 4 entrades comprades sobre una activitat
     *   - 4 seients assignats
     *
     * *scenario*:
     *  - Es compren més de 50 entrades i s'excedeix el nombre màxim d'entrades
     */
    @Test(expected = LimitExceededException.class)
    public void testCreateTicketAndLimitExceededException() throws DEDException {

        // GIVEN:
        Assert.assertEquals(10, this.safetyActivities4Covid19.numUsers());
        Assert.assertEquals(5, this.safetyActivities4Covid19.numOrganizations());
        Assert.assertEquals(5, this.safetyActivities4Covid19.numRecords() );
        Assert.assertEquals(1, this.safetyActivities4Covid19.numPendingRecords() );
        Assert.assertEquals(1, this.safetyActivities4Covid19.numRejectedRecords() );
        Assert.assertEquals(3, this.safetyActivities4Covid19.numActivities());
        Assert.assertEquals(3, this.safetyActivities4Covid19.availabilityOfTickets("ACT-1102"));
        //

        this.safetyActivities4Covid19.createTicket("idUser7", "ACT-1102");
        Assert.assertEquals(2, this.safetyActivities4Covid19.availabilityOfTickets("ACT-1102"));

        this.safetyActivities4Covid19.createTicket("idUser8", "ACT-1102");
        Assert.assertEquals(1,this.safetyActivities4Covid19.availabilityOfTickets("ACT-1102"));

        this.safetyActivities4Covid19.createTicket("idUser9", "ACT-1102");
        Assert.assertEquals(0, this.safetyActivities4Covid19.availabilityOfTickets("ACT-1102"));

        this.safetyActivities4Covid19.createTicket("idUser10", "ACT-1102");

    }

    private Date createDate(String date) {
        return DateUtils.createDate(date);
    }

    private LocalDate createLocalDate(String date) {
        return DateUtils.createLocalDate(date);
    }
}
