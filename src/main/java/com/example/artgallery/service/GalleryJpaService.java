package com.example.artgallery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

import com.example.artgallery.repository.*;
import com.example.artgallery.model.*;

@Service
public class GalleryJpaService implements GalleryRepository {
	@Autowired
	public ArtJpaRepository artJpaRepository;

	@Autowired
	public ArtistJpaRepository artistJpaRepository;

	@Autowired
	public GalleryJpaRepository galleryJpaRepository;

	@Override
	public ArrayList<Gallery> getGalleries() {
		List<Gallery> gallerys = galleryJpaRepository.findAll();
		ArrayList<Gallery> allgalleries = new ArrayList<>(gallerys);
		return allgalleries;
	}

	@Override
	public Gallery getGalleryById(int galleryId) {
		try {
			Gallery gallery = galleryJpaRepository.findById(galleryId).get();
			return gallery;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public Gallery addGallery(Gallery gallery) {
		ArrayList<Integer> artistsids = new ArrayList<>();
		List<Artist> artists = gallery.getArtists();
		for (Artist artist : artists) {
			int id = artist.getArtistId();
			artistsids.add(id);
		}
		ArrayList<Artist> allartists = (ArrayList<Artist>) artistJpaRepository.findAllById(artistsids);

		gallery.setArtists(allartists);

		for (Artist artist : allartists) {
			artist.getGalleries().add(gallery);
		}

		artistJpaRepository.saveAll(allartists);
		galleryJpaRepository.save(gallery);

		return gallery;
	}

	@Override
	public Gallery updateGallery(int galleryId, Gallery gallery) {
		try {
			Gallery oldGallery = galleryJpaRepository.findById(galleryId).get();
			if (gallery.getGalleryName() != null) {
				oldGallery.setGalleryName(gallery.getGalleryName());
			}
			if (gallery.getLocation() != null) {
				oldGallery.setLocation(gallery.getLocation());
			}

			if (gallery.getArtists() != null) {
				List<Artist> artists = oldGallery.getArtists();
				for (Artist artist : artists) {
					artist.getGalleries().remove(oldGallery);
				}
				ArrayList<Integer> artistsids = new ArrayList<>();
				List<Artist> artistss = gallery.getArtists();
				for (Artist artist : artistss) {
					int id = artist.getArtistId();
					artistsids.add(id);
				}
				ArrayList<Artist> allartists = (ArrayList<Artist>) artistJpaRepository.findAllById(artistsids);

				gallery.setArtists(allartists);

				for (Artist artist : allartists) {
					artist.getGalleries().add(gallery);
				}

				artistJpaRepository.saveAll(allartists);
				galleryJpaRepository.save(gallery);

			}
		}

		catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return gallery;

	}

	@Override
	public void deleteGallery(int galleryId) {
		try {
			Gallery gallery = galleryJpaRepository.findById(galleryId).get();
			List<Artist> artists = gallery.getArtists();
			for (Artist artist : artists) {
				artist.getGalleries().remove(gallery);
			}
			galleryJpaRepository.deleteById(galleryId);
			artistJpaRepository.saveAll(artists);
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	public List<Artist> getGalleryArtists(int galleryId) {
		try {
			Gallery gallery = galleryJpaRepository.findById(galleryId).get();
			List<Artist> artists = gallery.getArtists();
			return artists;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
}
