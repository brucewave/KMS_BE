package com.group3.kindergartenmanagementsystem.controller;

import com.group3.kindergartenmanagementsystem.payload.AddAlbumDTO;
import com.group3.kindergartenmanagementsystem.payload.AlbumDTO;
import com.group3.kindergartenmanagementsystem.service.AlbumService;
import com.group3.kindergartenmanagementsystem.service.FileStorageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

@RestController
@RequestMapping("api/albums")
@AllArgsConstructor
public class AlbumController {
    AlbumService albumService;
    @GetMapping
    public ResponseEntity<List<AlbumDTO>> getAllPictures(){
        return ResponseEntity.ok(albumService.getAllPicture());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PARENT', 'TEACHER')")
    public ResponseEntity<AlbumDTO> getPictureById(@PathVariable Integer id){
        return ResponseEntity.ok(albumService.getPictureById(id));
    }

    @GetMapping("/child/{id}")
    @PreAuthorize("hasAnyRole('PARENT', 'TEACHER')")
    public ResponseEntity<List<AlbumDTO>> getAlbumByChildId(@PathVariable(value = "id") Integer childId){
        return ResponseEntity.ok(albumService.getAlbumByChildId(childId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('PARENT', 'TEACHER')")
    public ResponseEntity<AlbumDTO> createNewAlbum(@ModelAttribute AddAlbumDTO addAlbumDTO){
        return new ResponseEntity<>(albumService.createNewPicture(addAlbumDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('PARENT', 'TEACHER', 'ADMIN')")
    public ResponseEntity<String> deletePictureById(@PathVariable Integer id){
        albumService.deletePictureById(id);
        return ResponseEntity.ok("Delete successfully");
    }
}
