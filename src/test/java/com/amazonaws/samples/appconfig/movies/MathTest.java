package com.amazonaws.samples.appconfig.movies;

import org.junit.Test;

import java.math.BigDecimal;
import com.amazonaws.samples.appconfig.utils.Math;

import static org.junit.Assert.assertEquals;

public class MathTest {

    @Test
    public void testDivide() {
        Math math = new Math();
        math.divide();

        // Test divide(BigDecimal, BigDecimal, int)
        BigDecimal bd = BigDecimal.valueOf(10);
        BigDecimal bd2 = BigDecimal.valueOf(2);
        BigDecimal expectedResult1 = BigDecimal.valueOf(5);
        assertEquals(expectedResult1, bd.divide(bd2, BigDecimal.ROUND_DOWN));

        // Test divide(BigDecimal, int)
        BigDecimal expectedResult2 = BigDecimal.valueOf(5.0);

        // Test divide(BigDecimal, int, int)
        BigDecimal expectedResult3 = BigDecimal.valueOf(5.0);
        assertEquals(expectedResult3, bd.divide(bd2, 1, BigDecimal.ROUND_CEILING));

        BigDecimal expectedResult4 = BigDecimal.valueOf(5.0);
        assertEquals(expectedResult4, bd.divide(bd2, 1, 1));

        // Test setScale(int, int)
        BigDecimal expectedResult5 = BigDecimal.valueOf(10);
        bd.setScale(2, 1);
        assertEquals(expectedResult5, bd);
    }
}
