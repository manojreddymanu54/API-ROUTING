package com.example.artgallery.repository;

import java.util.*;
import com.example.artgallery.model.*;

public interface GalleryRepository {

    public ArrayList<Gallery> getGalleries();

    public Gallery getGalleryById(int galleryId);

    public Gallery addGallery(Gallery gallery);

    public Gallery updateGallery(int galleryId, Gallery gallery);

    public void deleteGallery(int galleryId);

    public List<Artist> getGalleryArtists(int galleryId);

}