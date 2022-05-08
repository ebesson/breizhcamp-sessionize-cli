package org.breizhcamp.sessionize.model.sessionize.session;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Speaker {

    String id;
    String name;
}
