package com.controllers;

import java.io.IOException;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.User;

import com.models.ChunkData;
import com.services.ScanManager;

@RestController
public class ClientController {
	@Autowired
	private ScanManager scanManager;

	@RequestMapping(value = "/client/login", method = RequestMethod.POST)
	public ResponseEntity<?> getNextTask() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/client/getNextTask", method = RequestMethod.POST)
	public ResponseEntity<ChunkData> getNextTask(@AuthenticationPrincipal User user)
			throws ClassNotFoundException, IOException {
		return new ResponseEntity<>(scanManager.getNextTask(user.getUsername()), HttpStatus.OK);
	}

	@RequestMapping(value = "/client/setResult", method = RequestMethod.POST)
	public void setResult(@AuthenticationPrincipal User user, @RequestParam String password)
			throws NameNotFoundException {
		scanManager.setResult(user.getUsername(), password);
	}

	@RequestMapping(value = "/client/keepAlive", method = RequestMethod.POST)
	public void keepAlive(@AuthenticationPrincipal User user) throws NameNotFoundException {
		scanManager.keepAlive(user.getUsername());
	}

	@ExceptionHandler({ ClassNotFoundException.class, IOException.class })
	public ResponseEntity<?> handleDbError(Exception e) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<?> handleDbError(EmptyResultDataAccessException e) {
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler(NameNotFoundException.class)
	public ResponseEntity<?> handleDbError(NameNotFoundException e) {
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
