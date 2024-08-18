package com.example.artgallery.repository;

import java.util.*;
import com.example.artgallery.model.*;

public interface ArtRepository {

    public ArrayList<Art> getArts();

    public Art getArtById(int artId);

    public Art addArt(Art art);

    public Art updateArt(int artId, Art art);

    public void deleteArt(int artId);

    public Artist getArtArtist(int artId);

}