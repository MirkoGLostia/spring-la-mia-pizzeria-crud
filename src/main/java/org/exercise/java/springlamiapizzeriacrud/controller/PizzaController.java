package org.exercise.java.springlamiapizzeriacrud.controller;


import jakarta.validation.Valid;
import org.exercise.java.springlamiapizzeriacrud.model.Pizza;
import org.exercise.java.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.Binding;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;


    @GetMapping ("pizza-list")
    public String pizzaMenu(@RequestParam Optional<String> search, Model model) {
        List<Pizza> pizzaList;

        if (search.isPresent()) {
            pizzaList = pizzaRepository.findByNameContainingIgnoreCase(search.get());
        } else {
            pizzaList = pizzaRepository.findAll();
        }


        model.addAttribute("area", "pizza-list");
        model.addAttribute("pizzaList", pizzaList);
        return "pizzas/list";
    }


    @GetMapping("pizza-list/detail/{id}")
    public String pizzaDetail(@PathVariable Integer id, Model model) {
        Optional<Pizza> result = pizzaRepository.findById(id);

        model.addAttribute("area", "detail-pizza");

        if (result.isPresent()) {
            model.addAttribute("pizza", result.get());
            return "pizzas/detail";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "book with id " + id + " not found");
        }
    }


    @GetMapping("pizza/create")
    public String createPizza(Model model) {
        model.addAttribute("area", "pizza-create");
        model.addAttribute("pizza", new Pizza());
        return "pizzas/create";
    }

    @PostMapping("pizza/create")
    public String doCreatePizza(Model model, @Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult) {
        model.addAttribute("area", "pizza-create");

        if (bindingResult.hasErrors()) {
            return "pizzas/create";
        }


        Pizza savedPizza = pizzaRepository.save(formPizza);
        return "redirect:/pizza-list/detail/" + savedPizza.getId();
    }




}
