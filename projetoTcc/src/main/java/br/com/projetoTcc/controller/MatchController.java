package br.com.projetoTcc.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.projetoTcc.model.Match;
import br.com.projetoTcc.model.User;
import br.com.projetoTcc.model.enums.MatchStatus;
import br.com.projetoTcc.service.CompetenceService;
import br.com.projetoTcc.service.MatchService;
import br.com.projetoTcc.service.UserService;
import br.com.projetoTcc.utils.CompetenceFilter;
import br.com.projetoTcc.utils.MatchFilter;

@Controller
public class MatchController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	GlobalController globalController;

	@Autowired
	UserService userService;

	@Autowired
	CompetenceService competenceService;

	@Autowired
	MatchService matchService;

	@RequestMapping("/match")
	public String match(Model model) {
		User user = userService.findById(globalController.getLoginUser().getId());

		CompetenceFilter competenceFilter = new CompetenceFilter();
		competenceFilter.setCompetenceCategorie("all");

		Collection<User> userList = userService.filterUsersByCompetence(competenceFilter, user);

		model.addAttribute("userList", userList);

		model.addAttribute("competenceFilter", competenceFilter);

		int numberNewMatchs = userService.findNumberOfnewMatchs(user);
		model.addAttribute("numberNewMatchs", numberNewMatchs);

		if (userList.isEmpty())
			model.addAttribute("isEmpty", true);
		else
			model.addAttribute("userList", userList);

		logger.info("match");
		return "match";
	}

	@RequestMapping("/mymatchs")
	public String mymatchs(Model model) {

		MatchFilter matchFilter = new MatchFilter();
		matchFilter.setMatchStatus("all");
		User user = userService.findById(globalController.getLoginUser().getId());

		Collection<Match> matchUsersReceivers = userService.filterMatchsReceivers(matchFilter, user);
		Collection<Match> matchUsersRequests = userService.filterMatchsRequests(matchFilter, user);

		model.addAttribute("matchFilter", matchFilter);

		int numberNewMatchs = userService.findNumberOfnewMatchs(user);
		model.addAttribute("numberNewMatchs", numberNewMatchs);

		if (matchUsersReceivers.isEmpty() && matchUsersRequests.isEmpty())
			model.addAttribute("isEmpty", true);
		else {
			model.addAttribute("matchUsersReceivers", matchUsersReceivers);
			model.addAttribute("matchUsersRequests", matchUsersRequests);
		}
		logger.info("mymatchs");
		return "mymatchs";
	}

	@RequestMapping(value = { "/match/request/{id}" }, method = RequestMethod.GET)
	public String requestMatch(@PathVariable("id") int id, Model model, final RedirectAttributes redirectAttributes) {
		logger.info("/match/request/" + id);
		try {
			User userMatch = userService.findById(id);
			User user = userService.findById(globalController.getLoginUser().getId());

			Match match = matchService.checkIfMatchExists(userMatch, user);

			if (match != null) {
				if (match.getMatchStatus().equals(MatchStatus.WAITING.getValue())) {

					match = matchService.setToAccepted(match);

					model.addAttribute("msg", "acceptedMatch");
					model.addAttribute("userMatch", userMatch);

					CompetenceFilter competenceFilter = new CompetenceFilter();
					competenceFilter.setCompetenceCategorie("all");

					Collection<User> userList = userService.filterUsersByCompetence(competenceFilter, user);

					model.addAttribute("userList", userList);

					model.addAttribute("competenceFilter", competenceFilter);

					int numberNewMatchs = userService.findNumberOfnewMatchs(user);
					model.addAttribute("numberNewMatchs", numberNewMatchs);

					if (userList.isEmpty())
						model.addAttribute("isEmpty", true);
					else
						model.addAttribute("userList", userList);

					return "/match";
				}

				if (match.getMatchStatus().equals(MatchStatus.IGNORED.getValue())) {
					redirectAttributes.addFlashAttribute("msg", "rejectMatch");
					redirectAttributes.addFlashAttribute("userMatch", userMatch);

					CompetenceFilter competenceFilter = new CompetenceFilter();
					competenceFilter.setCompetenceCategorie("all");

					Collection<User> userList = userService.filterUsersByCompetence(competenceFilter, user);

					model.addAttribute("userList", userList);

					model.addAttribute("competenceFilter", competenceFilter);

					int numberNewMatchs = userService.findNumberOfnewMatchs(user);
					model.addAttribute("numberNewMatchs", numberNewMatchs);

					if (userList.isEmpty())
						model.addAttribute("isEmpty", true);
					else
						model.addAttribute("userList", userList);

					return "/match";
				}

			}

			Match newMatch = new Match(user, userMatch);
			if (matchService.save(newMatch) != null) {
				newMatch.setMatchStatus(MatchStatus.WAITING.getValue());
				redirectAttributes.addFlashAttribute("msg", "waitingMatch");
				redirectAttributes.addFlashAttribute("userMatch", userMatch);

				CompetenceFilter competenceFilter = new CompetenceFilter();
				competenceFilter.setCompetenceCategorie("all");

				Collection<User> userList = userService.filterUsersByCompetence(competenceFilter, user);

				model.addAttribute("userList", userList);

				model.addAttribute("competenceFilter", competenceFilter);

				int numberNewMatchs = userService.findNumberOfnewMatchs(user);
				model.addAttribute("numberNewMatchs", numberNewMatchs);

				if (userList.isEmpty())
					model.addAttribute("isEmpty", true);
				else
					model.addAttribute("userList", userList);

				return "/match";
			}

		} catch (Exception e) {
			model.addAttribute("msg", "fail");
			logger.error("requestMatch: " + e.getMessage());
		}

		return "/home";

	}

	@RequestMapping(value = { "/match/ignore/{id}" }, method = RequestMethod.GET)
	public String ignoreMatch(@PathVariable("id") int id, Model model, final RedirectAttributes redirectAttributes) {
		logger.info("/match/ignore/" + id);
		try {
			User userMatch = userService.findById(id);
			User user = userService.findById(globalController.getLoginUser().getId());
			Collection<User> userList = new ArrayList<User>();
			Match match = matchService.checkIfMatchExists(userMatch, user);

			if (match != null) {
				if (match.getMatchStatus().equals(MatchStatus.WAITING.getValue())) {
					match = matchService.setToIgnored(match);
					redirectAttributes.addFlashAttribute("msg", "ignoredMatch");
					redirectAttributes.addFlashAttribute("userMatch", userMatch);

					CompetenceFilter competenceFilter = new CompetenceFilter();
					competenceFilter.setCompetenceCategorie("all");

					userList = userService.filterUsersByCompetence(competenceFilter, user);

					model.addAttribute("userList", userList);

					model.addAttribute("competenceFilter", competenceFilter);

					int numberNewMatchs = userService.findNumberOfnewMatchs(user);
					model.addAttribute("numberNewMatchs", numberNewMatchs);

					if (userList.isEmpty())
						model.addAttribute("isEmpty", true);
					else
						model.addAttribute("userList", userList);

					return "/match";

				} else {
					if (match.getMatchStatus().equals(MatchStatus.IGNORED.getValue())) {
						match = matchService.setToIgnored(match);
						redirectAttributes.addFlashAttribute("msg", "ignoredMatch");
						redirectAttributes.addFlashAttribute("userMatch", userMatch);

						CompetenceFilter competenceFilter = new CompetenceFilter();
						competenceFilter.setCompetenceCategorie("all");

						userList = userService.filterUsersByCompetence(competenceFilter, user);

						model.addAttribute("userList", userList);

						model.addAttribute("competenceFilter", competenceFilter);

						int numberNewMatchs = userService.findNumberOfnewMatchs(user);
						model.addAttribute("numberNewMatchs", numberNewMatchs);

						if (userList.isEmpty())
							model.addAttribute("isEmpty", true);
						else
							model.addAttribute("userList", userList);

						return "/match";
					}

				}

			}

			Match newMatch = new Match(user, userMatch);
			if (matchService.save(newMatch) != null) {
				newMatch = matchService.setToIgnored(newMatch);
				model.addAttribute("msg", "ignoredMatch");
				model.addAttribute("userMatch", userMatch);

				CompetenceFilter competenceFilter = new CompetenceFilter();
				competenceFilter.setCompetenceCategorie("all");

				userList = userService.filterUsersByCompetence(competenceFilter, user);

				model.addAttribute("userList", userList);

				model.addAttribute("competenceFilter", competenceFilter);

				int numberNewMatchs = userService.findNumberOfnewMatchs(user);
				model.addAttribute("numberNewMatchs", numberNewMatchs);

				if (userList.isEmpty())
					model.addAttribute("isEmpty", true);
				else
					model.addAttribute("userList", userList);

				return "/match";
			}

		} catch (Exception e) {
			model.addAttribute("msg", "fail");
			logger.error("requestMatch: " + e.getMessage());
		}

		return "/home";

	}

	@RequestMapping(value = { "/mymatchs/filter" }, method = RequestMethod.POST)
	public String filterMatch(@ModelAttribute("matchFilter") MatchFilter matchFilter, Model model,
			final RedirectAttributes redirectAttributes) {
		logger.info("/match/filter" + matchFilter);
		try {
			User user = userService.findById(globalController.getLoginUser().getId());

			Collection<Match> matchUsersReceivers = userService.filterMatchsReceivers(matchFilter, user);
			Collection<Match> matchUsersRequests = userService.filterMatchsRequests(matchFilter, user);

			model.addAttribute("matchFilter", matchFilter);

			int numberNewMatchs = userService.findNumberOfnewMatchs(user);
			model.addAttribute("numberNewMatchs", numberNewMatchs);

			if (matchUsersReceivers.isEmpty() && matchUsersRequests.isEmpty())
				model.addAttribute("isEmpty", true);
			else {
				model.addAttribute("matchUsersReceivers", matchUsersReceivers);
				model.addAttribute("matchUsersRequests", matchUsersRequests);
			}

		} catch (Exception e) {
			model.addAttribute("msg", "fail");
			logger.error("requestMyMatchs: " + e.getMessage());
		}

		return "myMatchs";

	}

	@RequestMapping(value = { "/mymatchs/accept/{id}" }, method = RequestMethod.GET)
	public String acceptMatch(@PathVariable("id") int id, Model model, final RedirectAttributes redirectAttributes) {
		logger.info("/mymatchs/accept/" + id);
		try {
			Match match = matchService.findById(id);
			User user = userService.findById(globalController.getLoginUser().getId());

			if (match != null) {

				match = matchService.setToAccepted(match);
				model.addAttribute("msg", "acceptMsg");
				redirectAttributes.addFlashAttribute("userMatch", match.getUserRequest());
				MatchFilter matchFilter = new MatchFilter();
				matchFilter.setMatchStatus("all");

				Collection<Match> matchUsersReceivers = userService.filterMatchsReceivers(matchFilter, user);
				Collection<Match> matchUsersRequests = userService.filterMatchsRequests(matchFilter, user);

				model.addAttribute("matchFilter", matchFilter);
				model.addAttribute("userMatch", match.getUserRequest());

				int numberNewMatchs = userService.findNumberOfnewMatchs(user);
				model.addAttribute("numberNewMatchs", numberNewMatchs);

				if (matchUsersReceivers.isEmpty() && matchUsersRequests.isEmpty())
					model.addAttribute("isEmpty", true);
				else {
					model.addAttribute("matchUsersReceivers", matchUsersReceivers);
					model.addAttribute("matchUsersRequests", matchUsersRequests);
				}
				return "/mymatchs";
			}

		} catch (Exception e) {
			model.addAttribute("msg", "fail");
			logger.error("accpetMatch: " + e.getMessage());
		}

		return "/mymatchs";

	}

	@RequestMapping(value = { "/mymatchs/cancel/{id}" }, method = RequestMethod.GET)
	public String cancelMatch(@PathVariable("id") int id, Model model, final RedirectAttributes redirectAttributes) {
		logger.info("/mymatchs/cancel/" + id);
		try {
			Match match = matchService.findById(id);
			User user = userService.findById(globalController.getLoginUser().getId());

			if (match != null) {
				matchService.delete(match);

				model.addAttribute("msg", "cancelMsg");
				model.addAttribute("userMatch", match.getUserRequest());
				MatchFilter matchFilter = new MatchFilter();
				matchFilter.setMatchStatus("all");

				Collection<Match> matchUsersReceivers = userService.filterMatchsReceivers(matchFilter, user);
				Collection<Match> matchUsersRequests = userService.filterMatchsRequests(matchFilter, user);

				model.addAttribute("matchFilter", matchFilter);
				model.addAttribute("userMatch", match.getUserRequest());

				int numberNewMatchs = userService.findNumberOfnewMatchs(user);
				redirectAttributes.addAttribute("numberNewMatchs", numberNewMatchs);

				if (matchUsersReceivers.isEmpty() && matchUsersRequests.isEmpty())
					model.addAttribute("isEmpty", true);
				else {
					model.addAttribute("matchUsersReceivers", matchUsersReceivers);
					model.addAttribute("matchUsersRequests", matchUsersRequests);
				}
				return "/mymatchs";
			}

		} catch (Exception e) {
			model.addAttribute("msg", "fail");
			logger.error("cancelMatch: " + e.getMessage());
		}

		return "/mymatchs";

	}

	@RequestMapping(value = { "/mymatchs/undoignore/{id}" }, method = RequestMethod.GET)
	public String undoIgnoreMatch(@PathVariable("id") int id, Model model,
			final RedirectAttributes redirectAttributes) {
		logger.info("/mymatchs/undoignore/" + id);
		try {
			Match match = matchService.findById(id);
			User user = userService.findById(globalController.getLoginUser().getId());

			if (match != null) {
				matchService.delete(match);
				model.addAttribute("msg", "undoIgnoreMsg");
				model.addAttribute("userMatch", match.getUserRequest());
				MatchFilter matchFilter = new MatchFilter();
				matchFilter.setMatchStatus("all");

				Collection<Match> matchUsersReceivers = userService.filterMatchsReceivers(matchFilter, user);
				Collection<Match> matchUsersRequests = userService.filterMatchsRequests(matchFilter, user);

				model.addAttribute("matchFilter", matchFilter);
				model.addAttribute("userMatch", match.getUserRequest());

				int numberNewMatchs = userService.findNumberOfnewMatchs(user);
				model.addAttribute("numberNewMatchs", numberNewMatchs);

				if (matchUsersReceivers.isEmpty() && matchUsersRequests.isEmpty())
					model.addAttribute("isEmpty", true);
				else {
					model.addAttribute("matchUsersReceivers", matchUsersReceivers);
					model.addAttribute("matchUsersRequests", matchUsersRequests);
				}
				model.addAttribute("msg", "error");
				return "/mymatchs";
			}

		} catch (Exception e) {
			model.addAttribute("msg", "fail");
			logger.error("undoIgnoreMatch: " + e.getMessage());
		}

		return "/mymatchs";

	}

	@RequestMapping(value = { "/mymatchs/ignore/{id}" }, method = RequestMethod.GET)
	public String ignoreOrder(@PathVariable("id") int id, Model model, final RedirectAttributes redirectAttributes) {
		logger.info("/mymatchs/cancel/" + id);
		try {
			Match match = matchService.findById(id);
			
			User user = userService.findById(globalController.getLoginUser().getId());

			if (match != null) {
				matchService.delete(match);

				model.addAttribute("msg", "ignoreOrderMsg");
				model.addAttribute("userMatch", match.getUserReceiver());
				MatchFilter matchFilter = new MatchFilter();
				matchFilter.setMatchStatus("all");

				Collection<Match> matchUsersReceivers = userService.filterMatchsReceivers(matchFilter, user);
				Collection<Match> matchUsersRequests = userService.filterMatchsRequests(matchFilter, user);

				model.addAttribute("matchFilter", matchFilter);
				model.addAttribute("userMatch", match.getUserReceiver());

				int numberNewMatchs = userService.findNumberOfnewMatchs(user);
				redirectAttributes.addAttribute("numberNewMatchs", numberNewMatchs);

				if (matchUsersReceivers.isEmpty() && matchUsersRequests.isEmpty())
					model.addAttribute("isEmpty", true);
				else {
					model.addAttribute("matchUsersReceivers", matchUsersReceivers);
					model.addAttribute("matchUsersRequests", matchUsersRequests);
				}
				return "/mymatchs";
			}

		} catch (Exception e) {
			model.addAttribute("msg", "fail");
			logger.error("cancelMatch: " + e.getMessage());
		}

		return "/mymatchs";

	}
}
