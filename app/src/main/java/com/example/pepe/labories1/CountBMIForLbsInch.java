package com.example.pepe.labories1;

/**
 * Created by Pepe on 2017-03-24.
 */

public class CountBMIForLbsInch implements ICountInterface {

    static final float MIN_WEIGHT = 20f;
    static final float MAX_WEIGHT = 550f;
    static final float MIN_HEIGHT = 20f;
    static final float MAX_HEIGHT = 100f;

    final static float onePoundInKgs = 0.45359237f;
    final static float oneInchInM = 0.0254f;

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

        return poundsToKg(weight)/inchesToMeters(height)/inchesToMeters(height);
    }


    float poundsToKg(float pounds){
        return pounds * onePoundInKgs;
    }

    float inchesToMeters(float inches){
        return inches*oneInchInM;
    }
}

