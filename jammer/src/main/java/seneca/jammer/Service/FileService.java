package seneca.jammer.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {
    public String uploadFile(MultipartFile file);
    public String deleteFile(String filename);
}
