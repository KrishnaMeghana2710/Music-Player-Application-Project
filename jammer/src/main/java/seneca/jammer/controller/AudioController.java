package seneca.jammer.controller;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import seneca.jammer.Service.AudioService;
import seneca.jammer.model.song;
import seneca.jammer.repository.songRepository;
import seneca.jammer.model.songInfo;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/api/songs")
public class AudioController {

    @Autowired
    private AudioService audioService;

    
    private final songRepository repo;
    
        
    //this will return all the songs as a list

    @GetMapping("/getSongs")
    public ResponseEntity<List<song>> getSongs(){
        return ResponseEntity.ok(repo.findAll());
        }

    @DeleteMapping("/deleteSong")
    public String delete(@RequestParam("model") String model) {
        ObjectMapper mapper = new ObjectMapper();
        song temp;
        try {
            temp = mapper.readValue(model, song.class);
            String delFile= temp.getFilename();
            audioService.deleteFile(delFile);
            String id = temp.getId();
            repo.deleteById(id);

            return "File deleted";
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "File not deleted";

    }


    @PostMapping("/uploadSong")
    public String createString(@RequestParam("model") String model, @RequestParam(value = "file", required = false) MultipartFile file){
        ObjectMapper mapper = new ObjectMapper();
    try {
        songInfo modelDTO = mapper.readValue(model, songInfo.class);
        audioService.uploadAudio(file, modelDTO);
        return "File Upload";
    } catch (JsonMappingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (JsonProcessingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
        
        return "File not-uploaded";
    }
}
