package com.brian.classes.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.brian.classes.models.LoginUser;
import com.brian.classes.models.User;
import com.brian.classes.repositories.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepo;
	public UserService (UserRepository userR) {
		this.userRepo = userR;
	}
	public User register(User newUser, BindingResult result) {
//		Calendar rightNow = Calendar.getInstance();
//		rightNow.add(1, -10);
		//System.out.println(rightNow);
		//System.out.println(newUser.getBirthday());
		Optional<User> potentialUser = userRepo.findByEmail(newUser.getEmail());
		if (potentialUser.isPresent()) {
			result.rejectValue("email", "Matches", "Your email is already taken.");
			
		}
		if (!newUser.getPassword().equals(newUser.getConfirm())) {
			result.rejectValue("confirm", "Matches","The Confirm Password must match Password!");
			
		}
//		if (newUser.getBirthday().after(rightNow)) {
//			result.rejectValue("birthday", "Matches", "Your must be at least ten years old to register!");
//		}
		
		if (result.hasErrors()) {
			return null;
		}
		
		String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
		newUser.setPassword(hashed);
		userRepo.save(newUser);
		return newUser;
	}
	public User login(LoginUser newLoginObject, BindingResult result) {
		User user = null;
		Optional<User> potentialUser = userRepo.findByEmail(newLoginObject.getEmail());
		if (!potentialUser.isPresent()) {
			result.rejectValue("email", "Matches", "Your email is not correct. Either register or retry.");
			
		}
		else {
			 user = potentialUser.get();
			 if ( !BCrypt.checkpw(newLoginObject.getPassword(), user.getPassword())) {
					result.rejectValue("password", "Matches","Password does not match.  Either register or retry.");
				
				}
			 
		}
		
		if (result.hasErrors()) {
			return null;
		}
		else {
			return user;
		}
		
	}
	public User findUser(Long id) {
		Optional<User> optionalUser = userRepo.findById(id);
		if(optionalUser.isPresent()) {
			return optionalUser.get();
		}
		else {
			return null;
		}
	}
}
