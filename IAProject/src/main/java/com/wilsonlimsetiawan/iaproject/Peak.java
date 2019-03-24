package com.wilsonlimsetiawan.iaproject;
public class Peak 
{    
    //initialization of the two static arrays 
    static String[] time = {"00:00","01:00","02:00","03:00","04:00","05:00",
    "06:00","07:00","08:00","09:00","10:00","11:00","12:00","13:00","14:00",
    "15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00"};
    static boolean[] peakTime = {false,false,false,false,false,false,false,true,true,
    true,false,true,false,false,false,false,false,true,true,true,false,false,true,false};
    
    public static double peakTimeMultiplier;
    
    //method to return a time's peak status
    public static boolean checkPeakTime(int a)
    {
        return peakTime[a] == true;
    }
    
    //method to switch the peak status of a certain time
    public static void editPeakTime(int indexPosition, boolean peakStatus)
    {
        if(peakStatus != peakTime[indexPosition]) 
        {
             peakTime[indexPosition] = peakStatus; 
        }            
    }
    
    public static double calculatePeakTimeMultiplier()
    {
    //if the ride is during a peaktime, it should cost more
        int selectedTime = GUI.timeOfJourneyBox.getSelectedIndex();
        if(peakTime[selectedTime] == true)
        {
            peakTimeMultiplier = 1.8;
        }
        else
        {
            peakTimeMultiplier = 1.2;
        }
        return peakTimeMultiplier;
    }
}
