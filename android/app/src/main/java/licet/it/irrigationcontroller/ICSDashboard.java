package licet.it.irrigationcontroller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ICSDashboard extends AppCompatActivity {

    TextView TEMPERATURE, MOISTURE, IC_STATUS_TEXT, USER_IC_STATUS_TEXT;
    ImageView IC_STATUS;
    Button UPDATE, SINGLE_FORECAST;
    ImageButton USER_IC_STATUS;

    LinearLayout LL_MOISTURE, LL_TEMPERATURE, LL_STATUS, LL_SWITCH;

    private static int USER_VAL, NO_OF_VAL;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ics_dashboard);

        setTitle("\t\t\t\t\t\t\t\t\t\tICS DASHBOARD");

        TEMPERATURE = (TextView) findViewById(R.id.db_temperature);
        MOISTURE = (TextView) findViewById(R.id.db_moisture);
        IC_STATUS = (ImageView) findViewById(R.id.db_ic_status);
        IC_STATUS_TEXT = (TextView) findViewById(R.id.db_ic_status_text);
        USER_IC_STATUS = (ImageButton) findViewById(R.id.db_user_ic_status);
        USER_IC_STATUS_TEXT = (TextView) findViewById(R.id.db_user_ic_status_text);
        UPDATE = (Button) findViewById(R.id.db_update);
        SINGLE_FORECAST = (Button) findViewById(R.id.db_single_forecast);

        LL_MOISTURE = (LinearLayout) findViewById(R.id.db_LL_moisture);
        LL_TEMPERATURE = (LinearLayout) findViewById(R.id.db_LL_temperature);
        LL_STATUS = (LinearLayout) findViewById(R.id.db_LL_status);
        LL_SWITCH = (LinearLayout) findViewById(R.id.db_LL_switch);


        progressDialog = new ProgressDialog(ICSDashboard.this);


        USER_IC_STATUS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences GET = getSharedPreferences("ICS", 0);
                USER_VAL = GET.getInt("USER_VAL", 0);

                if (USER_VAL == 2412) {

                    USER_VAL = 2512;
                    SharedPreferences PUT = getSharedPreferences("ICS", 0);
                    SharedPreferences.Editor editor = PUT.edit();
                    editor.putInt("USER_VAL", USER_VAL);
                    editor.apply();

                    USER_IC_STATUS.setBackgroundResource(R.drawable.db_status_on_img);
                    USER_IC_STATUS_TEXT.setText("ON");
                    USER_IC_STATUS_TEXT.setTextColor(Color.GREEN);
                    PostThingspeak postThingspeak = new PostThingspeak();
                    postThingspeak.execute();

                } else if (USER_VAL == 2512) {

                    USER_VAL = 2412;
                    SharedPreferences PUT = getSharedPreferences("ICS", 0);
                    SharedPreferences.Editor editor = PUT.edit();
                    editor.putInt("USER_VAL", USER_VAL);
                    editor.apply();

                    USER_IC_STATUS.setBackgroundResource(R.drawable.db_status_off_img);
                    USER_IC_STATUS_TEXT.setText("OFF");
                    USER_IC_STATUS_TEXT.setTextColor(Color.RED);
                    PostThingspeak postThingspeak = new PostThingspeak();
                    postThingspeak.execute();

                }

                else {

                    USER_VAL = 2512;
                    SharedPreferences PUT = getSharedPreferences("ICS", 0);
                    SharedPreferences.Editor editor = PUT.edit();
                    editor.putInt("USER_VAL", USER_VAL);
                    editor.apply();

                    USER_IC_STATUS.setBackgroundResource(R.drawable.db_status_on_img);
                    USER_IC_STATUS_TEXT.setText("ON");
                    USER_IC_STATUS_TEXT.setTextColor(Color.GREEN);
                    PostThingspeak postThingspeak = new PostThingspeak();
                    postThingspeak.execute();

                }
            }
        });


        SINGLE_FORECAST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ICSDashboard.this, SingleForecast.class);
                startActivity(intent);

            }
        });


        UPDATE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                performUpdate();

            }
        });


        LL_MOISTURE.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:

                                dialog.dismiss();

                                break;

                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ICSDashboard.this);
                builder.setTitle("Soil Moisture Value Guide")
                        .setMessage("Value < 500 : Irrigation Necessary\nValue > 500 : Irrigation Unnecessary")
                        .setPositiveButton("OK", dialogClickListener).show();

                return true;
            }
        });


        LL_TEMPERATURE.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:

                                dialog.dismiss();

                                break;

                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ICSDashboard.this);
                builder.setTitle("Temperature Value Guide (in °C)")
                        .setMessage("Value > 22 : Irrigation Necessary\nValue < 22 : Irrigation Unnecessary")
                        .setPositiveButton("OK", dialogClickListener).show();

                return true;
            }
        });


        LL_STATUS.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:

                                dialog.dismiss();

                                break;

                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ICSDashboard.this);
                builder.setMessage("Denotes the current status of the Irrigation Controller")
                        .setPositiveButton("OK", dialogClickListener).show();

                return true;
            }
        });


        LL_SWITCH.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:

                                dialog.dismiss();

                                break;

                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ICSDashboard.this);
                builder.setMessage("Used to control the working of the Irrigation Controller")
                        .setPositiveButton("OK", dialogClickListener).show();

                return true;
            }
        });

        if(isConnectingToInternet())
            performUpdate();

        else {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:

                            Intent intent = new Intent(ICSDashboard.this, ICSDashboard.class);
                            startActivity(intent);

                            break;

                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(ICSDashboard.this);
            builder.setMessage("Network Failure : Please check your Internet Connection !")
                    .setPositiveButton("Try Again", dialogClickListener).show();
        }

    }


    private void performUpdate() {

        progressDialog.setMessage("Fetching Data ... ");
        progressDialog.show();

        SharedPreferences GET = getSharedPreferences("ICS", 0);
        USER_VAL = GET.getInt("USER_VAL", 0);

        if (USER_VAL == 2412) {
            USER_IC_STATUS.setBackgroundResource(R.drawable.db_status_off_img);
            USER_IC_STATUS_TEXT.setText("OFF");
            USER_IC_STATUS_TEXT.setTextColor(Color.RED);
        } else if (USER_VAL == 2512){
            USER_IC_STATUS.setBackgroundResource(R.drawable.db_status_on_img);
            USER_IC_STATUS_TEXT.setText("ON");
            USER_IC_STATUS_TEXT.setTextColor(Color.GREEN);
        }

        NO_OF_VAL = 1;

        GetThingSpeak getThingSpeak = new GetThingSpeak();
        getThingSpeak.execute();

    }


    class PostThingspeak extends AsyncTask<Void, Void, String> {

        protected void onPreExecute() {}

        protected String doInBackground(Void... urls) {

            try {

                SharedPreferences GET = getSharedPreferences("ICS", 0);
                USER_VAL = GET.getInt("USER_VAL", 0);

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

            if(response != null) {

                if(USER_VAL == 2412)
                    Toast.makeText(ICSDashboard.this, "Value OFF has been posted !", Toast.LENGTH_LONG).show();

                else if(USER_VAL == 2512)
                    Toast.makeText(ICSDashboard.this, "Value ON has been posted !", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(ICSDashboard.this, "Post Error !", Toast.LENGTH_LONG).show();
        }
    }



    class GetThingSpeak extends AsyncTask<Void, Void, String> {


        protected void onPreExecute() {

        }

        protected String doInBackground(Void... urls) {
            try {

                URL url = new URL("https://api.thingspeak.com/channels/222785/feeds.json?results="+NO_OF_VAL+"&api_key=6EBU1IYO52MLMEZH");
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
                Toast.makeText(ICSDashboard.this, "Fetch Error !", Toast.LENGTH_SHORT).show();
                return;
            }


            try {

                JSONObject jsonObject = new JSONObject(response);
                JSONArray feeds = jsonObject.getJSONArray("feeds");
                JSONObject feedsJSONObject = feeds.getJSONObject(0);

                if(!feedsJSONObject.isNull("field1")) {

                    MOISTURE.setText(String.valueOf(feedsJSONObject.getDouble("field1")));
                    TEMPERATURE.setText(String.valueOf(feedsJSONObject.getDouble("field2")) + " °C");

                    if (feedsJSONObject.getDouble("field3") == 1) {
                        IC_STATUS.setBackgroundResource(R.drawable.db_switch_on_img);
                        IC_STATUS_TEXT.setText("ON");
                        IC_STATUS_TEXT.setTextColor(Color.GREEN);
                    }

                    else if (feedsJSONObject.getDouble("field3") == 0) {
                        IC_STATUS.setBackgroundResource(R.drawable.db_switch_off_img);
                        IC_STATUS_TEXT.setText("OFF");
                        IC_STATUS_TEXT.setTextColor(Color.RED);
                    }

                    progressDialog.dismiss();

                }

                else {

                    NO_OF_VAL++;
                    GetThingSpeak getThingSpeak = new GetThingSpeak();
                    getThingSpeak.execute();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private boolean isConnectingToInternet() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;

            for (Network mNetwork : networks) {

                networkInfo = connectivityManager.getNetworkInfo(mNetwork);

                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }

        } else {

            if (connectivityManager != null) {

                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();

                if (info != null) {

                    for (NetworkInfo anInfo : info) {

                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

}