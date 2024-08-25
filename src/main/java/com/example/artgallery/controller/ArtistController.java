package com.example.artgallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import org.springframework.web.bind.annotation.*;

import com.example.artgallery.service.*;
import com.example.artgallery.model.*;
import com.example.artgallery.service.*;

@RestController
public class ArtistController {

	@Autowired
	private ArtJpaService artJpaService;

	@Autowired
	private ArtistJpaService artistJpaService;

	@Autowired
	private GalleryJpaService galleryJpaService;

	@GetMapping("/galleries/artists")
	public ArrayList<Artist> getArtists() {
		return artistJpaService.getArtists();
	}

	@GetMapping("/galleries/artists/{artistId}")
	public Artist getArtistById(@PathVariable("artistId") int artistId) {
		return artistJpaService.getArtistById(artistId);
	}

	@PostMapping("/galleries/artists")
	public Artist addArtist(@RequestBody Artist artist) {
		return artistJpaService.addArtist(artist);
	}

	@PutMapping("/galleries/artists/{artistId}")
	public Artist updateArtist(@PathVariable("artistId") int artistId, @RequestBody Artist artist) {
		return artistJpaService.updateArtist(artistId, artist);
	}

	@DeleteMapping("/galleries/artists/{artistId}")
	public void deleteArtist(@PathVariable("artistId") int artistId) {
		artistJpaService.deleteArtist(artistId);
	}

	@GetMapping("/artists/{artistId}/galleries")
	public List<Gallery> getArtistsGalleries(@PathVariable("artistId") int artistId) {
		return artistJpaService.getArtistsGalleries(artistId);
	}

	@GetMapping("/artists/{artistId}/arts")
	public List<Art> getArtistArts(@PathVariable("artistId") int artistId) {
		return artistJpaService.getArtistArts(artistId);
	}
}
