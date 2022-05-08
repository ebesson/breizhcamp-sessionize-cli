package org.breizhcamp.sessionize.model.io.cfp;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.*;

//import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Proposal {


    public static final List<String> AUTHORIZED_SORTS = Arrays.asList("state", "name", "language", "tracklabel", "difficulty", "added");

    public enum State { DRAFT, CONFIRMED, PRESENT, ACCEPTED, REFUSED, BACKUP }

    private int id;
    private State state;
    @NotNull(message = "Session name field is required")
    private String name;
    private String language;
    private String eventId;
    private Integer trackId;
    private String trackLabel;
    private String description;
    private String references;
    private Integer difficulty;
    private Date added;
    private Integer format;
    private String formatName;
    private User speaker;

    private Date schedule;
    private String scheduleHour;
    private Integer roomId;

    private Set<User> cospeakers = new HashSet<>();

    private String video;
    private String slides;

    private List<String> voteUsersEmail;
    private String mean;


    @JsonIgnore
    public Set<String> getSpeakersIds() {
        Set<String> speakersIds = new HashSet<>();
        speakersIds.add(this.getSpeaker().getId());
        //speakersIds.addAll(this.getCospeakers().stream().map(User::getId).collect(Collectors.toSet()));
        return speakersIds;
    }
}
