package com.example.artgallery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

import com.example.artgallery.repository.*;
import com.example.artgallery.model.*;

@Service
public class ArtJpaService implements ArtRepository {
	@Autowired
	public ArtJpaRepository artJpaRepository;
	@Autowired
	public ArtistJpaRepository artistJpaRepository;

	@Override
	public ArrayList<Art> getArts() {
		List<Art> arts = artJpaRepository.findAll();
		ArrayList<Art> allarts = new ArrayList<>(arts);
		return allarts;
	}

	@Override
	public Art getArtById(int artId) {
		try {
			Art art = artJpaRepository.findById(artId).get();
			return art;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public Art addArt(Art art) {
		try {
			Artist artist = art.getArtist();
			int artistId = artist.getArtistId();
			Artist artist1 = artistJpaRepository.findById(artistId).get();
			art.setArtist(artist1);
			return artJpaRepository.save(art);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Art updateArt(int artId, Art art) {
		try {
			Art oldart = artJpaRepository.findById(artId).get();
			if (art.getArtTitle() != null) {
				oldart.setArtTitle(art.getArtTitle());
			}
			if (art.getTheme() != null) {
				oldart.setTheme(art.getTheme());
			}
			if (art.getArtist() != null) {
				Artist artist = art.getArtist();
				int artistId = artist.getArtistId();
				Artist artist1 = artistJpaRepository.findById(artistId).get();
				oldart.setArtist(artist1);
				artJpaRepository.save(oldart);

			}
			return oldart;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void deleteArt(int artId) {
		try {
			artJpaRepository.deleteById(artId);
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Artist getArtArtist(int artId) {
		try {
			Art art = artJpaRepository.findById(artId).get();
			Artist artist = art.getArtist();
			return artist;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

}
