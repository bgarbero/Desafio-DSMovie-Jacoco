package com.devsuperior.dsmovie.services;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.dto.ScoreDTO;
import com.devsuperior.dsmovie.entities.MovieEntity;
import com.devsuperior.dsmovie.entities.ScoreEntity;
import com.devsuperior.dsmovie.entities.UserEntity;
import com.devsuperior.dsmovie.projections.UserDetailsProjection;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.repositories.ScoreRepository;
import com.devsuperior.dsmovie.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dsmovie.tests.MovieFactory;
import com.devsuperior.dsmovie.tests.ScoreFactory;
import com.devsuperior.dsmovie.tests.UserDetailsFactory;
import com.devsuperior.dsmovie.tests.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ScoreServiceTests {
	
	@InjectMocks
	private ScoreService service;

	@Mock
	private UserService userService;

	@Mock
	private ScoreRepository scoreRepository;

	@Mock
	private MovieRepository movieRepository;

	private UserEntity user;
	private MovieDTO movieDTO;
	private Long existingId, nonExistingId;
	private MovieEntity movie;
	private ScoreEntity score;
	private ScoreDTO scoreDTO;

	@BeforeEach
	public void setup() throws Exception{
		MockitoAnnotations.openMocks(this);

		score = ScoreFactory.createScoreEntity();

		scoreDTO = ScoreFactory.createScoreDTO();

		movie = MovieFactory.createMovieEntity();

		movieDTO = MovieFactory.createMovieDTO();

		user = UserFactory.createUserEntity();

		existingId = 1L;
		nonExistingId = 2L;

		Mockito.when(userService.authenticated()).thenReturn(user);

		Mockito.when(movieRepository.findById(existingId)).thenReturn(Optional.of(movie));
		Mockito.when(movieRepository.findById(nonExistingId)).thenReturn(Optional.empty());
		Mockito.when(movieRepository.save(movie)).thenReturn(movie);

		Mockito.when(scoreRepository.saveAndFlush(score)).thenReturn(score);

	}
	
	@Test
	public void saveScoreShouldReturnMovieDTO() {
		MovieDTO result = service.saveScore(scoreDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), movieDTO.getId());
		Assertions.assertEquals(movieDTO.getTitle(), result.getTitle());
	}
	
	@Test
	public void saveScoreShouldThrowResourceNotFoundExceptionWhenNonExistingMovieId() {
		ScoreEntity score = ScoreFactory.createScoreEntity();
		ScoreDTO scoreDTO = new ScoreDTO(nonExistingId, score.getValue());

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.saveScore(scoreDTO);
		});
	}
}
