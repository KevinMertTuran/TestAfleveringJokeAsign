package testex;

import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import testex.jokefetch.ChuckNorris;
import testex.jokefetch.EduJoke;
import testex.jokefetch.IFetcherFactory;
import testex.jokefetch.IJokeFetcher;
import testex.jokefetch.Moma;
import testex.jokefetch.Tambal;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class JokeFetcherTest {

    @Mock
    IDateFormatter IDateformatter;

    @Mock
    Joke newJoke;

    @Mock
    JokeFetcher jokeFetcher;

    @Mock
    IDateFormatter IDateFormatterMock;

    @Mock
    IFetcherFactory IFetcherfactory;

    @Mock
    ChuckNorris chuckNorris;

    @Mock
    Moma moma;

    @Mock
    Tambal tambal;

    @Mock
    EduJoke eduJoke;

    @Before
    public void setup() {
        List<IJokeFetcher> fetchers = Arrays.asList(eduJoke, chuckNorris, moma, tambal);
        when(IFetcherfactory.getJokeFetchers("EduJoke, ChuckNorris, Moma, Tambal")).thenReturn(fetchers);

        List<String> availableTypes = Arrays.asList("EduJoke", "ChuckNorris", "Moma", "Tambal");
        when(IFetcherfactory.getAvailableTypes()).thenReturn(availableTypes);

        jokeFetcher = new JokeFetcher(IDateFormatterMock, IFetcherfactory);
    }

    @Test
    public void getAvailableTypesTest() throws Exception {
        List<String> availableTypes = jokeFetcher.getAvailableTypes();

        assertThat(availableTypes, hasItems());
        assertThat(availableTypes, hasItems("eduprog", "chucknorris", "moma", "tambal"));
    }

    @Test
    public void checkIfValidTokenTest() throws Exception {
        boolean invalid = jokeFetcher.isStringValid("test,test2,test3,test4");
        boolean valid = jokeFetcher.isStringValid("eduprog,chucknorris,moma,tambal");

        assertThat(valid, equalTo(true));
        assertThat(invalid, equalTo(false));
    }

    @Test
    public void getJokesTest() throws Exception {
        given(IDateformatter.getFormattedDate(eq("Europe/Copenhagen"), anyObject())).willReturn("17 feb. 2017 10:56 AM");
        Jokes jokes = jokeFetcher.getJokes("eduprog,chucknorris,chucknorris,moma,tambal", "Europe/Copenhagen");

        assertThat(jokes.getJokes().size(), greaterThan(3));
        assertThat(jokes.getTimeZoneString(), containsString("17 feb. 2017 10:56 AM"));

        verify(IDateformatter, times(1)).getFormattedDate(anyObject(), anyObject());

        jokes.getJokes().stream().forEach((Joke joke) -> {
            assertThat(joke, equalTo(newJoke));
        });

        verify(eduJoke, times(1)).getJoke();
        verify(chuckNorris, times(2)).getJoke();
        verify(moma, times(1)).getJoke();
        verify(tambal, times(1)).getJoke();
    }

}
