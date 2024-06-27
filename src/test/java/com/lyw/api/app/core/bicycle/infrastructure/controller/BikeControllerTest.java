package com.lyw.api.app.core.bicycle.infrastructure.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.lyw.api.app.core.bicycle.application.services.BicycleCommandService;
import com.lyw.api.app.core.bicycle.application.services.BicycleQueryService;
import com.lyw.api.app.core.bicycle.domain.model.Bicycle;
import com.lyw.api.app.core.bicycle.domain.queries.GetBicycleByIdQuery;
import com.lyw.api.app.core.bicycle.infrastructure.dto.BicycleResponseDto;
import com.lyw.api.app.core.bicycle.infrastructure.mapper.BicycleMapper;

@ExtendWith(MockitoExtension.class)
class BikeControllerTest {

	@Mock
	private BicycleQueryService bicycleQueryService;

	@Mock
	private BicycleCommandService bicycleCommandService;

	@Mock
	private BicycleMapper bicycleMapper;

	@InjectMocks
	private BikeController bikeController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetBicycleById() {
		// ! Configuration
		Long bicycleId = 1L;
		Bicycle bicycle = new Bicycle(
				1L,
				"bikeName",
				"bikeDescription",
				100.0,
				"bikeSize",
				"bikeModel",
				"imageData",
				null,
				null,
				null,
				null);
		BicycleResponseDto expectedResponse = new BicycleResponseDto();

		// ! Mock
		when(bicycleQueryService.handle(new GetBicycleByIdQuery(bicycleId)))
				.thenReturn(bicycle);
		when(bicycleMapper.toResponseDto(bicycle)).thenReturn(expectedResponse);

		// ! Execution
		ResponseEntity<BicycleResponseDto> response = bikeController.getBicycleById(bicycleId);

		// ! Verification
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedResponse, response.getBody());
		verify(bicycleQueryService, times(1)).handle(any(GetBicycleByIdQuery.class));
		verify(bicycleMapper, times(1)).toResponseDto(bicycle);
	}

	@Test
	void testCreateBicycle() {
		fail("Not yet implemented");
	}

}
