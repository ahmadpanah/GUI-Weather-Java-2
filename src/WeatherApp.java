import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.*;

public class WeatherApp {
	
	private static JFrame frame;
	private static JTextField locationField;
	private static JTextArea weatherDisplay;
	private static JButton fetchButton;
	private static String apiKey = "d488c94385ca4aa59d32cf9f73f638bf";
	
	private static String fetchWeatherData(String city) {
		try {
			URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String response = "";
			String line;
			
			while((line = reader.readLine()) != null) {
				response += line;
			}
			
			reader.close();
			
			JSONObject jsonObject = (JSONObject) JSONValue.parse(response.toString());
			JSONObject mainObj = (JSONObject) jsonObject.get("main");
			
			double temperature = (double) mainObj.get("temp");
			long humidity = (long) mainObj.get("humidity");
			
			JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
			JSONObject weather = (JSONObject) weatherArray.get(0);
			String description = (String) weather.get("description");
			
			return "Description: " + description + "\ntemperature: " 
			+ temperature + "°С" +  "\nHumidity: " + humidity + "%";
		} catch (Exception e) {
			return "Failed to fetch Weather. Please Check Your Internet";
		}
	}
	
	public static void main(String[] args) {
		frame = new JFrame("Weather Application");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,300);
		frame.setLayout(new FlowLayout());
		
		locationField = new JTextField(15);
		fetchButton = new JButton("Submit");
		weatherDisplay = new JTextArea(10,30);
		weatherDisplay.setEditable(false);
		
		frame.add(new JLabel ("Enter City Name:"));
		frame.add(locationField);
		frame.add(fetchButton);
		frame.add(weatherDisplay);
		
		fetchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String city = locationField.getText();
				String WeatherInfo = fetchWeatherData(city);
				weatherDisplay.setText(WeatherInfo);
				
			}
		});
		
		frame.setVisible(true);
		
	}

}
