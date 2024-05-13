package org.breizhcamp.sessionize.model.website;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@JsonPropertyOrder({ "id", "name", "event_start", "event_end", "event_type", "format",
        "venue", "venue_id", "speakers", "video_url", "files_url", "slides_url", "description" })
public class Session {

    String id;
    String name;

    @JsonProperty("event_start")
    String eventStart;

    @JsonProperty("event_end")
    String eventEnd;

    @JsonProperty("event_type")
    String eventType;

    String format;
    String venue;
    String venue_id;
    String speakers;
    String video_url;
    String files_url;
    String slides_url;
    String description;

}
