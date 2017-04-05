package com.example.pepe.labories1;

/**
 * Created by Pepe on 2017-03-15.
 */

public class CountBMIForKGM implements ICountInterface {

    static final float MIN_WEIGHT = 10f;
    static final float MAX_WEIGHT = 250f;
    static final float MIN_HEIGHT = 0.5f;
    static final float MAX_HEIGHT = 2.5f;

    @Override
    public boolean isValidWeight(float weight) {
        return weight >= MIN_WEIGHT && weight <= MAX_WEIGHT;
    }

    @Override
    public boolean isValidHeight(float height) {
        return height >= MIN_HEIGHT && height <= MAX_HEIGHT;
    }

    @Override
    public float countBMI(float weight, float height) {
        if(!isValidWeight(weight) || !isValidHeight(height)) throw new IllegalArgumentException("Zła waga lub zły wzrost ");

        return weight/height/height;
    }


}

//zadanie domowe - testy
//MASS
//pole
//HEIGHT
//pole
//Button count
//BMI
//20