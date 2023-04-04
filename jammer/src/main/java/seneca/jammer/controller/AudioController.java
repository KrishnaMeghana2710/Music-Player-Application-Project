package seneca.jammer.controller;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
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
@Component
@RequestMapping(path="/api/songs")
public class AudioController {

    @Autowired
    private AudioService audioService;

    
    private final songRepository repo;
    
        
    //this will return all the song objects as a list
    @GetMapping("/getSongs")
    public ResponseEntity<List<song>> getSongs(){
        return ResponseEntity.ok(repo.findAll());
        }

    //delete a song from the cloud as well as from the database
    @DeleteMapping("/deleteSong")
    public String delete(@RequestParam("model") String model) {
        ObjectMapper mapper = new ObjectMapper();
        song toBeDeleted;
        try {
            toBeDeleted = mapper.readValue(model, song.class);
            String delFile= toBeDeleted.getFilename();
            audioService.deleteFile(delFile);
            String id = toBeDeleted.getId();
            repo.deleteById(id);
            return "File deleted";
        } catch (JsonProcessingException e) {
            
            e.printStackTrace();
        }
        return "File not deleted";

    }


    //takes input from the user and uploades it into the cloud as well as the database
    @PostMapping("/uploadSong")
    public String createSong(@RequestParam("model") String model, @RequestParam(value = "file", required = false) MultipartFile file){
        ObjectMapper mapper = new ObjectMapper();
    try {
        songInfo modelDTO = mapper.readValue(model, songInfo.class);
        audioService.uploadAudio(file, modelDTO);
        return "File Uploaded";
    } catch (JsonMappingException e) {
        
        e.printStackTrace();
    } catch (JsonProcessingException e) {
        
        e.printStackTrace();
    }
        
        return "File not-uploaded";
    }
}
