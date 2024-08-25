package com.example.artgallery.repository;

import java.util.*;
import com.example.artgallery.model.*;

public interface ArtistRepository {

    public ArrayList<Artist> getArtists();

    public Artist getArtistById(int artistId);

    public Artist addArtist(Artist artist);

    public Artist updateArtist(int artId, Artist artist);

    public void deleteArtist(int artistId);

    public List<Gallery> getArtistsGalleries(int artistId);

    public List<Art> getArtistArts(int artistId);
}