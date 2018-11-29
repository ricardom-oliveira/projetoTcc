package br.com.projetoTcc.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.projetoTcc.model.Match;
import br.com.projetoTcc.model.User;
import br.com.projetoTcc.model.enums.MatchStatus;
import br.com.projetoTcc.model.enums.Roles;
import br.com.projetoTcc.model.enums.Status;
import br.com.projetoTcc.service.MatchService;
import br.com.projetoTcc.service.UserService;

@Controller
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	GlobalController globalController;

	@Autowired
	UserService userService;

	@Autowired
	MatchService matchService;

	
	@RequestMapping("/admin")
	public String homeAdmin(Model model) {

		logger.info("admin");
		return "home";
	}
	
    @RequestMapping("/all-users")
    public String allUsers (Model model) {
    	model.addAttribute("allUsers", userService.findAllByRole(Roles.ROLE_USER.getValue()));
    	Collection<Match> allUsers = matchService.findByMatchStatus(MatchStatus.ACCEPTED.getValue());
    	if (allUsers.isEmpty()) {
    		model.addAttribute("isEmpty", true);
    		return "all-users";
    	}
    	logger.info("all-users");
        return "all-users";
    }
    
    @RequestMapping("/all-matchs")
    public String allMatchs (Model model) {
    	Collection<Match> allMatchs = matchService.findByMatchStatus(MatchStatus.ACCEPTED.getValue());
    	if (allMatchs.isEmpty()) {
    		model.addAttribute("isEmpty", true);
    		return "all-matchs";
    	}
    	
    	model.addAttribute("allMatchs", allMatchs);
    	logger.info("all-matchs");
        return "all-matchs";
    }

	@RequestMapping(value = { "/admin/delete/{id}" }, method = RequestMethod.GET)
	public String deleteUserByAdmin(@PathVariable("id") int id, Model model,
			final RedirectAttributes redirectAttributes) {
		logger.info("/admin/delete/{id}" + id);
		try {
			if (userService.delete(id)) {
				redirectAttributes.addFlashAttribute("msg", "successDelete");
				redirectAttributes.addFlashAttribute("allUsers", userService.findAll());
				return "redirect:/admin";
			}

		} catch (Exception e) {
			model.addAttribute("msg", "fail");
			logger.error("deleteUserByAdmin: " + e.getMessage());
		}
		return "admin";
	}

	@RequestMapping(value = { "/admin/banishment/{id}" }, method = RequestMethod.GET)
	public String banishmentUserByAdmin(@PathVariable("id") int id, Model model,
			final RedirectAttributes redirectAttributes) {
		logger.info("/admin/delete/{id}" + id);
		try {

			User user = userService.findById(id);

			user.setStatus(Status.PASSIVE);

			redirectAttributes.addFlashAttribute("msg", "successBanishment");
			redirectAttributes.addFlashAttribute("allUsers", userService.findAll());
			return "redirect:/admin";

		} catch (Exception e) {
			model.addAttribute("msg", "fail");
			logger.error("deleteUserByAdmin: " + e.getMessage());
		}
		return "admin";
	}
	
	@RequestMapping(value = { "/admin/cancel/{id}" }, method = RequestMethod.GET)
	public String cancelMatchAdmin(@PathVariable("id") int id, Model model, final RedirectAttributes redirectAttributes) {
		logger.info("/admin/cancel/" + id);
		try {
			Match match = matchService.findById(id);

			if (match != null) {
				matchService.delete(match);

				redirectAttributes.addFlashAttribute("msg", "cancelMsg");
				redirectAttributes.addFlashAttribute("userMatchRequest", match.getUserRequest());
				redirectAttributes.addFlashAttribute("userMatchReceiver", match.getUserReceiver());
				return "redirect:/all-matchs";
			}

		} catch (Exception e) {
			model.addAttribute("msg", "fail");
			logger.error("cancelMatchAdmin: " + e.getMessage());
		}

		return "/all-matchs";

	}
}
