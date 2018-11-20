package br.com.projetoTcc.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import br.com.projetoTcc.service.CompetenceService;
import br.com.projetoTcc.service.MatchService;
import br.com.projetoTcc.service.UserService;

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
	    	
	    	Collection<User> userOkToMatch = findUsersOkToMatch();
	    	
	    	
	    	
	    	model.addAttribute("allUsers", userOkToMatch);
	    	
	    	
	    	
	    	logger.info("match");
	        return "match";
	    }


		private Collection<User> findUsersOkToMatch() {
			
			Collection<User> allUsers = userService.findAllByRole(Roles.ROLE_USER.getValue());
	    	User userLogin = globalController.getLoginUser();	
			
	    	List<Match> matchsUserLogin = matchService.findByUserRequestAndMatchStatus(userLogin, MatchStatus.WAITING.getValue());
          
	    	Collection<User> userOkToMatch = new ArrayList<User>();
	    		for (User user : allUsers) {
					boolean isEquals = false;
					if (!matchsUserLogin.isEmpty() || matchsUserLogin == null) {
		    			for (Match match : matchsUserLogin) {
							if(!isEquals){
								if ((user.getId() == match.getUserReceiver().getId())  || (userLogin.getId() == user.getId())){
									isEquals = true;
								}								
							}							
						}
		    			if ((!isEquals)) {
							userOkToMatch.add(user);								
						}
					}else {
						if (userLogin.getId() != user.getId()) {
							userOkToMatch.add(user);	
						}
					}
					
				}
				return userOkToMatch;
	    	
	    	
		}
	    
	    
	    @RequestMapping(value = {"/match/request/{id}"},  method = RequestMethod.GET)
	    public String requestMatch(@PathVariable("id") int id,    		
	    													Model model,
	    													final RedirectAttributes redirectAttributes) {
	        logger.info("/match/request/" + id);
	        try {
	        	User userMatch = userService.findById(id);
	            User userLogin = globalController.getLoginUser();
	        	
	            //falta refatorar
	            Match match = checkIfMatchExists(userMatch, userLogin);	
        		
        		if (match != null) {
        			match.setMatchStatus(MatchStatus.ACCEPTED.getValue());        	    	
                	redirectAttributes.addFlashAttribute("msg", "acceptedMatch"); 	    	
                	redirectAttributes.addFlashAttribute("userMatch", userMatch);
                	redirectAttributes.addFlashAttribute("allUsers", findUsersOkToMatch());
                	return "redirect:/match";        			
        		}
        		
        		Match newMatch = new Match(userLogin, userMatch);
        		if (matchService.save(newMatch) != null) {
        			newMatch.setMatchStatus(MatchStatus.WAITING.getValue());        	    	
                	redirectAttributes.addFlashAttribute("msg", "waitingMatch"); 	    	
                	redirectAttributes.addFlashAttribute("userMatch", userMatch);
                	redirectAttributes.addFlashAttribute("allUsers", findUsersOkToMatch());
                	return "redirect:/match";
        		}
        		
	       
	        
	        } catch (Exception e) {
	            model.addAttribute("msg", "fail");
	            logger.error("requestMatch: " + e.getMessage());
	        }
	        model.addAttribute("allUsers", findUsersOkToMatch());
	        return "match";         	            
	          
	        }


		private Match checkIfMatchExists(User userMatch, User userLogin) {
			List<Match> matchsUserLogin = matchService.findByUserReceiverAndMatchStatus(userLogin, MatchStatus.WAITING.getValue());
			
			if (matchsUserLogin != null) {
				for (Match match : matchsUserLogin) {
					if(match.getUserRequest().getId() == userMatch.getId()) {
						return match;
					}
				}
			}
			return null;
		
	    }
	    
	    
}
