
package testex;

import java.util.Date;

public interface IDateFormatter {
    
    // Step 2 - Create an interface IDateFormatter, from the DateFormatter
    // class, and let the class implement this interface.
    
    String getFormattedDate(String timeZone, Date time) throws JokeException;
}
