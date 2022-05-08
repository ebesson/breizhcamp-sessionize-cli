package org.breizhcamp.sessionize.model.sessionize.session;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Sessions {

    public Integer groupId;
    public String groupName;
    public List<Session> sessions;
}
