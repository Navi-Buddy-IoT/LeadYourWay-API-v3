package com.lyw.api.app.core.bicycle.application.implementation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lyw.api.app.assets.application.services.TemperatureCommandService;
import com.lyw.api.app.assets.application.services.VelocityCommandService;
import com.lyw.api.app.assets.domain.commands.CreateTemperatureCommand;
import com.lyw.api.app.assets.domain.commands.CreateVelocityCommand;
import com.lyw.api.app.assets.domain.commands.PatchTemperatureCommand;
import com.lyw.api.app.assets.domain.model.Temperature;
import com.lyw.api.app.assets.domain.model.Velocity;
import com.lyw.api.app.assets.infrastructure.dto.TemperatureRequestDto;
import com.lyw.api.app.core.bicycle.domain.commands.CreateBicycleCommand;
import com.lyw.api.app.core.bicycle.domain.commands.DeleteBicycleCommand;
import com.lyw.api.app.core.bicycle.domain.commands.PatchBicycleTemperatureCommand;
import com.lyw.api.app.core.bicycle.domain.model.Bicycle;
import com.lyw.api.app.core.bicycle.domain.repositories.BicycleRepository;
import com.lyw.api.app.core.bicycle.infrastructure.dto.BicycleRequestDto;
import com.lyw.api.app.iam.identity.domain.model.User;
import com.lyw.api.app.iam.identity.domain.repositories.UserRepository;
import com.lyw.api.app.shared.utils.ValidationUtil;

@ExtendWith(MockitoExtension.class)
class BicycleCommandServiceImplTest {
	@InjectMocks
	private BicycleCommandServiceImpl bicycleCommandServiceImpl;

	@Mock
	private BicycleRepository bicycleRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private ValidationUtil validationUtil;

	@Mock
	private TemperatureCommandService temperatureService;

	@Mock
	private VelocityCommandService velocityService;

	@Test
	void testHandleCreateBicycleCommand() {
		// ! Configuration
		BicycleRequestDto bicycleRequestDto = new BicycleRequestDto(
				"bicycleName",
				"bicycleDescription",
				100.0,
				"bicycleSize",
				"bicycleModel",
				"imageData");
		CreateBicycleCommand command = new CreateBicycleCommand("ichW0iwUKzd8exoe9BQoprJlJi93", bicycleRequestDto);
		User user = new User();
		user.setId("ichW0iwUKzd8exoe9BQoprJlJi93");
		Bicycle expectedBicycle = new Bicycle(
				1L,
				"bicycleName",
				"bicycleDescription",
				100.0,
				"bicycleSize",
				"bicycleModel",
				"imageData",
				user,
				null,
				null,
				null);

		// ! Mock
		Mockito.when(validationUtil.findUserById("ichW0iwUKzd8exoe9BQoprJlJi93")).thenReturn(user);
		Mockito.when(bicycleRepository.save(Mockito.any(Bicycle.class))).thenReturn(expectedBicycle);
		Mockito.when(temperatureService.handle(Mockito.any(CreateTemperatureCommand.class)))
				.thenReturn(new Temperature());
		Mockito.when(velocityService.handle(Mockito.any(CreateVelocityCommand.class))).thenReturn(new Velocity());

		// ! Execution
		Bicycle actualBicycle = bicycleCommandServiceImpl.handle(command);

		// ! Verification
		assertEquals(expectedBicycle, actualBicycle);
		Mockito.verify(validationUtil, Mockito.times(1)).findUserById("ichW0iwUKzd8exoe9BQoprJlJi93");
		Mockito.verify(bicycleRepository, Mockito.times(1)).save(Mockito.any());
	}

	@Test
	void testHandleDeleteBicycleCommand() {
		// ! Configuration
		Long bicycleId = 1L;
		DeleteBicycleCommand command = new DeleteBicycleCommand(bicycleId);
		Bicycle bicycle = new Bicycle();
		bicycle.setId(bicycleId);

		// ! Mock
		Mockito.when(validationUtil.findBicycleById(bicycleId)).thenReturn(bicycle);

		// ! Execution
		bicycleCommandServiceImpl.handle(command);

		// ! Verification
		Mockito.verify(validationUtil, Mockito.times(1)).findBicycleById(bicycleId);
		Mockito.verify(bicycleRepository, Mockito.times(1)).delete(bicycle);
	}

	@Test
	void testHandlePatchBicycleTemperatureCommand() {
		// ! Configuration
		TemperatureRequestDto temperatureRequestDto = new TemperatureRequestDto();
		temperatureRequestDto.setBicycleId(1L);
		temperatureRequestDto.setTemperature(30.0);
		PatchBicycleTemperatureCommand command = new PatchBicycleTemperatureCommand(temperatureRequestDto);

		Bicycle bicycle = new Bicycle();
		Temperature existingTemperature = new Temperature();
		existingTemperature.setId(1L);
		bicycle.setTemperature(existingTemperature);

		Temperature updatedTemperature = new Temperature();
		updatedTemperature.setId(1L);
		updatedTemperature.setTemperature(30.0);

		// ! Mock
		Mockito.when(validationUtil.findBicycleById(1L)).thenReturn(bicycle);
		Mockito.when(temperatureService.handle(new PatchTemperatureCommand(30.0, 1L))).thenReturn(updatedTemperature);

		// ! Execution
		Temperature actualTemperature = bicycleCommandServiceImpl.handle(command);

		// ! Verification
		assertEquals(updatedTemperature, actualTemperature);
		Mockito.verify(validationUtil, Mockito.times(1)).findBicycleById(1L);
		Mockito.verify(temperatureService, Mockito.times(1)).handle(Mockito.any(PatchTemperatureCommand.class));
	}

}
