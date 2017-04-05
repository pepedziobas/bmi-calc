
package com.example.pepe.labories1;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class CountBMITest {


    @Test
    public void massUnderZeroIsInvalid(){
        //GIVEN (dane testowe)
        float mass = -1.0f;
        //WHEN (inicjalizujemy nasza klase)
        ICountInterface countBMI = new CountBMIForKGM();
        //THEN
        assertFalse(countBMI.isValidWeight(mass));
    }


    @Test
    public void massAboveIsInvalid(){
        //GIVEN (dane testowe)
        float height = 3f;
        //WHEN (inicjalizujemy nasza klase)
        ICountInterface countBMI = new CountBMIForKGM();
        //THEN
        assertFalse(countBMI.isValidHeight(height));
    }
}