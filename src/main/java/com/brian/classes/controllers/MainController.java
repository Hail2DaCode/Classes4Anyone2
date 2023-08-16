package com.brian.classes.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.brian.classes.models.Class;
import com.brian.classes.models.LoginUser;
import com.brian.classes.models.Rating;
import com.brian.classes.models.User;
import com.brian.classes.services.ClassService;
import com.brian.classes.services.RatingService;
import com.brian.classes.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
@Controller
public class MainController {
	@Autowired 
	private UserService userServ;
	@Autowired
	private ClassService cServ;
	@Autowired
	private RatingService rServ;
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("newUser", new User());
		model.addAttribute("newLogin", new LoginUser());
		return "loginAndReg.jsp";
	}
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("newUser") User newUser,
			BindingResult result, Model model, HttpSession session) {
		User legitUser = userServ.register(newUser, result);
		 if (result.hasErrors()) {
			 model.addAttribute("newLogin", new LoginUser());
			 return "/loginAndReg.jsp";
		 }
		 session.setAttribute("id", legitUser.getId());
		 session.setAttribute("name", legitUser.getUserName());
		 return "redirect:/dash";
	}
	
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin,
			BindingResult result, Model model, HttpSession session) {
		User user = userServ.login(newLogin, result);
		if (result.hasErrors()) {
			model.addAttribute("newUser", new User());
			return "loginAndReg.jsp";
		}
		else {
			 session.setAttribute("id", user.getId());
			 session.setAttribute("name", user.getUserName());
			 return "redirect:/dash";
		}
	}
	@GetMapping("/clear")
	public String clear(HttpSession session) {
		session.setAttribute("id", null);
		session.setAttribute("name", null);
		return "redirect:/";
	}
	@GetMapping("/dash")
	public String homePage(Model model, HttpSession session) {
		if (session.getAttribute("id") == null) {
			return "redirect:/";
		}
		List<Class> allClasses = cServ.allClasses();
		List<Class> classesITeach = cServ.allClassesITeach((Long) session.getAttribute("id"));
		List<Class> classesIStudy = cServ.allClassesIStudy((Long) session.getAttribute("id"), (Long) session.getAttribute("id"));
		List<Class> availableClasses = cServ.availableClasses((Long) session.getAttribute("id"), (Long) session.getAttribute("id"));
//		List<Rating> averageRatings = rServ.getAverageRatings();
//		System.out.println(averageRatings);
		HashMap<Long, Float> averageRatingsITeach = cServ.averageRatingsPerClass(classesITeach);
		HashMap<Long, Float> averageRatingsIStudy = cServ.averageRatingsPerClass(classesIStudy);
		HashMap<Long, Float> averageRatingsAvailable = cServ.averageRatingsPerClass(availableClasses);
		HashMap<Long, Float> averageRatings = cServ.averageRatingsPerClass(allClasses);
		model.addAttribute("allClasses", allClasses);
		model.addAttribute("classesITeach", classesITeach);
		model.addAttribute("classesIStudy", classesIStudy);
		model.addAttribute("availableClasses", availableClasses);
		model.addAttribute("averageRatingsITeach", averageRatingsITeach);
		model.addAttribute("averageRatingsIStudy",averageRatingsIStudy);
		model.addAttribute("averageRatingsAvailable", averageRatingsAvailable);
		model.addAttribute("averageRatings", averageRatings);
		//System.out.println(averages.get(3));
		return "dash.jsp";
	}
	@GetMapping("/classes/new")
	public String newClassPage(@ModelAttribute("class") Class klass, HttpSession session) {
		if (session.getAttribute("id") == null) {
			return "redirect:/";
		}
		return "newClass.jsp";
	}
	@PostMapping("/new/Class")
	public String newClassCreation(@Valid @ModelAttribute("class") Class klass, BindingResult result, HttpSession session) {
		if (session.getAttribute("id") == null) {
			return "redirect:/";
		}
		if (cServ.isTitle(klass)) {
			result.rejectValue("title", "Matches", "Title for class is already taken.  Please use another one!");
		}
		if (result.hasErrors()) {
			return "newClass.jsp";
		}
		else {
			cServ.createClass(klass);
			return "redirect:/dash";
		}
	}
	@GetMapping("/classes/{id}/join")
	public String joinClass(@PathVariable("id") Long id, Model model, HttpSession session) {
		if (session.getAttribute("id") == null) {
			return "redirect:/";
		}
		Class oldClass = cServ.findClass(id);
		User student = userServ.findUser((Long)session.getAttribute("id"));
		cServ.addStudent(oldClass, student);
		return "redirect:/dash";
	}
	@GetMapping("/classes/{id}/edit")
	public String editPage(@PathVariable("id") Long id, Model model, HttpSession session) {
		if (session.getAttribute("id") == null) {
			return "redirect:/";
		}
		Class klass = cServ.findClass(id);
		model.addAttribute("klass", klass);
		return "editClass.jsp";
	}
	@PutMapping("/classes/{id}")
	public String updateClass(@Valid @ModelAttribute("klass") Class klass, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("klass", klass);
			System.out.println("error");
			return "editClass.jsp";
		}
		else {
			System.out.println("success");
			cServ.createClass(klass);
			return "redirect:/dash";
		}
	}
	@GetMapping("/classes/{id}/delete")
	public String destroyClass(@PathVariable("id") Long id, HttpSession session) {
		if (session.getAttribute("id") == null) {
			return "redirect:/";
		}
		cServ.deleteClass(id);
		return "redirect:/dash";
	}
	@GetMapping("/classes/{id}/leave")
	public String leaveClass(@PathVariable("id") Long id, HttpSession session) {
		if (session.getAttribute("id") == null) {
			return "redirect:/";
		}
		Class oldClass = cServ.findClass(id);
		User student = userServ.findUser((Long)session.getAttribute("id"));
		cServ.removeStudent(oldClass, student);
		return "redirect:/dash";
	}
	@GetMapping("/classes/{id}")
	public String showClass(@PathVariable("id") Long id, Model model, HttpSession session) {
		if (session.getAttribute("id") == null) {
			return "redirect:/";
		}
		Class klass = cServ.findClass(id);
		User student = userServ.findUser((Long)session.getAttribute("id"));
		List<Class> classes = cServ.allClasses();
		HashMap<Long, Float> averageRatings = cServ.averageRatingsPerClass(classes);
		//List<Class> availableClasses = cServ.availableClasses((Long)session.getAttribute("id"), (Long)session.getAttribute("id"));
		model.addAttribute("klass", klass);
		model.addAttribute("student", student);
		model.addAttribute("averageRatings", averageRatings);
		//model.addAttribute("availableClasses", availableClasses);
		return "showClass.jsp";
	}
	@GetMapping("/classes/{id}/rate")
	public String showRating(@PathVariable("id") Long id, Model model, HttpSession session) {
		if (session.getAttribute("id") == null) {
			return "redirect:/";
		}
		Class klass = cServ.findClass(id);
		User student = userServ.findUser((Long)session.getAttribute("id"));
		//List<Class> availableClasses = cServ.availableClasses((Long)session.getAttribute("id"), (Long)session.getAttribute("id"));
		model.addAttribute("klass", klass);
		model.addAttribute("student", student);
		return "rateClass.jsp";
	}
	@PostMapping("/rate/class")
	public String rateClass( @RequestParam(value = "rating") Long rId, @RequestParam(value = "klass") Long kId, @RequestParam(value = "student") Long sId) {
		Rating rating = rServ.findRating(rId);
		Class klass = cServ.findClass(kId);
		cServ.addRatings(klass, rating);
		return "redirect:/dash";
	}
}
