package com.smartcontactmanager.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartcontactmanager.entities.User;
import com.smartcontactmanager.helper.Message;
import com.smartcontactmanager.repository.UserRepository;

import jakarta.validation.Valid;

@Controller
public class AuthController {

	@Autowired
	UserRepository userRepository;

	@PostMapping("/register")
	public ResponseEntity<List<Message>> register(@RequestBody @Valid User user, BindingResult bindingResult,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
			@RequestParam(value = "repeatPassword") String repeatPassword, Model model) {
		List<Message> msgList = new ArrayList<Message>();
		if (bindingResult.hasErrors()) {
			// Handle validation errors
			List<FieldError> errors = bindingResult.getFieldErrors();
			errors.forEach(error -> {
				msgList.add(new Message(error.getField(), error.getDefaultMessage()));
			});
			return ResponseEntity.badRequest().body(msgList);
		}
		try {
			if (!agreement) {
				throw new Exception("Please agree to terms and conditions");
			} else if (!repeatPassword.matches(user.getPassword())) {
				throw new Exception("Repeated Password does not match");
			} else if (userRepository.findByEmail(user.getEmail()) != null) {
				throw new Exception("Email Already Registered");
			} else {
				try {
					user.setEnabled(true);
					user.setRole("USER");
					user.setImageURl("/img/default.png");
					User result = userRepository.save(user);
					System.out.println("New User Registered:" + result.toString());
					msgList.add(new Message("default", "Registered Successfully !!"));
					return new ResponseEntity<List<Message>>(msgList, HttpStatus.OK);

				} catch (Exception e1) {
					// TODO: handle exception
					System.err.println(e1.getMessage());
					msgList.add(new Message("default", e1.getMessage()));
					return new ResponseEntity<List<Message>>(msgList, HttpStatus.NOT_ACCEPTABLE);
				}
			}
		} catch (Exception e2) {
			// TODO: handle exception
			System.err.println(e2.getMessage());
			if (e2.getMessage().equals("Email Already Registered")) {
				msgList.add(new Message("email", e2.getMessage()));
				return new ResponseEntity<List<Message>>(msgList, HttpStatus.BAD_REQUEST);
			} else {
				msgList.add(new Message("default", e2.getMessage()));
				return new ResponseEntity<List<Message>>(msgList, HttpStatus.NOT_ACCEPTABLE);
			}
		}
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<List<Message>> login(@RequestBody @Valid User user, BindingResult bindingResult,
			@RequestParam(value = "agreement", defaultValue = "false") boolean rememberMe, Model model) {
		
		return new ResponseEntity<List<Message>>(new ArrayList<Message>(),HttpStatus.ACCEPTED);
	}
}
