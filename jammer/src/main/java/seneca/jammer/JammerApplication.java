package seneca.jammer;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import seneca.jammer.repository.songRepository;
@SpringBootApplication
public class JammerApplication {
	//@Autowired
	//private static songRepository repo;
		public static void main(String[] args) {
		SpringApplication.run(JammerApplication.class, args);
	}
}
