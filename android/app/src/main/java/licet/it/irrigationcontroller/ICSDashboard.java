package licet.it.irrigationcontroller;

import android.app.ProgressDialog;
import android.location.Location;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ICSDashboard extends AppCompatActivity {

    TextView TEMPERATURE, MOISTURE;
    ImageView IC_STATUS;
    Button USER_IC_STATUS;

    private static int USER_VAL = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ics_dashboard);

        TEMPERATURE = (TextView) findViewById(R.id.db_temperature);
        MOISTURE = (TextView) findViewById(R.id.db_moisture);
        IC_STATUS = (ImageView) findViewById(R.id.db_ic_status);
        USER_IC_STATUS = (Button) findViewById(R.id.db_user_ic_status);

        if (USER_VAL == 0)
            USER_IC_STATUS.setBackgroundResource(R.drawable.off);

        else if(USER_VAL == 1)
            USER_IC_STATUS.setBackgroundResource(R.drawable.on);


        USER_IC_STATUS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (USER_VAL == 0) {

                    USER_VAL = 1;
                    USER_IC_STATUS.setBackgroundResource(R.drawable.on);
                    PostThingspeak postThingspeak = new PostThingspeak();
                    postThingspeak.execute();

                }

                else if(USER_VAL == 1) {

                    USER_VAL = 0;
                    USER_IC_STATUS.setBackgroundResource(R.drawable.off);
                    PostThingspeak postThingspeak = new PostThingspeak();
                    postThingspeak.execute();

                }
            }
        });

        GetThingSpeak getThingSpeak = new GetThingSpeak();
        getThingSpeak.execute();

    }


    class PostThingspeak extends AsyncTask<Void, Void, String> {

        protected void onPreExecute() {}

        protected String doInBackground(Void... urls) {

            try {
                URL url = new URL("https://api.thingspeak.com/update?api_key=6EBU1IYO52MLMEZH&field4="+USER_VAL);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                try {

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();

                }

                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {

            if(response != null)
                Toast.makeText(ICSDashboard.this, "Posted!", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(ICSDashboard.this, response, Toast.LENGTH_LONG).show();
        }
    }



    class GetThingSpeak extends AsyncTask<Void, Void, String> {

        ProgressDialog progressDialog = new ProgressDialog(ICSDashboard.this);

        protected void onPreExecute() {

            progressDialog.setTitle("Fetching Data ... ");
            progressDialog.show();

        }

        protected String doInBackground(Void... urls) {
            try {

                URL url = new URL("https://api.thingspeak.com/channels/222785/feeds.json?results=1&api_key=6EBU1IYO52MLMEZH");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {

            if(response == null) {
                Toast.makeText(ICSDashboard.this, "There was an error", Toast.LENGTH_SHORT).show();
                return;
            }


            try {

                JSONObject jsonObject = new JSONObject(response);
                JSONArray feeds = jsonObject.getJSONArray("feeds");
                JSONObject feedsJSONObject = feeds.getJSONObject(0);

                if(!String.valueOf(feedsJSONObject.getDouble("field1")).equals("null")) {

                    MOISTURE.setText(String.valueOf(feedsJSONObject.getDouble("field1")));
                    TEMPERATURE.setText(String.valueOf(feedsJSONObject.getDouble("field2")));

                    if (feedsJSONObject.getDouble("field3") == 1)
                        IC_STATUS.setBackgroundResource(R.drawable.db_on_img);

                    else if (feedsJSONObject.getDouble("field3") == 0)
                        IC_STATUS.setBackgroundResource(R.drawable.db_off_img);

                }

                progressDialog.dismiss();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
