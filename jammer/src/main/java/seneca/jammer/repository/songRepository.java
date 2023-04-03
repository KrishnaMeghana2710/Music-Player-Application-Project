package seneca.jammer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import seneca.jammer.model.song;
import java.util.*;

public interface songRepository extends MongoRepository<song, String>{

    Optional<song> findBySongName(String songName);

}
