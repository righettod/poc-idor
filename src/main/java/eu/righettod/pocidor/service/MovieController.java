package eu.righettod.pocidor.service;

import eu.righettod.pocidor.util.IDORUtil;
import eu.righettod.pocidor.vo.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Exposed service to obtains Movies information
 */
@RestController
@EnableAutoConfiguration
public class MovieController {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);

    /**
     * Simulated storage backend
     */
    private final List<Movie> movies = new ArrayList<>();

    public MovieController() {
        movies.add(new Movie("23589", "StarWars", 1977, "George Lucas"));
        movies.add(new Movie("25876", "Avengers", 2014, "Marvel"));
        movies.add(new Movie("35148", "Jumanji", 2005, "Fox"));
    }

    /**
     * Service to list all available movies
     *
     * @return The collection of movies ID and name as JSON response
     */
    @RequestMapping(value = "/movies", method = GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, String> listAllMovies() {
        Map<String, String> result = new HashMap<>();

        try {
            this.movies.forEach(m -> {
                try {
                    //Compute the front end ID fof the current element
                    String frontEndId = IDORUtil.computeFrontEndIdentifier(m.getBackendIdentifier());
                    //Add the computed ID and the associated item name to the result map
                    result.put(frontEndId, m.getName());
                } catch (Exception e) {
                    LOGGER.error("Error during ID generation for real ID {}: {}", m.getBackendIdentifier(), e.getMessage());
                }
            });
        } catch (Exception e) {
            //Ensure that in case of error no item is returned
            result.clear();
            LOGGER.error("Error during processing", e);
        }

        return result;
    }

    /**
     * Service to obtain the information on a specific movie
     *
     * @param id Movie identifier from a front end point of view
     * @return The movie object as JSON response
     */
    @RequestMapping(value = "/movies/{id}", method = GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Movie obtainMovieName(@PathVariable("id") String id) {

        //Search for the wanted movie information using Front End Identifier
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
                LOGGER.error("Error during processing", e);
            }
            return match;
        }).findFirst();

        //We have marked the Backend Identifier class field as excluded from the serialization
        //So we can sent the object to front end through the serializer
        return movie.get();
    }


}
