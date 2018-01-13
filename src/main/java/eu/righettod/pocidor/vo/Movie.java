package eu.righettod.pocidor.vo;

/**
 * Simple Value Object to represent a stored item.
 */
public class Movie {

    private String backendIdentifier;
    private String name;

    public Movie(String backendIdentifier, String name) {
        this.backendIdentifier = backendIdentifier;
        this.name = name;
    }

    public String getBackendIdentifier() {
        return backendIdentifier;
    }

    public String getName() {
        return name;
    }
}
