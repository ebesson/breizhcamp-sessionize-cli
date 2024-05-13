package org.breizhcamp.sessionize.model.website;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

@Data
@Accessors(chain = true)
public class Speaker {

    String id;
    String lastname;
    String firstname;
    String imageProfilURL;
    String bio;
    String github;
    String googleplus;
    String twitter;
    String social;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Speaker speaker = (Speaker) o;
        return Objects.equals(getId(), speaker.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
