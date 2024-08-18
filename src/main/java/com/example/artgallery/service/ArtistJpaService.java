package com.example.artgallery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

import com.example.artgallery.repository.*;
import com.example.artgallery.model.*;

@Service
public class ArtistJpaService implements ArtistRepository {
	@Autowired
	public ArtJpaRepository artJpaRepository;

	@Autowired
	public ArtistJpaRepository artistJpaRepository;

	@Autowired
	public GalleryJpaRepository galleryJpaRepository;

	@Override
	public ArrayList<Artist> getArtists() {
		List<Artist> artists = artistJpaRepository.findAll();
		ArrayList<Artist> allArtists = new ArrayList<>(artists);
		return allArtists;
	}

	@Override
	public Artist getArtistById(int artistId) {
		try {
			Artist artist = artistJpaRepository.findById(artistId).get();
			return artist;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public Artist addArtist(Artist artist) {
		try {
			ArrayList<Integer> galleryids = new ArrayList<>();

			ArrayList<Gallery> galleries = artist.getGalleries();
			for (Gallery gallery : galleries) {
				int id = gallery.getGalleryId();
				galleryids.add(id);
			}

			ArrayList<Gallery> galleriesdetails = (ArrayList<Gallery>) galleryJpaRepository.findAllById(galleryids);

			if (galleryids.size() != galleriesdetails.size()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}

			artist.setGalleries(galleriesdetails);
			artistJpaRepository.save(artist);
			return artist;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Artist updateArtist(int artistId, Artist artist) {
		try {
			Artist oldartist = artistJpaRepository.findById(artistId).get();
			if (artist.getArtistName() != null) {
				oldartist.setArtistName(artist.getArtistName());
			}
			if (artist.getGenre() != null) {
				oldartist.setGenre(artist.getGenre());
			}

			if (artist.getGalleries() != null) {
				ArrayList<Integer> galleryids = new ArrayList<>();
				ArrayList<Gallery> galleries = artist.getGalleries();
				for (Gallery gallery : galleries) {
					int id = gallery.getGalleryId();
					galleryids.add(id);
				}

				ArrayList<Gallery> galleriesdetails = (ArrayList<Gallery>) galleryJpaRepository.findAllById(galleryids);

				if (galleryids.size() != galleriesdetails.size()) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
				}
				oldartist.setGalleries(galleriesdetails);

			}
			artistJpaRepository.save(oldartist);
			return oldartist;
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void deleteArtist(int artistId) {
		try {
			artistJpaRepository.deleteById(artistId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		throw new ResponseStatusException(HttpStatus.NO_CONTENT);
	}

	@Override
	public ArrayList<Gallery> getArtistGalleries(int artistId) {
		try {
			Artist artist = artistJpaRepository.findById(artistId).get();
			ArrayList<Gallery> galleries = artist.getGalleries();
			return galleries;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public List<Art> getArtistArts(int artistId) {
		try {
			Artist artist = artistJpaRepository.findById(artistId).get();
			return artJpaRepository.findByArtist(artist);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

}
