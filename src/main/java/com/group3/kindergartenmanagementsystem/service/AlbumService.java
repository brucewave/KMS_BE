package com.group3.kindergartenmanagementsystem.service;

import com.group3.kindergartenmanagementsystem.payload.AddAlbumDTO;
import com.group3.kindergartenmanagementsystem.payload.AlbumDTO;

import java.util.List;

public interface AlbumService {
    List<AlbumDTO> getAllPicture();
    AlbumDTO getPictureById(Integer id);
    List<AlbumDTO> getAlbumByChildId(Integer childId);
    AlbumDTO createNewPicture(AddAlbumDTO albumDTO);
    AlbumDTO updatePictureById(Integer id, AlbumDTO albumDTO);
    void deletePictureById(Integer id);
}
