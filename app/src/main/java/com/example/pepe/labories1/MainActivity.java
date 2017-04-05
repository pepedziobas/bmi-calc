package com.example.pepe.labories1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textViewHeight) TextView textViewHeight;
    @BindView(R.id.textViewWeight) TextView textViewWeight;
    @BindView(R.id.textViewResult) TextView textViewResult;
    @BindView(R.id.editTextHeight) EditText editTextHeight;
    @BindView(R.id.editTextWeight) EditText editTextWeight;
    @BindView(R.id.calculateBMIButton) Button calculateBMIButton;
    @BindView(R.id.switchBtn1) Button switchBtn1;
    @BindView(R.id.switchBtn2) Button switchBtn2;
    @BindView(R.id.legendUnderweight) TextView legendUnderweight;
    @BindView(R.id.legendNormal) TextView legendNormal;
    @BindView(R.id.legendOverweight) TextView legendOverweight;
    @BindView(R.id.legendObesity) TextView legendObesity;


    CountBMIForKGM kgMCalc = new CountBMIForKGM();
    CountBMIForLbsInch lbsInchCalc = new CountBMIForLbsInch();
    boolean flag = false;

    public static final String STATE_FLAG = "flag";
    public static final String STATE_RESULT = "result";

    public static final String MY_PREFERENCES = "myPreferences";
    public static final String WEIGHT_SP = "weightSP";
    public static final String HEIGHT_SP = "heightSP";
    public static final String RESULT_SP = "resultSP";
    public static final String SWITCH_SP = "switchSP";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initSwitch(switchBtn1, switchBtn2);
        initCalculateBMIButton(calculateBMIButton);

//         Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) //no
            onCreateWhenRotate(savedInstanceState);
        else //yes
            onCreateWhenSavedSP();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clean:
                Toast.makeText(this, R.string.action_clean_info, Toast.LENGTH_SHORT).show();
                clean();
                break;

            case R.id.menuSave:
                Toast.makeText(this, R.string.menuSaveInfo, Toast.LENGTH_SHORT).show();
                savingAppStateSP();
                break;

            case R.id.menuShare:
                share();
                break;

            case R.id.menuAboutAuthor:
                Intent i = new Intent(getApplicationContext(),AboutAuthorActivity.class);
                startActivity(i);
                break;
            default:
                break;
        }
        return true;
    }

    public void onCreateWhenRotate(Bundle savedInstanceState){
        // If STATE_FLAG is true, restore values assigned to lbs/inch mode we mustnt check if flag is false because kg/m is default
        if (savedInstanceState.getBoolean(STATE_FLAG)) {
            flag = true;
            switchBtn2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.colorAccent));
            switchBtn1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.colorBtnOff));
            editTextWeight.setHint(R.string.hintWLBS);
            editTextHeight.setHint(R.string.hintHINCH);
        }
        //Check whether score field is "" or not
        if (savedInstanceState.getInt(STATE_RESULT) != -1) {
            dealWithResult(savedInstanceState.getFloat(STATE_RESULT));
            textViewResult.setText(String.valueOf(savedInstanceState.getFloat(STATE_RESULT)));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(STATE_FLAG, flag);

        savedInstanceState.putInt(STATE_RESULT, -1);

        if(!textViewResult.getText().equals("")) {
            savedInstanceState.putFloat(STATE_RESULT, Float.valueOf(textViewResult.getText().toString()));
        }
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onCreateWhenSavedSP(){
        SharedPreferences settings = getSharedPreferences(MY_PREFERENCES, 0);

        //if nothing was previously saved we mustn't do that stuff
        if (settings.contains(WEIGHT_SP)) {

            boolean switchSP = settings.getBoolean(SWITCH_SP, false);
            if (switchSP == true) {
                flag = true;
                switchBtn1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.color.colorBtnOff));
                switchBtn2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.color.colorAccent));
                editTextWeight.setHint(R.string.hintWLBS);
                editTextHeight.setHint(R.string.hintHINCH);
            }
            else {
                flag = false;
                switchBtn1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.color.colorAccent));
                switchBtn2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.color.colorBtnOff));
                editTextWeight.setHint(R.string.hintWKg);
                editTextHeight.setHint(R.string.hintHM);
            }

            float weightSP = settings.getFloat(WEIGHT_SP, 0f);
            if (weightSP != -1)
                editTextWeight.setText(String.valueOf(weightSP));
            // else editTextWeight.setText("");

            float heightSP = settings.getFloat(HEIGHT_SP, 0f);
            if (heightSP != -1)
                editTextHeight.setText(String.valueOf(heightSP));
            // else editTextHeight.setText("");

            float resultSP = settings.getFloat(RESULT_SP, 0f);
            if(resultSP != -1) {
                textViewResult.setText(String.valueOf(resultSP));
                dealWithResult(resultSP);
            }
        }
    }

    public void savingAppStateSP(){

        SharedPreferences settings = getSharedPreferences(MY_PREFERENCES, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putBoolean(SWITCH_SP,flag);

        if(editTextWeight.getText().toString().equals(""))
            editor.putFloat(WEIGHT_SP, -1);
        else
            editor.putFloat(WEIGHT_SP, Float.valueOf(editTextWeight.getText().toString()));

        if(editTextHeight.getText().toString().equals(""))
            editor.putFloat(HEIGHT_SP, -1);
        else
            editor.putFloat(HEIGHT_SP, Float.valueOf(editTextHeight.getText().toString()));

        if(textViewResult.getText().toString().equals(""))
            editor.putFloat(RESULT_SP, -1);
        else
            editor.putFloat(RESULT_SP, Float.valueOf(textViewResult.getText().toString()));

        editor.commit();
    }

    public void clean(){
        flag = false;
        editTextWeight.setText("");
        editTextHeight.setText("");
        editTextWeight.setHint(R.string.hintWKg);
        editTextHeight.setHint(R.string.hintHM);
        textViewResult.setText("");
        textViewResult.setBackground(null);
        setLegendBackgroundColor(legendUnderweight, ContextCompat.getDrawable(getApplicationContext(),R.color.colorBtnOff), legendNormal,legendOverweight,legendObesity);
        setLegendTextColor(legendUnderweight, Color.BLACK, legendNormal, legendOverweight, legendObesity);
        switchBtn1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.color.colorAccent));
        switchBtn2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.color.colorBtnOff));
    }

    public void share(){
        if(textViewResult.getText().equals(""))
            Toast.makeText(this, R.string.menuShareFailInfo, Toast.LENGTH_SHORT).show();
        else {
            String textToShare = getString(R.string.menuShareInfo1) + textViewResult.getText().toString() + getString(R.string.menuShareInfo2);
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, textToShare);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
    }

    public void initSwitch(final Button switchBtn1, final Button switchBtn2){

        initSwitchButton(switchBtn1,switchBtn2);
        initSwitchButton(switchBtn2,switchBtn1);
    }

    public void initSwitchButton(final Button button, final Button other){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.color.colorAccent));
                other.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.color.colorBtnOff));
                //switchBtn2.setBackground(getResources().getDrawable(R.color.colorBtnOff));
                if(button == switchBtn1){
                    editTextWeight.setHint(R.string.hintWKg);
                    editTextHeight.setHint(R.string.hintHM);
                    Toast.makeText(getBaseContext(),R.string.changeToKgM, Toast.LENGTH_SHORT).show();
                    flag = false;
                }
                else{
                    editTextWeight.setHint(R.string.hintWLBS);
                    editTextHeight.setHint(R.string.hintHINCH);
                    Toast.makeText(getBaseContext(),R.string.changeToLbInch, Toast.LENGTH_SHORT).show();
                    flag = true;
                }
                editTextHeight.setError(null);
                editTextWeight.setError(null);
            }
        });
    }

    public void initCalculateBMIButton(Button calculateBMIButton){

        calculateBMIButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextWeight.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(editTextHeight.getWindowToken(), 0);

                if (flag == false)
                    countBMIAndDealWithErrors(kgMCalc);
                else
                    countBMIAndDealWithErrors(lbsInchCalc);
            }
        });
    }
    public void countBMIAndDealWithErrors(ICountInterface calc){

        if (isEmpty(editTextWeight))
            showErrorWeight(calc);
        if (isEmpty(editTextHeight))
            showErrorHeight(calc);

        else if(!isEmpty(editTextHeight) && !isEmpty(editTextWeight)){

            float weight = Float.parseFloat(editTextWeight.getText().toString());
            float height = Float.parseFloat(editTextHeight.getText().toString());

            if(!calc.isValidWeight(weight))
                showErrorWeight(calc);
            if(!calc.isValidHeight(height))
                showErrorHeight(calc);
            else if(calc.isValidWeight(weight) && calc.isValidHeight(height)) {
                textViewResult.setText(String.valueOf(calc.countBMI(weight, height)));
                dealWithResult(calc.countBMI(weight,height));
            }
        }
    }

    private void dealWithResult(float result){

        if(result < 18.5f) {
            textViewResult.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.color.underweight));
            setLegendTextColor(legendUnderweight, Color.WHITE, legendNormal,legendOverweight,legendObesity);
            setLegendBackgroundColor(legendUnderweight, ContextCompat.getDrawable(getApplicationContext(),R.color.underweight),legendNormal,legendOverweight,legendObesity);
        }
        else if(result < 24.9f){
            textViewResult.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.normal));
            setLegendTextColor(legendNormal, Color.WHITE, legendUnderweight,legendOverweight,legendObesity);
            setLegendBackgroundColor(legendNormal, ContextCompat.getDrawable(getApplicationContext(),R.color.normal),legendUnderweight,legendOverweight,legendObesity);
        }
        else if(result < 29.9f){
            textViewResult.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.overweight));
            setLegendTextColor(legendOverweight, Color.WHITE, legendUnderweight,legendNormal,legendObesity);
            setLegendBackgroundColor(legendOverweight, ContextCompat.getDrawable(getApplicationContext(),R.color.overweight),legendUnderweight,legendNormal,legendObesity);
        }
        else {
            textViewResult.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.color.obesity));
            setLegendTextColor(legendObesity, Color.WHITE, legendUnderweight,legendNormal,legendOverweight);
            setLegendBackgroundColor(legendObesity, ContextCompat.getDrawable(getApplicationContext(),R.color.obesity),legendUnderweight,legendNormal,legendOverweight);
        }
    }

    private void setLegendTextColor(TextView tv, int color, TextView tv1, TextView tv2, TextView tv3){
        tv.setTextColor(color);
        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
    }
    private void setLegendBackgroundColor(TextView tv, Drawable color, TextView tv1, TextView tv2, TextView tv3){
        tv.setBackground(color);
        tv1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.color.colorBtnOff));
        tv2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.color.colorBtnOff));
        tv3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.color.colorBtnOff));
    }

    private void showErrorWeight(ICountInterface unit) {
        if(unit instanceof CountBMIForKGM)
            editTextWeight.setError(getResources().getString(R.string.errorW));
        else
            editTextWeight.setError(getResources().getString(R.string.errorW2));
    }

    private void showErrorHeight(ICountInterface unit) {
        if(unit instanceof CountBMIForKGM)
            editTextHeight.setError(getResources().getString(R.string.errorH));
        else
            editTextHeight.setError(getResources().getString(R.string.errorH2));
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }
}
