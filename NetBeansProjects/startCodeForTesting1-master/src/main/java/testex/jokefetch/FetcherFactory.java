
package testex.jokefetch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import testex.JokeException;

public class FetcherFactory implements IFetcherFactory {

    private final List<String> allTypes = Arrays.asList("EduJoke", "ChuckNorris", "Moma", "Tambal");
    
    @Override
    public List<String> getAvailableTypes() {
        return allTypes;
    }

    @Override
    public List<IJokeFetcher> getJokeFetchers(String jokesToFetch) {
        List<IJokeFetcher> fetchers = new ArrayList<IJokeFetcher>();
        String[] tokes = jokesToFetch.split(",");
        
        Class c;
        IJokeFetcher fetch;
        
        try {
            for(String token : tokes){
                if(!allTypes.contains(tokes)){
                    throw new JokeException("Inputs (jokesToFetch) contains types not recognized");
                }
                else {
                    fetchers.add( (IJokeFetcher) Class.forName("testex.jokefetch." + token).newInstance()); 
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return fetchers;
    }
    
}
