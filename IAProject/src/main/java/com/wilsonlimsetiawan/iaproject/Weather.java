package com.wilsonlimsetiawan.iaproject;
import com.google.maps.errors.ApiException;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import tk.plogitech.darksky.forecast.APIKey;
import tk.plogitech.darksky.forecast.DarkSkyClient;
import tk.plogitech.darksky.forecast.ForecastException;
import tk.plogitech.darksky.forecast.ForecastRequest;
import tk.plogitech.darksky.forecast.ForecastRequestBuilder;
import static tk.plogitech.darksky.forecast.ForecastRequestBuilder.Block.alerts;
import static tk.plogitech.darksky.forecast.ForecastRequestBuilder.Block.daily;
import static tk.plogitech.darksky.forecast.ForecastRequestBuilder.Block.flags;
import static tk.plogitech.darksky.forecast.ForecastRequestBuilder.Block.hourly;
import static tk.plogitech.darksky.forecast.ForecastRequestBuilder.Block.minutely;
import tk.plogitech.darksky.forecast.GeoCoordinates;
import tk.plogitech.darksky.forecast.model.Latitude;
import tk.plogitech.darksky.forecast.model.Longitude;

public class Weather {
    public static String weatherData;
    public static final String API_Key = "14e8050093472d4e0f61acfdbfa6dd50";
    public static long hourDifference;
    public static double weatherMultiplier;
    public static String justTheChance;
  
    public static double initWeatherAPI() throws ApiException 
    ,InterruptedException, IOException, ForecastException{
       
        //so it knows how many hours to add to the weathercall
        int currentTimeIndex =  GUI.currentTimeBox.getSelectedIndex();
        int journeyTimeIndex =  GUI.timeOfJourneyBox.getSelectedIndex();
        hourDifference = journeyTimeIndex - currentTimeIndex;
        
    ForecastRequest request = new ForecastRequestBuilder()
        .key(new APIKey(API_Key))
        .exclude(daily,minutely,hourly,alerts,flags)
        .language(ForecastRequestBuilder.Language.en)
        .time(Instant.now().plus(hourDifference, ChronoUnit.HOURS))
        
         //Specifying the location to be Singapore
        .location(new GeoCoordinates(new Longitude(103.8198), new Latitude(1.3521))).build();
    
    System.out.println(Instant.now().plus(hourDifference, ChronoUnit.HOURS));
        
    DarkSkyClient client = new DarkSkyClient();
    String forecast = client.forecastJsonString(request);
    weatherData = forecast;
   
        //Setting up regex. The pattern we are matching is the instance of
        //"precipProbability":[0-9|/.]+" The[0-9|/.]+" represents a number
        //which is simply the chance of rain that we want
        Pattern pattern = Pattern.compile("\"precipProbability\":[0-9|/.]+"); 
        Matcher matcher = pattern.matcher(weatherData);
        String precipChanceRaw = " ";
 
        //Look for the pattern in the string and make 'precipChanceRaw' that value.
        while (matcher.find()) 
        {
           precipChanceRaw = matcher.group();
        }
 
        //The output we get still has the ""precipProbability":" part attached ,so to remove it
        //we're just replacing it with nothing. Now, justTheChance is only the number.
       justTheChance = precipChanceRaw.replace("\"precipProbability\":", "  ");
        
        //convert the string into a double so its value will be comparable
        double comparison = Double.parseDouble(justTheChance);
      
        if (comparison >= 0.7)
        {
            weatherMultiplier = 1.8;
        }
        else if (comparison >= 0.5)
        {
            weatherMultiplier = 1.5;
        }
        else if (comparison >= 0.3)
        {
            weatherMultiplier = 1.2;
        }  
        else 
        {
            weatherMultiplier = 1.0;         
        }
        return weatherMultiplier; 
    }
  }   

