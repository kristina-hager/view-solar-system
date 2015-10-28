package com.kh_android.viewsolarsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android_space.viewplanetsactivity.ViewPlanetsActivity;

public class MainActivity extends ActionBarActivity {

    static final int SOLAR_SYSTEM_RESULT_REQUEST = 1;  // The request code
    private static final String TAG = "VP MainActivity: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * Called when user hits send button - starts the ViewPlanetsActivity which handles showing
     * planetary systems and expects a result in this case. The result is handled by onActivityResult
     */
    //http://developer.android.com/training/basics/intents/result.html
    //explains how to pass data to an activity and get a result back
    public void showSolarSystemWithResult(View view) {
        //start viewplanetsactivity.ViewPlanetsActivity and pass an input along
        Intent intent = new Intent(this, com.android_space.viewplanetsactivity.ViewPlanetsActivity.class);
        intent.putExtra(ViewPlanetsActivity.SYSTEM_NAME, "mini solar system");
        //pass along names of planets
        Bundle planetBundle = new Bundle();
        planetBundle.putStringArray(ViewPlanetsActivity.PLANETS_ARRAY,
                new String[]{"venus", "mercury", "earth", "mars"});
        intent.putExtras(planetBundle);

        //send 'request' code along with activity
        //when you receive the result Intent, the same code will provided back so this app
        //can properly identify the result and determine how to handle it
        Log.i(TAG, "start activity for result with request code " + SOLAR_SYSTEM_RESULT_REQUEST);
        startActivityForResult(intent, SOLAR_SYSTEM_RESULT_REQUEST);
    }

    /**
     * Called when user hits send button - starts the ViewPlanetsActivity which handles showing
     * planetary systems
     */
    public void showEmptySystem(View view) {
        //start viewplanetsactivity.ViewPlanetsActivity and pass an input along
        Intent intent = new Intent(this, com.android_space.viewplanetsactivity.ViewPlanetsActivity.class);
        intent.putExtra(ViewPlanetsActivity.SYSTEM_NAME, "empty system");

        //not asking for result here
        startActivity(intent);
    }

    /**
     *
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "in onActivityResult. requestCode: " + requestCode + ", resultCode: " + resultCode);
        //check for which request we are responding to (only one request in this example,
        //but this is still a good check)
        if (requestCode == SOLAR_SYSTEM_RESULT_REQUEST) {
            //make sure the request was successful
            if (resultCode == RESULT_OK) {
                //do something with the result
                //in this case, we know the library activity returns a string
                //I am not totally sure how to document this in general
                //or if there's an APIdoc equivalent for android modules
//                String result = data.getDataString();
                Bundle bundle = data.getExtras();
                String result = bundle.getString(Intent.EXTRA_TEXT);
                Log.i(TAG, "got result: " + result);
                TextView resultMessage = (TextView) findViewById(R.id.result_message);
                resultMessage.setText(result);
            }
        }

    }
}
