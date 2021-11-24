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
import uoc.ei.tads.Iterador;

/**
 * Definició del TAD de gestió de la plataforma de gestió d'activitats culturals
 *
 */
public interface SafetyActivities4Covid19 {

    /**
     * dimensió del contenidor d'usuaris
     */
    public static final int U = 45;

    /**
     * dimensió del contenidor d'organitzacions
     */
    public static final int O = 25;

    /**
     * dimensió del contenidor d'activitats
     */
    public static final int A = 256;

    enum Mode {
        ON_LINE, FACE2FACE
    }

    enum Status {
        PENDING, ENABLED, DISABLED
    }

    enum Rating {

        ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);

        private final int value;

        private Rating(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * Mètode que permet afegir un usuari en el sistema
     *
     * @pre cert.
     * @post Si el codi d'usuari és nou, els usuaris són els mateixos més un nou
     *       usuari amb les dades indicades. Si no, les dades de l'usuari s'han
     *       actualitzat amb els nous.
     *
     * @param userId           identificador de l'usuari
     * @param name             nom de l'usuari
     * @param surname          cognoms de l'usuari
     * @param birthday         data de naixement
     * @param covidCertificate certificat de covid
     */
    public void addUser(String userId, String name, String surname, LocalDate birthday, boolean covidCertificate);

    /**
     * Mètode que afegeix una organització en el sistema
     *
     * @pre cert.
     * @post Si el codi d'organització no existeix les organitzacions, són les
     *       mateixes més una nova amb les dades indicades. Si no, les dades de
     *       l'organització s'han actualitzat amb els nous.
     *
     * @param organizationId
     * @param name
     * @param description
     */
    public void addOrganization(int organizationId, String name, String description);

    /**
     * Mètode que afegeix un nou expedient en el sistema
     *
     * @pre l'activitat i el codi d'expedient no existeixen.
     * @post Els expedients són els mateixos més un de nou amb les dades indicades.
     *       El nou expedient és l'element del cim d'expedients. En cas que
     *       l'organització, identificada per organizationId, no existeixi, s'ha
     *       d'informar de l'error
     *
     * @param recordId       identificador de l'expedient
     * @param actId          identificador de l'activitat cultural
     * @param description    la descripció de l'activitat cultural
     * @param date           data en la qual és dura a terme l'activitat cultural
     * @param mode           tipus d'activitat cultural (ON-LINE o PRESENCIAL)
     * @param num            nombre màxim d'assistents
     * @param organizationId identificador de l'organització
     */
    public void addRecord(String recordId, String actId, String description, Date date, Mode mode, int num,
            int organizationId) throws OrganizationNotFoundException;

    /**
     * Mètode que actualitza l'estat de l'expedient pendent de validar
     *
     * @pre cert.
     * @post L'estat de l'element que està en el cim de la pila d'expedients es
     *       modifica, el nombre d'expedients són els mateixos menys un (el cim) i
     *       el nombre d'activitats són els mateixos més un, en cas que l'expedient
     *       sigui favorable. En cas que no hi hagi expedients en la pila, s'ha
     *       d'informar de l'error.
     *
     * @param status      estat de l'expedient
     * @param date        data en la qual es realitza la valoració de l'expedient
     * @param description descripció de la valoració de l'expedient
     * @throws NoRecordsException llança una excepció en cas que no existeixin
     *                            expedients pendents de valorar
     * @throws LimitExceededException llança una excepció en cas que s'hagi superat el límit d'activitats
     */
    public void updateRecord(Status status, Date date, String description) throws NoRecordsException, LimitExceededException;

    /**
     * Mètode que permet comprar entrades en una activitat cultural
     *
     * @pre cert.
     * @post El nombre d'entrades d'una activitat cultural són els mateixos més una
     *       unitat. En cas que l'usuari o l'activitat cultural no existeixi, s'ha
     *       d'informar d'un error. En cas que ja s'hagi superat el màxim de places,
     *       s'ha d'indicar un error.
     *
     * @param userId identificador de l'usuari
     * @param actId  identificador de l'activitat cultural
     * @throws UserNotFoundException     llança l'excepció en cas que l'usuari no
     *                                   existeixi
     * @throws ActivityNotFoundException llança l'excepció en cas que l'activitat no
     *                                   existeixi
     * @throws LimitExceededException    llança l'excepció en cas que es demanin més
     *                                   entrades que les disponibles
     */
    public void createTicket(String userId, String actId)
            throws UserNotFoundException, ActivityNotFoundException, LimitExceededException;

    /**
     * Mètode que permet l'assignació d'un seient en un acte cultural
     *
     * @pre cert.
     * @post El cap de la cua indica el seient a assignar i el nombre de tiquets
     *       pendents d'assignar d'una activitat cultural són els mateixos menys una
     *       unitat. En cas que l'activitat cultural no existeixi, s'ha d'informar
     *       d'un error.
     *
     *
     * @param actId identificador de l'activitat
     * @return retorna el tiquet actualitzat amb el seient assignat
     * @throws ActivityNotFoundException llança l'activitat en cas que no existeixi
     */
    public Ticket assignSeat(String actId) throws ActivityNotFoundException;

    /**
     * Mètode que afegeix una valoració sobre una activitat cultural per part d'un
     * usuari
     *
     * @pre cert.
     * @post Les valoracions són les mateixes més una nova amb les dades indicades.
     *       En cas que l'activitat o l'usuari no existeixi, s'ha d'informar de
     *       l'error. Si l'usuari no ha participat en l'activitat cultural, també
     *       s'ha d'informar de l'error.
     *
     * @param actId   identificador de l'activitat
     * @param rating  valoració de l'activitat
     * @param message missatge associat a l'activitat
     * @param userId  identificador de l'activitat
     * @throws ActivityNotFoundException  es llança l'excepció en cas que
     *                                    l'activitat no existeixi
     * @throws UserNotFoundException      es llança l'excepció en cas que l'usuari
     *                                    no existeixi
     * @throws UserNotInActivityException es llança l'excepció en cas que l'usuari
     *                                    no hagi participat en l'activitat cultural
     */
    public void addRating(String actId, Rating rating, String message, String userId)
            throws ActivityNotFoundException, UserNotFoundException, UserNotInActivityException;

    /**
     * Mètode que proporciona les valoracions d'una activitat cultural
     *
     * @pre cert.
     * @post Retorna un iterador per recórrer les valoracions d'una activitat. En
     *       cas que no existeixi l'activitat o no hi hagi valoracions s'indica un
     *       error
     *
     * @param actId identificador de l'activitat
     * @return retorna un iterador per recórrer les activitats culturals
     *
     * @throws ActivityNotFoundException es llança l'excepció en cas que no
     *                                   existeixi l'activitat
     * @throws NoRatingsException        es llança l'excepció en cas que no
     *                                   existeixin valoracions sobre l'activitat
     */
    public Iterador<uoc.ded.practica.model.Rating> getRatings(String actId)
            throws ActivityNotFoundException, NoRatingsException;

    /**
     * Mètode que proporciona l'activitat millor valorada
     *
     * @pre cert.
     * @post retorna l'activitat millor valorada. En cas que no existeixi cap
     *       activitat s'ha d'indicar un error
     *
     * @return retorna l'activitat millor valorada
     * @throws NoActivitiesException es llança l'excepció en cas que no existeixi
     *                               cap activitat
     */
    public Activity bestActivity() throws ActivityNotFoundException;

    /**
     * Mètode que proporciona l'usuari més participatiu
     *
     * @pre cert.
     * @post Retorna a l'usuari més actiu (major participació en activitats
     *       culturals). En cas que existeixi més d'un usuari amb el mateix nombre
     *       de participacions es proporciona aquell que va participar amb
     *       anterioritat. En cas que no existeixi cap usuari s'ha d'indicar un
     *       error mostActiveUser(): User
     * @return retorna l'usuari més participatiu
     * @throws UserNotFoundException es llança l'excepció en cas que no existeixi
     *                               cap usuari com activitat
     */
    public User mostActiveUser() throws UserNotFoundException;

    /**
     * Mètode que proporciona el % d'expedients rebutjats
     *
     * @pre cert.
     * @post retorna un enter amb el valor d'expedients que no han estat validats
     *
     * @return retorna el % d'expedients rebutjats
     */
    public double getInfoRejectedRecords();

    /**
     * Mètode que proporciona totes les activitats del sistema
     *
     * @pre cert.
     * @post Retorna un iterador per recórrer totes les activitats. En cas que no
     *       existeixin activitats s'ha d'indicar un error
     *
     * @return retorna un iterador per recórrer totes les activitats
     * @throws NoActivitiesException llança una excepció en cas que no hi hagi
     *                               activitats
     */
    public Iterador<Activity> getAllActivities() throws NoActivitiesException;

    /**
     * Mètode que proporciona un iterador amb les activitats creades per
     * l'organització
     *
     * @pre l'organització existeix.
     * @post Retorna un iterador per recórrer les activitats d'una organització. En
     *       cas que no existeixin les activitats s'indica un error
     *
     * @param organizationId identificador de l'organització
     * @return retorna un iterador amb les activitats d'una organització
     * @throws NoActivitiesException llança una excepció en cas que no existeixin
     *                               activitats creades per l'organització
     */
    public Iterador<Activity> getActivitiesByOrganization(int organizationId) throws NoActivitiesException;

    /**
     * Mètode que proporciona les activitats a les quals ha assistit un usuari
     *
     * @pre l'usuari existeix.
     * @post Retorna un iterador per recórrer les activitats d'un usuari. En cas que
     *       no existeixin activitats s'indica un error.
     *
     * @param userId identificador de l'usuari
     * @return retorna un iterador per recórrer les activitats d'un usuari
     * @throws NoActivitiesException llança una excepció en cas que no hi hagi
     *                               activitats associades a l'usuari
     */
    public Iterador<Activity> getActivitiesByUser(String userId) throws NoActivitiesException;

    ///////////////////////////////////////////////////////////////////////
    ///
    ///////////////////////////////////////////////////////////////////////

    /**
     * Mètode que proporciona l'usuari identificat
     *
     * @param userId identificador de l'usuari
     * @return retorna l'usuari o null en cas que no existeixi
     */
    public User getUser(String userId);

    /**
     * Mètode que proporciona una organització
     *
     * @param organizationId identificador de l'organització
     * @return retorna l'organització o null en cas que no existeixi.
     */
    public Organization getOrganization(int organizationId);

    /**
     * Mètode que proporciona l'expedient actual a valorar
     *
     * @return retorna l'expedient a valorar o null si no hi ha cap
     */
    public Record currentRecord();

    /**
     * Mètode que proporciona el nombre d'usuaris
     *
     * @return Retorna el nombre d'usuaris
     */
    public int numUsers();

    /**
     * Mètode que proporciona el nombre d'organitzacions
     *
     * @return Retorna el nombre d'organitzacions
     */
    public int numOrganizations();

    /**
     * Mètode que retorna el nombre d'expedients pendents de validar
     *
     * @return retorna el nombre d'expedients
     */
    public int numPendingRecords();

    /**
     * Mètode que proporciona el total d'expedients que hi ha en el sistema
     *
     * @return retorna el nombre total d'expedients
     */
    public int numRecords();

    /**
     * Mètode que proporciona el nombre d'expedients que han estat rebutjats
     *
     * @return retorna el nombre d'expedients rebutjats
     */
    public int numRejectedRecords();

    /**
     * Mètode que proporciona el nombre d'activitats
     *
     * @return retorna el nombre d'activitats
     */
    public int numActivities();

    /**
     * Mètode que proporciona el nombre d'activitats d'una organització
     *
     * @pre l'organització existeix
     *
     * @param organizationId identificador de l'activitat
     * @return retorna el nombre d'activitats de l'organització
     */
    public int numActivitiesByOrganization(int organizationId);

    /**
     * Mètode que proporciona una activitat
     *
     * @param actId identificador de l'activitat
     * @return retorna l'activitat o null si no existeix
     */
    public Activity getActivity(String actId);

    /**
     * Mètode que proporciona el nombre d'entrades disponibles sobre una activitat
     * cultural
     *
     * @param actId identificador de l'activitat cultural
     * @return retorna el nombre d'entrades disponibles d'una activitat cultural o
     *         zero en qualsevol altre cas
     */
    public int availabilityOfTickets(String actId);
}
