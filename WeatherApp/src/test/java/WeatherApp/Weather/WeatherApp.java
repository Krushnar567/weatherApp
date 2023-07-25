package WeatherApp.Weather;

import java.io.InputStreamReader;
import java.util.Scanner;
import java.io.BufferedReader;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class WeatherApp {
	
    private static final String API_KEY = "b6907d289e10d714a6e88b30761fae22";
    
    private static final String API_BASE_URL = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=" + API_KEY;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int choice;
        
        do {
            System.out.println("1. Get weather");
            System.out.println("2. Get Wind Speed");
            System.out.println("3. Get Pressure");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

         switch (choice) {
                case 1:
                    handleWeather();
                    break;
                case 2:
                    handleWindSpeed();
                    break;
                case 3:
                    handlePressure();
                    break;
                case 0:
                    System.out.println("Terminating the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } 
        
        while (choice != 0);

                 scanner.close();
    }

    private static JSONObject fetchWeatherData() {
        try {
            URL url = new URL(API_BASE_URL);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return new JSONObject(response.toString());
        } 
        
        
        catch (IOException | JSONException e) {
            e.printStackTrace();
      }
        return null;
    }

    private static void handleWeather() {
    	
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the date (yyyy-MM-dd): ");
        
        String inputDate = scanner.next();

        JSONObject weatherData = fetchWeatherData();
        
        if (weatherData != null) {
        	
            JSONArray forecasts = weatherData.getJSONArray("list");
            
            for (int i = 0; i < forecasts.length(); i++) {
                JSONObject forecast = forecasts.getJSONObject(i);
                String dateTime = forecast.getString("dt_txt");
                
                if (dateTime.startsWith(inputDate)) {
                    JSONObject mainData = forecast.getJSONObject("main");
                    
                    double temperature = mainData.getDouble("temp");
                    
                    System.out.println("Temperature on " + dateTime + " is " + temperature + "°C");
                    
                   // return;
                }
            }
            
                    System.out.println("Weather data not found for the specified date.");
        }
        
             else {
            	 
                    System.out.println("Failed to fetch weather data from API. Please try again later.");
        }
    }

    private static void handleWindSpeed() {
    	
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the date (yyyy-MM-dd): ");
        String inputDate = scanner.next();

        JSONObject weatherData = fetchWeatherData();
        if (weatherData != null) {
        	
            JSONArray forecasts = weatherData.getJSONArray("list");
            
            for (int i = 0; i < forecasts.length(); i++) {
                JSONObject forecast = forecasts.getJSONObject(i);
                String dateTime = forecast.getString("dt_txt");
                
                if (dateTime.startsWith(inputDate)) {
                    JSONObject windData = forecast.getJSONObject("wind");
                    double windSpeed = windData.getDouble("speed");
                    System.out.println("Wind Speed on " + dateTime + " is " + windSpeed + " m/s");
                    return;
                }
            }
            
            System.out.println("Wind Speed data not found for the specified date.");
            
        } 
        
                else {
                	
                     System.out.println("Failed to fetch weather data from API. Please try again later.");
        }
    }

    private static void handlePressure() {
    	
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the date (yyyy-MM-dd): ");
        String inputDate = scanner.next();

        JSONObject weatherData = fetchWeatherData();
        
        if (weatherData != null) {
        	
            JSONArray forecasts = weatherData.getJSONArray("list");
            
            for (int i = 0; i < forecasts.length(); i++) {
            	
                JSONObject forecast = forecasts.getJSONObject(i);
                String dateTime = forecast.getString("dt_txt");
                
                if (dateTime.startsWith(inputDate)) {
                    JSONObject mainData = forecast.getJSONObject("main");
                    double pressure = mainData.getDouble("pressure");
                    System.out.println("Pressure on " + dateTime + " is " + pressure + " hPa");
                    return;
                }
            }
            
            System.out.println("Pressure data not found for the specified date.");
            
        } 
        
        else {
        	
            System.out.println("Failed to fetch weather data from API. Please try again later.");
        }
    }
}