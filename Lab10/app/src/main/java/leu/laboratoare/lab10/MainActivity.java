package leu.laboratoare.lab10;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    private EditText etCity;
    private TextView tvResult;
    private Spinner spDays;
    private Button btnSearch;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCity = findViewById(R.id.etCity);
        tvResult = findViewById(R.id.tvResult);
        spDays = findViewById(R.id.spDays);
        btnSearch = findViewById(R.id.btnSearch);

        String[] options = {"1 zi", "5 zile"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                options
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDays.setAdapter(adapter);

        btnSearch.setOnClickListener(v -> {
            String city = etCity.getText().toString().trim();
            String selected = spDays.getSelectedItem().toString();

            if (BuildConfig.WEATHER_API_KEY.isBlank()) {
                tvResult.setText("Lipseste cheia API. Adauga WEATHER_API_KEY in local.properties.");
            } else if (city.isEmpty()) {
                tvResult.setText("Introdu un oras.");
            } else {
                tvResult.setText("Se cauta...");
                new WeatherTask().execute(city, selected);
            }
        });
    }

    private class WeatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                String cityName = strings[0];
                String selectedDays = strings[1];

                String cityKey = getCityKey(cityName);

                if (cityKey == null) {
                    return "Nu s-a gasit orasul.";
                }

                return getForecast(cityName, cityKey, selectedDays);

            } catch (Exception e) {
                return "Eroare: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            tvResult.setText(result);
        }
    }

    private String getCityKey(String cityName) throws Exception {
        String cityEncoded = URLEncoder.encode(cityName, "UTF-8");

        String urlString = "https://dataservice.accuweather.com/locations/v1/cities/search"
                + "?apikey=" + BuildConfig.WEATHER_API_KEY
                + "&q=" + cityEncoded;

        String response = makeRequest(urlString);

        JSONArray jsonArray = new JSONArray(response);

        if (jsonArray.length() == 0) {
            return null;
        }

        JSONObject firstCity = jsonArray.getJSONObject(0);
        return firstCity.getString("Key");
    }

    private String getForecast(String cityName, String cityKey, String selectedDays) throws Exception {
        String endpoint;

        if (selectedDays.equals("1 zi")) {
            endpoint = "1day";
        } else {
            endpoint = "5day";
        }

        String urlString = "https://dataservice.accuweather.com/forecasts/v1/daily/"
                + endpoint + "/"
                + cityKey
                + "?apikey=" + BuildConfig.WEATHER_API_KEY
                + "&metric=true";

        String response = makeRequest(urlString);

        JSONObject jsonObject = new JSONObject(response);
        JSONArray dailyForecasts = jsonObject.getJSONArray("DailyForecasts");

        StringBuilder result = new StringBuilder();

        result.append("Oras: ").append(cityName).append("\n");
        result.append("Cod oras: ").append(cityKey).append("\n\n");

        for (int i = 0; i < dailyForecasts.length(); i++) {
            JSONObject day = dailyForecasts.getJSONObject(i);
            JSONObject temperature = day.getJSONObject("Temperature");

            JSONObject minimum = temperature.getJSONObject("Minimum");
            JSONObject maximum = temperature.getJSONObject("Maximum");

            String date = day.getString("Date").substring(0, 10);

            double minValue = minimum.getDouble("Value");
            double maxValue = maximum.getDouble("Value");
            String unit = minimum.getString("Unit");

            result.append("Ziua ").append(i + 1).append(" - ").append(date).append("\n");
            result.append("Minima: ").append(minValue).append(" ").append(unit).append("\n");
            result.append("Maxima: ").append(maxValue).append(" ").append(unit).append("\n\n");
        }

        return result.toString();
    }

    private String makeRequest(String urlString) throws Exception {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        );

        StringBuilder result = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            result.append(line);
        }

        reader.close();
        connection.disconnect();

        return result.toString();
    }
}
