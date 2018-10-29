package br.com.projetoTcc.controller;

import java.util.List;

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
import br.com.projetoTcc.model.Task;
import br.com.projetoTcc.model.User;
import br.com.projetoTcc.service.CompetenceService;
import br.com.projetoTcc.service.TaskService;
import br.com.projetoTcc.service.UserService;
import br.com.projetoTcc.utils.PassEncoding;
import br.com.projetoTcc.utils.Roles;
import br.com.projetoTcc.utils.Status;


@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    GlobalController globalController;

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;
    
    @Autowired
	CompetenceService competenceService;
    


    @RequestMapping("/")
    public String root(Model model) {
        model.addAttribute("reqUser", new User());
        logger.info("root");
        return "login";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("reqUser", new User());
        logger.info("login");
        return "login";
    }

    @RequestMapping("/all-users")
    public String home2 (Model model) {
    	User user = new User();
    	model.addAttribute("allUsers", userService.findAll());
    	logger.info("all-users");
        return "all-users";
    }

    @RequestMapping("/home")
    public String home(Model model) {
       
        logger.info("home");
        return "home";
    }
    
    @RequestMapping("/task")
    public String task(Model model) {
        Task task =new Task();
        model.addAttribute("reqTask", task);
        model.addAttribute("allTask", taskService.findByUserIdStatus(globalController.getLoginUser().getId(), Status.ACTIVE.getValue()));
        model.addAttribute("allPassiveTask", taskService.findByUserIdStatus(globalController.getLoginUser().getId(), Status.PASSIVE.getValue()));
        logger.info("task");
        return "task";
    }

    @RequestMapping("/admin")
    public String helloAdmin() {
        logger.info("admin");
        return "admin";
    }
    
    @RequestMapping("/register")
    public String register(Model model) {
        model.addAttribute("reqUser", new User());
        logger.info("register");
        return "register";
    }
    
    @RequestMapping("/edit-user")
    public String editUser(Model model) {
        logger.info("editUser");
    	model.addAttribute("editUser", userService.findById((globalController.getLoginUser().getId())));
        return "edit-user";
    }
        
        
    @RequestMapping(value = {"/user/register"}, method = RequestMethod.POST)
    public String register(@ModelAttribute("reqUser") User reqUser,
                           final RedirectAttributes redirectAttributes) {

        logger.info("/user/register");
        User user = userService.findByUserName(reqUser.getUsername());
        if (user != null) {
            redirectAttributes.addFlashAttribute("saveUser", "exist-name");
            return "redirect:/register";
        }
        user = userService.findByEmail(reqUser.getEmail());
        if (user != null) {
            redirectAttributes.addFlashAttribute("saveUser", "exist-email");
            return "redirect:/register";
        }

        reqUser.setPassword(PassEncoding.getInstance().passwordEncoder.encode(reqUser.getPassword()));
        reqUser.setRole(Roles.ROLE_USER.getValue());
        
        if (userService.save(reqUser) != null) {
            redirectAttributes.addFlashAttribute("saveUser", "success");
        } else {
            redirectAttributes.addFlashAttribute("saveUser", "fail");
        }

        return "redirect:/register";
    }
    
    
    @RequestMapping(value = {"/user/editUser"}, method = RequestMethod.POST)
    public String editUser(@ModelAttribute("editedUser") User editUser,
    										Model model,
    										final RedirectAttributes redirectAttributes) {
        logger.info("/user/editUser");
        try {
            User user = userService.findById((globalController.getLoginUser().getId()));

            if (!user.equals(editUser)) {
            	
            	
            	if (!editUser.getEmail().equalsIgnoreCase(user.getEmail())) {
            		 User userEmailVerification = userService.findByEmail(editUser.getEmail());
            		 if (userEmailVerification != null) {
            	            redirectAttributes.addFlashAttribute("saveUser", "exist-email");
            	            return "redirect:/register";
            	     }
            	}
            	
            	if (editUser.getPassword() == null) {
            		editUser.setPassword(user.getPassword());
            	}
            	
            	
            	editUser.setRole(user.getRole());
            	
            	
            	if (userService.update(editUser) != null) {
            		redirectAttributes.addFlashAttribute("msg", "sucess");
            	} else {
                    redirectAttributes.addFlashAttribute("msg", "fail");
                }
            	
            	return "redirect:/edit-user";
            	
                
            } else {
                model.addAttribute("msg", "same");
            }
        } catch (Exception e) {
            model.addAttribute("msg", "fail");
            logger.error("editUser: " + e.getMessage());
        }
        model.addAttribute("editUser", editUser);
        return "edit-user";
    }
    

    @RequestMapping("/perfil")
    public String perfil(Model model) {
    	User user = userService.findById((globalController.getLoginUser().getId()));
        model.addAttribute("user", user);
        model.addAttribute("allCompetences", competenceService.findByUser(user));
        model.addAttribute("newCompetence", new Competence());
        logger.info("perfil");
        return "perfil";
    }
    
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
            for (Competence competence : user.getCompetence()) {
				if (competence.equals(newCompetence))
					isNew = false;
			}
            
            if (isNew) {
            	newCompetence.setUser(user);
            	user.getCompetence().add(newCompetence);
            	
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
            if (competenceService.delete(id)) {            	
            	redirectAttributes.addFlashAttribute("msg", "successDelete");
            	redirectAttributes.addFlashAttribute("userPerfil", user);
            	return "redirect:/perfil";
            }	         
            
          
        } catch (Exception e) {
            model.addAttribute("msg", "fail");
            logger.error("deleteCompetence: " + e.getMessage());
        }
        model.addAttribute("userPerfil", userService.findById(globalController.getLoginUser().getId()));
        return "perfil";
    }
    
}
