package utils.logic;

import de.fhws.biedermann.webshop.utils.logic.OrderLogic;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.ws.rs.BadRequestException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;


public class OrderLogicTest {

    @ParameterizedTest
    @CsvSource({
            "8, Deutschland, 8.45",
            "15, Deutschland, 11.95",
            "25, Deutschland, 21.95",
            "8, USA, 18.45",
            "15, USA, 21.95",
            "25, USA, 31.95"})
    public void testGetShippingCostsWithValidData(int deviceCount, String destinationCountry, float expected) {
        float actualCost = OrderLogic.getShippingCosts(deviceCount, destinationCountry);
        assertEquals(expected, actualCost, 0.01f);
    }

    @ParameterizedTest
    @CsvSource({
            "-1, Deutschland, 18.45",
            "0, Deutschland, 18.45"})
    public void testGetShippingCostsWithInvalidDeviceCount(int deviceCount, String destinationCountry) {
        assertThrowsExactly(BadRequestException.class, () -> OrderLogic.getShippingCosts(deviceCount, destinationCountry));
    }

    @Test
    public void testGetShippingCostsEmptyCountry() {
        assertThrowsExactly(BadRequestException.class, () -> OrderLogic.getShippingCosts(10, ""));
    }
}
