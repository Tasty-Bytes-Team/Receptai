package lt.tastybytes.receptaiserver.model;

import jakarta.persistence.*;

@Entity
public class Metadata {
    @Id
    private long id;

    private String anonymousName;

    private String anonymousSurname;

    private String ipAddress;
}
