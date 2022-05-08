package org.breizhcamp.sessionize.model.sessionize.all;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Link {

    String title;
    String url;
    String linkType;
}
