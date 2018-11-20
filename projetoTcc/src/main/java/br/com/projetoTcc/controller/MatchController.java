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

	    	
	    	Collection<User> userList = matchService.findUsersOkToMatch(globalController.getLoginUser());
        	if(userList.isEmpty())
        		model.addAttribute("isEmpty", true);
        	else
        		model.addAttribute("allUsers", userList);
	    	
	    	
	    	
	    	
	    	
	    	logger.info("match");
	        return "match";
	    }


	    
	    @RequestMapping(value = {"/match/request/{id}"},  method = RequestMethod.GET)
	    public String requestMatch(@PathVariable("id") int id,    		
	    													Model model,
	    													final RedirectAttributes redirectAttributes) {
	        logger.info("/match/request/" + id);
	        try {
	        	User userMatch = userService.findById(id);
	            User userLogin = globalController.getLoginUser();
	        	
	            Match match = matchService.checkIfMatchExists(userMatch, userLogin);	
        		
        		if (match != null) {
        			if (match.getMatchStatus().equals(MatchStatus.WAITING.getValue())) {
        				match.setMatchStatus(MatchStatus.ACCEPTED.getValue());        	    	
                    	redirectAttributes.addFlashAttribute("msg", "acceptedMatch"); 	    	
                    	redirectAttributes.addFlashAttribute("userMatch", userMatch);
                    	Collection<User> userList = matchService.findUsersOkToMatch(globalController.getLoginUser());
                    	if(userList.isEmpty())
                    		redirectAttributes.addFlashAttribute("isEmpty", true);
                    	else
                    		redirectAttributes.addFlashAttribute("allUsers", userList);        
                    	return "redirect:/match"; 
        			}
        			
        			if (match.getMatchStatus().equals(MatchStatus.IGNORED.getValue())) { 	    	
                    	redirectAttributes.addFlashAttribute("msg", "rejectMatch"); 	    	
                    	redirectAttributes.addFlashAttribute("userMatch", userMatch);
                    	Collection<User> userList = matchService.findUsersOkToMatch(globalController.getLoginUser());
                    	if(userList.isEmpty())
                    		redirectAttributes.addFlashAttribute("isEmpty", true);
                    	else
                    		redirectAttributes.addFlashAttribute("allUsers", userList);       
                    	return "redirect:/match"; 
        			}
        			
        			       			
        		}
        		
        		Match newMatch = new Match(userLogin, userMatch);
        		if (matchService.save(newMatch) != null) {
        			newMatch.setMatchStatus(MatchStatus.WAITING.getValue());        	    	
                	redirectAttributes.addFlashAttribute("msg", "waitingMatch"); 	    	
                	redirectAttributes.addFlashAttribute("userMatch", userMatch);
                	Collection<User> userList = matchService.findUsersOkToMatch(globalController.getLoginUser());
                	if(userList.isEmpty())
                		redirectAttributes.addFlashAttribute("isEmpty", true);
                	else
                		redirectAttributes.addFlashAttribute("allUsers", userList);
                	return "redirect:/match";
        		}
        		
	       
	        
	        } catch (Exception e) {
	            model.addAttribute("msg", "fail");
	            logger.error("requestMatch: " + e.getMessage());
	        }
	        Collection<User> userList = matchService.findUsersOkToMatch(globalController.getLoginUser());
        	if(userList.isEmpty())
        		redirectAttributes.addFlashAttribute("isEmpty", true);
        	else
        		redirectAttributes.addFlashAttribute("allUsers", userList);
	        return "match";         	            
	          
	        }


	    @RequestMapping(value = {"/match/ignore/{id}"},  method = RequestMethod.GET)
	    public String ignoreMatch(@PathVariable("id") int id,    		
														Model model,
														final RedirectAttributes redirectAttributes) {
	        logger.info("/match/ignore/" + id);
	        try {
	        	User userMatch = userService.findById(id);
	            User userLogin = globalController.getLoginUser();
	        	
	            Match match = matchService.checkIfMatchExists(userMatch, userLogin);	
        		
        		if (match != null) {
        			if (match.getMatchStatus().equals(MatchStatus.WAITING.getValue())) {
        				match.setMatchStatus(MatchStatus.IGNORED.getValue());        	    	
                    	redirectAttributes.addFlashAttribute("msg", "ignoredMatch"); 	    	
                    	redirectAttributes.addFlashAttribute("userMatch", userMatch);
                    	Collection<User> userList = matchService.findUsersOkToMatch(globalController.getLoginUser());
                    	if(userList.isEmpty())
                    		redirectAttributes.addFlashAttribute("isEmpty", true);
                    	else
                    		redirectAttributes.addFlashAttribute("allUsers", userList);
                    	
                    	return "redirect:/match"; 
        			}
        			
        			if (match.getMatchStatus().equals(MatchStatus.IGNORED.getValue())) {
        				match.setMatchStatus(MatchStatus.IGNORED.getValue());        	    	
                    	redirectAttributes.addFlashAttribute("msg", "ignoredMatch"); 	    	
                    	redirectAttributes.addFlashAttribute("userMatch", userMatch);
                    	Collection<User> userList = matchService.findUsersOkToMatch(globalController.getLoginUser());
                    	if(userList.isEmpty())
                    		redirectAttributes.addFlashAttribute("isEmpty", true);
                    	else
                    		redirectAttributes.addFlashAttribute("allUsers", userList);        
                    	return "redirect:/match"; 
        			}
        			    			
        		}
        		
        		Match newMatch = new Match(userLogin, userMatch);
        		if (matchService.save(newMatch) != null) {
        			newMatch.setMatchStatus(MatchStatus.IGNORED.getValue());        	    	
                	redirectAttributes.addFlashAttribute("msg", "ignoredMatch"); 	    	
                	redirectAttributes.addFlashAttribute("userMatch", userMatch);
                	Collection<User> userList = matchService.findUsersOkToMatch(globalController.getLoginUser());
                	if(userList.isEmpty())
                		redirectAttributes.addFlashAttribute("isEmpty", true);
                	else
                		redirectAttributes.addFlashAttribute("allUsers", userList);
                	return "redirect:/match";
        		}
        		
	       
	        
	        } catch (Exception e) {
	            model.addAttribute("msg", "fail");
	            logger.error("requestMatch: " + e.getMessage());
	        }
	        Collection<User> userList = matchService.findUsersOkToMatch(globalController.getLoginUser());
        	if(userList.isEmpty())
        		redirectAttributes.addFlashAttribute("isEmpty", true);
        	else
        		redirectAttributes.addFlashAttribute("allUsers", userList);
	        return "match";         	            
	          
	        }
	
	   
	    
}
