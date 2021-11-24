package uoc.ded.practica;

import org.junit.Assert;
import org.junit.Test;
import uoc.ded.practica.util.DateUtils;

public class DateUtilsTest {
    @Test
    public void testCreateDate() {
        Assert.assertEquals("Fri Jan 01 00:00:00 CET 2021", DateUtils.createDate("01-01-2021 00:00:00").toString());
    }

    @Test
    public void testCreateDateException_returnsNull() {
        Assert.assertNull(DateUtils.createDate("bad date"));
    }

    @Test
    public void testCreateLocalDate() {
        Assert.assertEquals("2021-01-01", DateUtils.createLocalDate("01-01-2021").toString());
    }
}
