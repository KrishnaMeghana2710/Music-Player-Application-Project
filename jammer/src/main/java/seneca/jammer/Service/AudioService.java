package seneca.jammer.Service;
import seneca.jammer.model.*;
import seneca.jammer.repository.songRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AudioService {
     
    
    private final songRepository repository;
    
    private final S3service s3service;

    public void uploadAudio(MultipartFile file, songInfo info){
        //upload file to aws s3
        //the s3service.uploadfile will return the audiourl 
        String audioUrl = s3service.uploadFile(file);

        //create an object of type song
        var song = new song();

        //setting the details of the song
        song.setAudioUrl(audioUrl);
        song.setFilename(file.getOriginalFilename());
        
        song.setSongName(info.getSongname());
        song.setArtistName(info.getArtistName());

        //store file data in DB
        repository.save(song);
        
      }
      public void deleteFile(String fileName){
        s3service.deleteFile(fileName);
      }
}
