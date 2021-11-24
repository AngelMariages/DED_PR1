package uoc.ded.practica;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uoc.ded.practica.exceptions.DEDException;
import uoc.ded.practica.exceptions.UserNotFoundException;

public class SafetyActivities4Covid19_MostActiveUserTest {
    private SafetyActivities4Covid19 safetyActivities4Covid19;

    @Before
    public void setUp() throws Exception {
        this.safetyActivities4Covid19 = FactorySafetyActivities4Covid19.getSafetyActivities4Covid19();
    }

    @After
    public void tearDown() {
        this.safetyActivities4Covid19 = null;
    }

    @Test(expected = UserNotFoundException.class)
    public void throwsIfNoUserSet() throws DEDException {
        // No user set in this case
        SafetyActivities4Covid19Impl safetyActivities4Covid19 = new SafetyActivities4Covid19Impl();

        safetyActivities4Covid19.mostActiveUser();
    }

    @Test
    public void mostActiveUser_isSet_correctly() throws DEDException {
        safetyActivities4Covid19.createTicket("idUser1", "ACT-1102");
        Assert.assertEquals(this.safetyActivities4Covid19.getUser("idUser1"), this.safetyActivities4Covid19.mostActiveUser());
    }

    @Test
    public void mostActiveUser_isUpdated_correctly() throws DEDException {
        safetyActivities4Covid19.createTicket("idUser1", "ACT-1102");
        Assert.assertEquals(this.safetyActivities4Covid19.getUser("idUser1"), this.safetyActivities4Covid19.mostActiveUser());

        safetyActivities4Covid19.createTicket("idUser4", "ACT-1102");
        safetyActivities4Covid19.createTicket("idUser4", "ACT-1105");

        Assert.assertEquals(this.safetyActivities4Covid19.getUser("idUser4"),
                this.safetyActivities4Covid19.mostActiveUser());
    }
}
