package org.klem.app.android.gralphcoolique;
import java.text.SimpleDateFormat;


public class Utils {

	public static String getWellFormattedNow() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = sdf.format(new java.sql.Date(System.currentTimeMillis()));
		
		return date;
	}
	
	public static boolean containsOnlyNumbers(String str) {      
        for (int i = 0; i < str.length(); i++) {

            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        
        return true;
    }
}
