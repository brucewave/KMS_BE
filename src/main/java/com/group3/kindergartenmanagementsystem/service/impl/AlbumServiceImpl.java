package com.group3.kindergartenmanagementsystem.service.impl;

import com.group3.kindergartenmanagementsystem.exception.ForbiddenAccessException;
import com.group3.kindergartenmanagementsystem.exception.ResourceNotFoundException;
import com.group3.kindergartenmanagementsystem.model.Album;
import com.group3.kindergartenmanagementsystem.model.Child;
import com.group3.kindergartenmanagementsystem.payload.AddAlbumDTO;
import com.group3.kindergartenmanagementsystem.payload.AlbumDTO;
import com.group3.kindergartenmanagementsystem.repository.AlbumRepository;
import com.group3.kindergartenmanagementsystem.repository.ChildRepository;
import com.group3.kindergartenmanagementsystem.service.AlbumService;
import com.group3.kindergartenmanagementsystem.service.FileStorageService;
import com.group3.kindergartenmanagementsystem.service.SecurityService;
import com.group3.kindergartenmanagementsystem.utils.AppConstants;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    AlbumRepository albumRepository;
    ChildRepository childRepository;
    FileStorageService fileStorageService;
    SecurityService securityService;
    ModelMapper mapper;

    @Override
    public List<AlbumDTO> getAllPicture() {
        return albumRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public AlbumDTO getPictureById(Integer id) {
        Album album = albumRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Album", "id", id));
        if (!securityService.isParentOrTeacherOfChild(album.getChild()))
            throw new ForbiddenAccessException(HttpStatus.BAD_REQUEST,
                    "User with username: "+securityService.getUsername()+" can't access to this picture");
        return mapToDTO(album);
    }

    @Override
    public List<AlbumDTO> getAlbumByChildId(Integer childId) {
        Child child = childRepository.findById(childId)
                .orElseThrow(()-> new ResourceNotFoundException("Child", "id", childId));
        List<Album> albums = albumRepository.findByChild(child);
        if (!albums.isEmpty())
            if (!securityService.isParentOrTeacherOfChild(albums.get(0).getChild()))
                throw new ForbiddenAccessException(HttpStatus.BAD_REQUEST,
                    "User with username: "+securityService.getUsername()+" can't access to this albums");
        return albums.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public AlbumDTO createNewPicture(AddAlbumDTO addAlbumDTO) {
        Child child = childRepository.findById(addAlbumDTO.getChildId()).orElseThrow(
                () ->new ResourceNotFoundException("Child", "id", addAlbumDTO.getChildId()));
        if (!securityService.isParentOrTeacherOfChild(child))
            throw new ForbiddenAccessException(HttpStatus.BAD_REQUEST,
                    "User with username: "+securityService.getUsername()+" can't access to this child album");
        Album album = Album.builder()
                .child(child)
                .postedTime(LocalDateTime.now())
                .build();
        String fileName = fileStorageService.save(addAlbumDTO.getImage());
        album.setImage(fileName);
        Album createdAlbum = albumRepository.save(album);
        return mapToDTO(createdAlbum);
    }

    @Override
    public AlbumDTO updatePictureById(Integer id, AlbumDTO albumDTO) {
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deletePictureById(Integer id) {
        Album album = albumRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Album", "id", id));
        if (!securityService.isParentOrTeacherOfChild(album.getChild()))
            throw new ForbiddenAccessException(HttpStatus.BAD_REQUEST,
                    "User with username: "+securityService.getUsername()+" can't access to this picture");
        String fileName = album.getImage();
        try{
            Files.move(Paths.get(AppConstants.UPLOAD_FOLDER).resolve(fileName),
                    Paths.get(AppConstants.BIN_FOLDER).resolve(fileName));
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        albumRepository.delete(album);
    }

    private AlbumDTO mapToDTO(Album album){
        return AlbumDTO.builder()
                .id(album.getId())
                .image(album.getImage())
                .postedTime(album.getPostedTime())
                .childId(album.getChild().getId())
                .build();
    }

    private Album mapToEntity(AlbumDTO albumDTO){
        Child child = childRepository.findById(albumDTO.getChildId()).orElseThrow(
                ()-> new ResourceNotFoundException("Child", "id", albumDTO.getChildId()));
        Album album = mapper.map(albumDTO, Album.class);
        album.setChild(child);
        return album;
    }
}
