package uoc.ded.practica;


import uoc.ded.practica.model.Ticket;
import uoc.ded.practica.util.DateUtils;


public class FactorySafetyActivities4Covid19 {


    public static SafetyActivities4Covid19 getSafetyActivities4Covid19() throws Exception {
        SafetyActivities4Covid19 safetyActivities4Covid19;
        safetyActivities4Covid19 = new SafetyActivities4Covid19Impl();

        ////
        //// USERS
        ////
        safetyActivities4Covid19.addUser("idUser1", "Maria", "Simo", DateUtils.createLocalDate("07-01-1934"), true);
        safetyActivities4Covid19.addUser("idUser2", "Àlex", "Lluna", DateUtils.createLocalDate("08-10-1985"), true);
        safetyActivities4Covid19.addUser("idUser3", "Pepet", "Ferra", DateUtils.createLocalDate("30-03-1931"), false);
        safetyActivities4Covid19.addUser("idUser4", "Joana", "Quilez", DateUtils.createLocalDate("07-01-1924"), false);
        safetyActivities4Covid19.addUser("idUser5", "Armand", "Morata", DateUtils.createLocalDate("07-01-1942"),true);
        safetyActivities4Covid19.addUser("idUser6", "Jesus", "Sallent", DateUtils.createLocalDate("07-01-1932"), true);
        safetyActivities4Covid19.addUser("idUser7", "Anna", "Casals", DateUtils.createLocalDate("09-07-1988"), false);
        safetyActivities4Covid19.addUser("idUser8", "Mariajo", "Padró", DateUtils.createLocalDate("02-06-1992"), false);
        safetyActivities4Covid19.addUser("idUser9", "Agustí", "Padró", DateUtils.createLocalDate("15-01-2005"), true);
        safetyActivities4Covid19.addUser("idUser10", "Pepet", "Marieta", DateUtils.createLocalDate("23-04-2008"), false);

        ////
        //// ORGANIZATIONS
        ////
        safetyActivities4Covid19.addOrganization(3, "ORG_AAA", "description AAA" );
        safetyActivities4Covid19.addOrganization(8, "ORG_ABA", "description ABA" );
        safetyActivities4Covid19.addOrganization(10, "ORG_BAAB", "description BAAB" );
        safetyActivities4Covid19.addOrganization(1, "ORG_XXXX", "description XXXXX" );
        safetyActivities4Covid19.addOrganization(16, "ORG_CDD", "description CDD" );

        ////
        //// RECORDS && ACTIVITIES
        ////
        safetyActivities4Covid19.addRecord("R-001", "ACT-1101", "description ACT-1101" ,
                DateUtils.createDate("22-11-2021 23:00:00"), SafetyActivities4Covid19.Mode.FACE2FACE, 50, 10);

        safetyActivities4Covid19.addRecord("R-002", "ACT-1102", "description ACT-1102" ,
                DateUtils.createDate("22-11-2021 23:00:00"), SafetyActivities4Covid19.Mode.FACE2FACE, 7, 10);

        safetyActivities4Covid19.addRecord("R-003", "ACT-1103", "description ACT-1103" ,
                DateUtils.createDate("22-11-2021 23:00:00"), SafetyActivities4Covid19.Mode.FACE2FACE, 50, 10);

        safetyActivities4Covid19.addRecord("R-004", "ACT-1104", "description ACT-1104" ,
                DateUtils.createDate("22-11-2021 23:00:00"), SafetyActivities4Covid19.Mode.FACE2FACE, 50, 10);

        safetyActivities4Covid19.addRecord("R-005", "ACT-1105", "description ACT-1105" ,
                DateUtils.createDate("23-11-2021 23:00:00"), SafetyActivities4Covid19.Mode.FACE2FACE, 10, 1);

        safetyActivities4Covid19.updateRecord(SafetyActivities4Covid19.Status.ENABLED,
                DateUtils.createDate("12-10-2021 11:00:00"), "OK: XXX 0");

        safetyActivities4Covid19.updateRecord(SafetyActivities4Covid19.Status.ENABLED,
                DateUtils.createDate("12-10-2021 12:00:00"), "OK: XXX 1");

        safetyActivities4Covid19.updateRecord(SafetyActivities4Covid19.Status.DISABLED,
                DateUtils.createDate("12-10-2021 14:00:00"), "KO: XXX");

        safetyActivities4Covid19.updateRecord(SafetyActivities4Covid19.Status.ENABLED,
                DateUtils.createDate("12-10-2021 16:00:00"), "OK: XXX 2");



        safetyActivities4Covid19.createTicket("idUser1", "ACT-1105");
        safetyActivities4Covid19.createTicket("idUser2", "ACT-1105");
        safetyActivities4Covid19.createTicket("idUser3", "ACT-1105");

        Ticket t1 = safetyActivities4Covid19.assignSeat("ACT-1105");
        Ticket t2 = safetyActivities4Covid19.assignSeat("ACT-1105");
        Ticket t3 = safetyActivities4Covid19.assignSeat("ACT-1105");

        safetyActivities4Covid19.createTicket("idUser4", "ACT-1102");
        safetyActivities4Covid19.createTicket("idUser5", "ACT-1102");
        safetyActivities4Covid19.createTicket("idUser6", "ACT-1102");
        safetyActivities4Covid19.createTicket("idUser7", "ACT-1102");

        Ticket t4 = safetyActivities4Covid19.assignSeat("ACT-1102");
        Ticket t5 = safetyActivities4Covid19.assignSeat("ACT-1102");
        Ticket t6 = safetyActivities4Covid19.assignSeat("ACT-1102");
        Ticket t7 = safetyActivities4Covid19.assignSeat("ACT-1102");


        return safetyActivities4Covid19;
    }



}