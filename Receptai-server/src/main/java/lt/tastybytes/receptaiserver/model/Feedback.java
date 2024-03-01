package lt.tastybytes.receptaiserver.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Feedback {
    @Id
    private long id;

    private String rating; // temporary variable type

    private String comment;

    private Date feedbackDate;
}
