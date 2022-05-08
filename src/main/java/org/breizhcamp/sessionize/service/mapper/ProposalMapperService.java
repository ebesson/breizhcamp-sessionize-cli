package org.breizhcamp.sessionize.service.mapper;

import org.breizhcamp.sessionize.model.io.cfp.Proposal;
import org.breizhcamp.sessionize.model.io.cfp.User;
import org.breizhcamp.sessionize.model.sessionize.Constants;
import org.breizhcamp.sessionize.model.sessionize.session.*;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProposalMapperService {


    public static final int SPEAKER_INDEX_FIRSTNAME = 0;
    public static final int SPEARKER_INDEX_LASTNAME = 1;
    public List<Proposal> mapFromSessionizeTrack(Sessions sessions){
        List<Proposal> proposals = new ArrayList<>();
        for(Session session : sessions.getSessions()){

            Proposal proposal = new Proposal();
            proposal.setId(Integer.valueOf(session.getId()));
            proposal.setName(session.getTitle());
            proposal.setDescription(session.getDescription());

            CategoryItem format = session.getCategories().stream().
                    filter(category -> Constants.FORMAT.equals(category.getName())).
                    findFirst().
                    get().
                    getCategoryItems().get(0);
            proposal.setFormatName(format.getName());
            proposal.setFormat(format.getId());

            CategoryItem track = session.getCategories().stream().
                    filter(category -> Constants.THEME.equals(category.getName())).
                    findFirst().
                    get().
                    getCategoryItems().get(0);
            proposal.setTrackId(track.getId());
            proposal.setTrackLabel(track.getName());

            List<Speaker> speakers = session.getSpeakers();

            User user = mapUserFromSpeaker(speakers.get(0));
            proposal.setSpeaker(user);
            speakers.remove(0);

            // Cospeakers
            for (Speaker speaker : speakers){
                proposal.getCospeakers().add(mapUserFromSpeaker(speaker));
            }

            proposals.add(proposal);
        }
        return proposals;
    }

    @NotNull
    private User mapUserFromSpeaker(Speaker speaker) {
        User user = new User();
        user.setId(speaker.getId());
        user.setFirstname(speaker.getName().split(" ")[SPEAKER_INDEX_FIRSTNAME]);
        user.setLastname(speaker.getName().split(" ")[SPEARKER_INDEX_LASTNAME]);
        return user;
    }

}
