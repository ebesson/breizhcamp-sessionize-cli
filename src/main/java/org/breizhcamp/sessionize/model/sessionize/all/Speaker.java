package org.breizhcamp.sessionize.model.sessionize.all;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Speaker {

    String id;
    String firstName;
    String lastName;
    String bio;
    String profilePciture;
    String fullName;
    String tagLine;
    List<Link> links;
}
