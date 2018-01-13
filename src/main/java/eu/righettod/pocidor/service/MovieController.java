package eu.righettod.pocidor.service;

import eu.righettod.pocidor.util.IDORUtil;
import eu.righettod.pocidor.vo.Movie;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Exposed service to obtains Movies information
 */
@RestController
@EnableAutoConfiguration
public class MovieController {

    /**
     * Simulated storage backend
     */
    private final List<Movie> movies = new ArrayList<>();

    public MovieController() {
        movies.add(new Movie("23589", "StarWars"));
        movies.add(new Movie("25876", "Avengers"));
        movies.add(new Movie("35148", "Jumanji"));
    }

    /**
     * Service to list all available movies
     *
     * @return The list of movies as JSON response
     */
    @RequestMapping(value = "/movies", method = GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Movie> listAllMovies() {
        List<Movie> result = new ArrayList<>();

        try {
            this.movies.forEach(m -> {
                try {
                    //Compute the front end ID fof the current element
                    String frontEndId = IDORUtil.computeFrontEndIdentifier(m.getBackendIdentifier());
                    //Create a new item container with the computed ID and add it to the result list
                    result.add(new Movie(frontEndId, m.getName()));
                } catch (Exception e) {
                    //We print error in stderr here just because it's a POC...
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            //We print error in stderr here just because it's a POC...
            e.printStackTrace();
            result.clear();
        }

        return result;
    }

    /**
     * Service to obtain the information on a specific movie
     *
     * @param id Movie identifier from a front end point of view
     * @return The movie name
     */
    @RequestMapping(value = "/movies/{id}", method = GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String obtainMovieName(@PathVariable("id") String id) {

        Optional<Movie> movie = this.movies.stream().filter(m -> {
            boolean match;
            try {
                //Compute the front end ID for the current element
                String frontEndId = IDORUtil.computeFrontEndIdentifier(m.getBackendIdentifier());
                //Check if the computed ID match the one provided
                match = frontEndId.equals(id);
            } catch (Exception e) {
                //Ensure that in case of error no item is returned
                match = false;
                //We print error in stderr here just because it's a POC...
                e.printStackTrace();
            }
            return match;
        }).findFirst();

        return (movie.isPresent()) ? movie.get().getName() : "";
    }


}
