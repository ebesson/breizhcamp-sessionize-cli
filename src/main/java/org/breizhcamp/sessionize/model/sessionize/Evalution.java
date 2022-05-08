package org.breizhcamp.sessionize.model.sessionize;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Evalution {
    Integer rank;
    String sessionId;
    String session;
    String averageRating;
    String strongOpinions;
}

