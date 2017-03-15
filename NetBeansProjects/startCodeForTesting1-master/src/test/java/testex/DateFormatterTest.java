
package testex;

import java.util.Date;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DateFormatterTest {
    
    @Mock
    IDateFormatter IDateformatter;
    
    @Mock
    Joke joke;
    
    @Mock
    JokeFetcher jokeFetcher;
    
    @Test
    public void GetFormattedDateTest() throws Exception {
        Date date = new Date();
        String timeZone = "Europe/Copenhagen";
        
        DateFormatter dateFormatter = new DateFormatter();
        
        String result = dateFormatter.getFormattedDate(timeZone, date);
        String expected = "15 mar. 2017 04:59 PM";
        
        assertThat(expected, is(result));
    }
}
