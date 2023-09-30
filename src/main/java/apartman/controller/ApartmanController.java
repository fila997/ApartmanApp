package apartman.controller;
import apartman.model.Apartman;
import apartman.model.User;
import apartman.model.UserDetails;
import apartman.repositories.ApartmanRepository;
import apartman.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class ApartmanController {
    @Autowired
    ApartmanRepository apartmanRepository;

    @Autowired
    UserRepository userRepository;






    @GetMapping("/apartman")
    public String prikaziApartmane (Model model,@AuthenticationPrincipal UserDetails userDetails) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        Long userId = userDetails.getUserId();
        model.addAttribute("apartmani", apartmanRepository.findAll());
        model.addAttribute("userId", userId);
        model.addAttribute("user", user);
        model.addAttribute("apartman", new Apartman());
        model.addAttribute("added", false);
        model.addAttribute("activeLink", "Igre");
        User userr = userDetails.getUser();
        Long userIdd = user.getUserId();

        return "apartman";
    }

    @PostMapping("/apartman/add")
    public String dodajApartman (@Valid Apartman apartman, BindingResult result, Model model, RedirectAttributes redirectAttributes, UserDetails userDetails) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        if (result.hasErrors()) {
            model.addAttribute("apartman", apartman);
            model.addAttribute("apartmani", apartmanRepository.findAll());
            model.addAttribute("added", true);

            return "apartman";
        }
        Long userIdd = user.getUserId();
        User selectedUser = userRepository.findById(userIdd).orElse(null);
        apartman.setUser(selectedUser);
        apartmanRepository.save(apartman);
        redirectAttributes.addFlashAttribute("success", "Apartman je uspješno dodan!");
        return "redirect:/apartman";
    }



    @GetMapping("/apartman/edit/{id}")
    public String showEdit(@PathVariable("id") Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        model.addAttribute("user", user);
        Apartman apartman = apartmanRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        model.addAttribute("apartman", apartman);
        model.addAttribute("apartmani", apartmanRepository.findAll());



        return "apartman_edit";
    }

    @PostMapping("apartman/edit/{id}")
    public String urediApartman (@PathVariable("id") Long id, @Valid Apartman apartman, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        if (result.hasErrors()) {
            model.addAttribute("apartman", apartman);
            return "apartman_edit";
        }
        Long userIdd = user.getUserId();
        User selectedUser = userRepository.findById(userIdd).orElse(null);
        apartman.setUser(selectedUser);
        apartmanRepository.save(apartman);
        redirectAttributes.addFlashAttribute("success", "Apartman je uspješno uredjen!");
        return "redirect:/apartman";
    }


    @GetMapping("/apartman/delete/{id}")
    public String izbrisiApartman(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {



        try {
            apartmanRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Apartman je uspješno izbrisan!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Izbrisite rezervaciju za ovaj apartman kako bi mogli da izbrisete apartman");
        }
        return "redirect:/apartman";
    }

}
