package eu.righettod.pocidor.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Simple Value Object to represent a stored item.
 */
public class Movie {

    /**
     * We indicate to serializer that this field must never be serialized
     *
     * @see "https://fasterxml.github.io/jackson-annotations/javadoc/2.5/com/fasterxml/jackson/annotation/JsonIgnore.html"
     */
    @JsonIgnore
    private String backendIdentifier;

    /**
     * Theses fields can be included in the serialization
     */
    private String name;
    private int creationYear;
    private String creator;

    public Movie(String backendIdentifier, String name, int creationYear, String creator) {
        this.backendIdentifier = backendIdentifier;
        this.name = name;
        this.creationYear = creationYear;
        this.creator = creator;
    }

    public String getBackendIdentifier() {
        return backendIdentifier;
    }

    public String getName() {
        return name;
    }

    public int getCreationYear() {
        return creationYear;
    }

    public String getCreator() {
        return creator;
    }
}
