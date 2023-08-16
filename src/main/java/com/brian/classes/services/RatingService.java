package com.brian.classes.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.brian.classes.models.Rating;
import com.brian.classes.repositories.RatingRepository;

@Service
public class RatingService {
	private final RatingRepository rRepo;
	public RatingService(RatingRepository r) {
		this.rRepo = r;
	}
	public List<Rating> allRatings() {
		return rRepo.findAll();
	}
	
	public Rating createRating(Rating r) {
		return rRepo.save(r);
	}
	public Rating findRating(Long id) {
		Optional<Rating> optionalRating = rRepo.findById(id);
		if(optionalRating.isPresent()) {
			return optionalRating.get();
		}
		else {
			return null;
		}
	}
//	public List<Rating> getAverageRatings() {
//		return rRepo.averageRatings();
//	}
}
