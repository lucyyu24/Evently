package com.example.lucy.evently;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class NewEvent extends AppCompatActivity {

    private Firebase fireBase;
    private static Event event;
    private static String description;
    private static String date;
    private static String start;
    private static String end;
    private static double longitude;
    private static double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void showStartTimePickerDialog(View v) {
        DialogFragment newFragment = new StartTimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void showEndTimePickerDialog(View v) {
        DialogFragment newFragment = new EndTimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void setLocation (View view) {
        Intent intent = new Intent(this, SetLocation.class);
        startActivityForResult(intent, 1);
    }

    public void createEvent(View view) {
        Firebase.setAndroidContext(this);
        fireBase = new Firebase("https://evently.firebaseio.com/events");

        TextView dTv = (TextView) findViewById(R.id.description);
        description = dTv.getText().toString();
        Log.v("Cindy check desc", description);

        TextView tv = (TextView) findViewById(R.id.date);
        date = tv.getText().toString();
        Log.v("Cindy check date", date);

        TextView startTv = (TextView) findViewById(R.id.start);
        start = startTv.getText().toString();
        Log.v("Cindy check start", start);

        TextView endTv = (TextView) findViewById(R.id.end);
        end = endTv.getText().toString();
        Log.v("Cindy check end", end);



        event = new Event(description,date,start,end,latitude,longitude);

        Firebase newVal = fireBase.push();
        newVal.setValue(event);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){

                longitude = data.getDoubleExtra("lng", 0.0);
                Toast toast = Toast.makeText(this, Double.toString(longitude), 200);
                toast.show();
                latitude = data.getDoubleExtra("lat", 0.0);

                TextView locTv = (TextView) findViewById(R.id.location);
                locTv.setText("(" + latitude+","+longitude+")");
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
