package apartman.controller;

import apartman.model.Apartman;
import apartman.model.Rezervacija;
import apartman.model.User;
import apartman.model.UserDetails;
import apartman.model.*;
import apartman.repositories.ApartmanRepository;
import apartman.repositories.RezervacijeRepostiroy;
import apartman.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class RezervacijeController {

    @Autowired
    RezervacijeRepostiroy rezervacijeRepostiroy;

    @Autowired
    ApartmanRepository apartmanRepository;

    @Autowired
    UserRepository userRepository;


    @GetMapping("/sve_rezervacije")
    public String prikaziRezervacije (Model model,@AuthenticationPrincipal UserDetails userDetails) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        Long userId = userDetails.getUserId(); // ili koristite metodu kojom dobavljate ID korisnika
        List<Apartman> apartman = apartmanRepository.findAll();
        System.out.println(apartman.size());
        model.addAttribute("rezervacije", rezervacijeRepostiroy.findAll());
        model.addAttribute("apartman", apartman);
        model.addAttribute("userId", userId);
        model.addAttribute("user", user);
        model.addAttribute("rezervacija", new Rezervacija());
        model.addAttribute("added", false);
        model.addAttribute("activeLink", "Igre");
        User userr = userDetails.getUser();

        Long userIdd = user.getUserId();


        return "sve_rezervacije";
    }


    @GetMapping("/reserve/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        model.addAttribute("user", user);
        Apartman apartman = apartmanRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
        model.addAttribute("apartman", apartman);
        model.addAttribute("rezervacija", new Rezervacija());
        model.addAttribute("apartmani", apartmanRepository.findAll());



        return "reserve";
    }

    @PostMapping("/reserve/add/{id}")
    public String dodajRezervaciju (@PathVariable("id") Long id, Rezervacija rezervacija, BindingResult result, Model model, RedirectAttributes redirectAttributes, UserDetails userDetails) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();




        if (result.hasErrors()) {
            List<Apartman> apartmans = apartmanRepository.findAll();
            model.addAttribute("apartmani", apartmans);
            model.addAttribute("rezervacija", new Rezervacija());
            model.addAttribute("rezervacije", rezervacijeRepostiroy.findAll());
            model.addAttribute("added", true);

            return "apartman";
        }
        Long userIdd = user.getUserId();
        User selectedUser = userRepository.findById(userIdd).orElse(null);
        rezervacija.setUser(selectedUser);

        Apartman selectedApartman = apartmanRepository.findById(id).orElse(null);
        rezervacija.setApartman(selectedApartman);
        Rezervacija existingReserve= rezervacijeRepostiroy.findByCreatedByAndApartman(selectedUser, selectedApartman);
        if (existingReserve != null) {
            redirectAttributes.addFlashAttribute("error", "Vec ste rezervirali ovaj apartman.");
            return "redirect:/reserve/" + id;
        }
        rezervacijeRepostiroy.save(rezervacija);
        redirectAttributes.addFlashAttribute("success", "Uspjesna rezervacija!");
        return "redirect:/apartman";
    }

    @GetMapping("/apartman/delete-reserve/{id}")
    public String izbrisiRezervaciju(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {

        Rezervacija rezervacija = rezervacijeRepostiroy.findById(id).orElseThrow(() -> new IllegalArgumentException("Pogrešan ID"));
        rezervacijeRepostiroy.delete(rezervacija);
        redirectAttributes.addFlashAttribute("success", "Rezervacija  je uspješno izbrisana!");


        return "redirect:/sve_rezervacije";
    }


}
