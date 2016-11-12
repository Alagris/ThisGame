package net.alagris.src.controller;

import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.alagris.src.crypto.HashPassword;
import net.alagris.src.model.AssertDatabase;
import net.alagris.src.model.UserDatabaseQuery;
import net.alagris.src.model.object.UnporcessedUserObject;
import net.alagris.src.model.object.UserObject;

@Controller
public class RegisterController {

	@Autowired
	UserDatabaseQuery userDatabaseQuery;
	@Autowired
	AssertDatabase assertDatabase;
	@Autowired
	HashPassword hashPassword;

	@RequestMapping()
	String login(Model model) {
		model.addAttribute("unporcessedUserObject",new UnporcessedUserObject());
		return "/register.html";
	}

	String loginSubmit(@ModelAttribute UnporcessedUserObject unporcessedUserObject,
			RedirectAttributes redirectAttributes) {
		if (!unporcessedUserObject.validate(assertDatabase, userDatabaseQuery)) {
			redirectAttributes.addFlashAttribute("message", "Validation failed!");
			return "redirect:/register.html";
		}
		try {
			UserObject userObject = userDatabaseQuery.getUserByName(unporcessedUserObject.getUsername());
			try {
				if (userObject.comparePasswords(unporcessedUserObject.getPlaintextPassword(), hashPassword)) {
					redirectAttributes.addFlashAttribute("message", "Login successful!");
					return "redirect:/";
				} else {
					redirectAttributes.addFlashAttribute("message", "Login or password incorrect!");
					return "redirect:/register.html";
				}
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
				redirectAttributes.addFlashAttribute("message", "Something went wrong!");
				return "redirect:/register.html";
			}
		} catch (DataAccessException e) {
			redirectAttributes.addFlashAttribute("message", "User doesn't exist!");
			return "redirect:/register.html";
		}
	}

}
