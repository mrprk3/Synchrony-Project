package com.synchrony.user.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.synchrony.user.jwt.JwtUtil;
import com.synchrony.user.model.AuthenticationData;
import com.synchrony.user.model.Response;
import com.synchrony.user.model.ResponseData;
import com.synchrony.user.model.User;
import com.synchrony.user.model.UserConstant;
import com.synchrony.user.repository.UserRepository;
import com.synchrony.user.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	JwtUtil jwtUtil;

	private static Logger logger = LogManager.getLogger(UserController.class);
	// Register a User with basic information, username, and password with image

	@PostMapping("/userRegistration")
	public ResponseEntity<Response> saveUser(@RequestPart("userRegistrationData") User userRegistrationData,
			@RequestPart("userPhoto") MultipartFile userPhoto,
			@RequestHeader(value = "Authorization", required = true) String authorization) throws IOException {

		StringBuilder log = new StringBuilder();
		Response res = new Response();
		User user = new User();
		user.setUserId(userRegistrationData.getUserId());
		user.setFirstName(userRegistrationData.getFirstName());
		user.setLastName(userRegistrationData.getLastName());
		user.setEmail(userRegistrationData.getEmail());
		user.setPhone(userRegistrationData.getPhone());
		user.setUsername(userRegistrationData.getUsername());
		user.setPassword(userRegistrationData.getPassword());
		user.setStatus(userRegistrationData.getStatus());
		if (!userPhoto.isEmpty()) {
			user.setUserPhoto(userPhoto.getBytes());
		}
		logger.info(log.append("Entering :: User Controller :: user-registration"));
		userService.save(user);
		res.setErrorCode(UserConstant.USER_CREATE_SUCCESS_CODE);
		res.setErrorMessage(UserConstant.USER_CREATE_SUCCESS_MESSAGE);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	// view images after authorizing the username/password.
	@PostMapping("/viewUserPhoto")
	public ResponseEntity<Response> getUserPhotoBasedOnUserId(@RequestBody ResponseData responseData,
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		StringBuilder log = new StringBuilder();
		Response res = new Response();
		User resultSet = userService.userAuthenticate(responseData.getUsername(), responseData.getPassword());
		if (resultSet == null) {
			res.setErrorCode(UserConstant.ERROR_CODE_01);
			res.setErrorMessage(UserConstant.ERROR_LU01_MESSAGE);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		} else {
			logger.info(log.append("Entering :: User Controller :: viewUserPhoto"));
			User usr = userRepository.findByUsername(responseData.getUsername());
			res.setUserPhoto(usr.getUserPhoto());
			return new ResponseEntity<>(res, HttpStatus.OK);
		}
	}

	// Update images after authorizing the username/password.
	@PostMapping("/updateUserPhoto")
	public ResponseEntity<Response> updateUserPhoto(
			@RequestPart("AuthenticationData") AuthenticationData authentication,
			@RequestPart("userPhoto") MultipartFile userPhoto,
			@RequestHeader(value = "Authorization", required = true) String authorization) throws IOException {
		StringBuilder log = new StringBuilder();
		Response res = new Response();
		User resultSet = userService.userAuthenticate(authentication.getUsername(), authentication.getPassword());
		if (resultSet == null) {
			res.setErrorCode(UserConstant.ERROR_CODE_01);
			res.setErrorMessage(UserConstant.ERROR_LU01_MESSAGE);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		} else {
			logger.info(log.append("Entering :: User Controller :: updateUserPhoto"));
			resultSet.setUserPhoto(userPhoto.getBytes());
			userRepository.save(resultSet);
			res.setErrorCode(UserConstant.USER_PHOTO_UPDATE_CODE);
			res.setErrorMessage(UserConstant.USER_PHOTO_UPDATE_MESSAGE);
			return new ResponseEntity<>(res, HttpStatus.OK);
		}

	}

	// delete images after authorizing the username/password.

	@PostMapping("/deleteUserPhoto")
	public ResponseEntity<Response> User(@RequestBody AuthenticationData authenticationData,
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		StringBuilder log = new StringBuilder();
		Response res = new Response();
		User resultSet = userService.userAuthenticate(authenticationData.getUsername(),
				authenticationData.getPassword());
		if (resultSet == null) {
			res.setErrorCode(UserConstant.ERROR_CODE_01);
			res.setErrorMessage(UserConstant.ERROR_LU01_MESSAGE);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		} else {
			logger.info(log.append("Entering :: User Controller :: deleteUserPhoto"));
			resultSet.setUserPhoto(null);
			userRepository.save(resultSet);
			res.setErrorCode(UserConstant.USER_PHOTO_DELETE_CODE);
			res.setErrorMessage(UserConstant.USER_PHOTO_DELETE_MESSAGE);
			return new ResponseEntity<>(res, HttpStatus.OK);
		}

	}

	// View the User Basic Information and the Images using email_Id

	@GetMapping("/getUserDetails/{email}")
	public User getUserDetails(@PathVariable String email,
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		StringBuilder log = new StringBuilder();
		logger.info(log.append("Entering :: User Controller :: getUserDetails by email"));
		// Using native query
		User res = userService.searchUserByEmail(email);
		return res;
	}

	// View the User Basic Information and the Image using userid and password
	@PostMapping("/userAuthenticate")
	public User authenticateUser(@RequestBody AuthenticationData authenticateData,
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		StringBuilder log = new StringBuilder();
		logger.info(log.append("Entering :: User Controller :: userAuthenticate"));
		String username = authenticateData.getUsername();
		String password = authenticateData.getPassword();
		User res = userService.userAuthenticate(username, password);
		if (res != null) {
			res.setPassword("******");
		}
		return res;

	}

}
