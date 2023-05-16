import de.fhws.biedermann.webshop.utils.logic.OrderLogic;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.ws.rs.BadRequestException;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestOrderLogicForTask4 {

    @Test
    public void testAlwaysTrue(){
        assertTrue(true);
    }

    @ParameterizedTest
    @CsvSource({
            "T1.1, 5, Deutschland, 845",
            "T1.2, 5, France, 1845",
            "T1.3, 15, Deutschland, 1195",
            "T1.4, 15, France, 2195",
            "T1.5, 50, Deutschland, 2195",
            "T1.6, 50, France, 3195",
            "T2.3, 1, Deutschland, 845",
            "T2.4, 10, Deutschland, 845",
            "T2.5, 11, Deutschland, 1195",
            "T2.6, 20, Deutschland, 1195",
            "T2.7, 21, Deutschland, 2195",
            "T2.8, 2147483647, Deutschland, 2195",
            "T2.9, 1, Irak, 1845",
            "T2.10, 21, Vereinigte Königreich von Großbritannien und Nordirland, 3195",
    })
    public void testGetShippingCostsWithValidData(String testCaseId, int deviceCount, String destinationCountry, int expected) {
        assertEquals(expected, OrderLogic.getShippingCosts(deviceCount, destinationCountry));
    }

    @ParameterizedTest
    @CsvSource({
            "T1.7, -100, Deutschland",
            "T2.1, -2147483648, Deutschland",
            "T2.2, 0, Deutschland"
    })
    public void testGetShippingCostsWithInvalidDeviceCount(String testCaseId, int deviceCount, String destinationCountry) {
        assertThrowsExactly(BadRequestException.class, () -> OrderLogic.getShippingCosts(deviceCount, destinationCountry));
    }

    @ParameterizedTest
    @CsvSource({"T1.8, 5"})
    public void testGetShippingCostsEmptyCountry(String testCaseId, int deviceCount) {
        assertThrowsExactly(BadRequestException.class, () -> OrderLogic.getShippingCosts(deviceCount, ""));
    }
}
