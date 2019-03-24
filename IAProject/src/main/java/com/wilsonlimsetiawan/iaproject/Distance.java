package com.wilsonlimsetiawan.iaproject;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;
import java.io.IOException;

public class Distance {
    public static double realDistanceApart;
    public static String finalDistance;
    //API key obtained through Google
    public static final String API_KEY = "AIzaSyB5vWatP6U3w48VtxHS79obKlT8jE959ps";
    

    //method that directy calls upon the GoogleMapsAPI
    public static void initGoogleMapAPI(String key,String addrOne,String addrTwo) 
    //If there were any erors, these exceptions stop the program from crashing
    throws ApiException, InterruptedException, IOException{
        GeoApiContext api = new GeoApiContext.Builder()
		    .apiKey(API_KEY)
		    .build();
        
       DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(api); 
       //requesting two different adresses to make the distance calculation
       DistanceMatrix result = req.origins(addrOne)
               .destinations(addrTwo)
               //ensure its a driving distance
               .mode(TravelMode.DRIVING)
               .language("en-US")
               .await();
        // distApart is simply the distance in meters.       
	double distApart = result.rows[0].elements[0].distance.inMeters;
        // converting the distance into kilometers.
        realDistanceApart = distApart / 1000;
        //converting the double into a String
        finalDistance = Double.toString(realDistanceApart);   
    }
}
    

