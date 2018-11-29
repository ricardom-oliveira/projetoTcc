package br.com.projetoTcc.controller;

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

import br.com.projetoTcc.model.Competence;
import br.com.projetoTcc.model.User;
import br.com.projetoTcc.service.CompetenceService;
import br.com.projetoTcc.service.UserService;

@Controller
public class PerfilController {
	   
		private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	    @Autowired
	    GlobalController globalController;
	    @Autowired
	    UserService userService;
	    
	    @Autowired
		CompetenceService competenceService;

	    @RequestMapping(value = {"/user/perfil/save"}, method = RequestMethod.POST)
	    public String saveCompetence (@ModelAttribute("userPerfil") Competence newCompetence,    		
	    										Model model,
	    										final RedirectAttributes redirectAttributes) {
	        logger.info("/user/perfil/save");
	        try {
	            User user = userService.findById(globalController.getLoginUser().getId());
	            if (newCompetence.getDescription() == null) {
	            	
	            	redirectAttributes.addFlashAttribute("msg", "descriptionError");
	            	return "redirect:/perfil";
	            }	
	            
	            if (newCompetence.getCategorie() == null) {
	            	
	            	redirectAttributes.addFlashAttribute("msg", "categorieError");
	            	return "redirect:/perfil";
	            }	
	           
	                        
	            boolean isNew = true;
	            for (Competence competence : user.getCompetences()) {
					if (competence.equals(newCompetence))
						isNew = false;
				}
	            
	            if (isNew) {
	            	newCompetence.setUser(user);
	            	user.getCompetences().add(newCompetence);
	            	
	            	if (userService.save(user) == null) {
	            		redirectAttributes.addFlashAttribute("msg", "fail");
	                	redirectAttributes.addFlashAttribute("userPerfil", user);
	            	}
	            	
	            	redirectAttributes.addFlashAttribute("msg", "successSave");
	            	redirectAttributes.addFlashAttribute("userPerfil", user);
	            	return "redirect:/perfil";
	            }
	            
	            
	          
	        } catch (Exception e) {
	            model.addAttribute("msg", "fail");
	            logger.error("saveCompetence: " + e.getMessage());
	        }
	        model.addAttribute("userPerfil", userService.findById(globalController.getLoginUser().getId()));
	        return "perfil";
	    }
	    
	    @RequestMapping(value = {"/user/perfil/delete/{id}"},  method = RequestMethod.GET)
	    public String deleteCompetence (@PathVariable("id") int id,    		
	    													Model model,
	    													final RedirectAttributes redirectAttributes) {
	        logger.info("/user/perfil/delete/" + id);
	        try {
	        	User user = userService.findById(globalController.getLoginUser().getId());
	        	Competence competence = competenceService.findById(id);
	            competenceService.delete(competence);           	
            	redirectAttributes.addFlashAttribute("msg", "successDelete");
            	redirectAttributes.addFlashAttribute("userPerfil", user);
            	return "redirect:/perfil";
	            
	          
	        } catch (Exception e) {
	            model.addAttribute("msg", "fail");
	            logger.error("deleteCompetence: " + e.getMessage());
	        }
	        model.addAttribute("userPerfil", userService.findById(globalController.getLoginUser().getId()));
	        return "perfil";
	    }
	    
	    
	    @RequestMapping("/perfil")
	    public String perfil(Model model) {
	    	User user = userService.findById((globalController.getLoginUser().getId()));
	        model.addAttribute("user", user);
	        model.addAttribute("age", user.getAge());
	        model.addAttribute("allCompetences", competenceService.findByUser(user));
	        model.addAttribute("newCompetence", new Competence());
	        logger.info("perfil");
	        return "perfil";
	    }
	    
}
