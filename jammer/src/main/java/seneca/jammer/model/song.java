package seneca.jammer.model;


import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "song")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class song{

    @MongoId
    private String id;
    private String filename;
    private String audioUrl;
    private String songName;
    private String artistName;
    
    
}
