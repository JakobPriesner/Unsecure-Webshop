package utils.logic;

import de.fhws.biedermann.webshop.utils.logic.OrderLogic;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.ws.rs.BadRequestException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;


public class OrderLogicTest {
    @Test
    public void testGetShippingCostsEmptyCountry() {
        assertTrue(false);
    }
}
