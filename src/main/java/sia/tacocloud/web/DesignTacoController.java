package sia.tacocloud.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sia.tacocloud.Ingredient;
import sia.tacocloud.Ingredient.Type;
import sia.tacocloud.Order;
import sia.tacocloud.Taco;
import sia.tacocloud.data.IngredientRepository;
import sia.tacocloud.data.TacoRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;
    private TacoRepository tacoRepository;

    @Autowired
    public DesignTacoController(
            IngredientRepository ingredientRepository,
            TacoRepository tacoRepository) {
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredients::add);

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    ingredients.stream().
                            filter(ingredient -> ingredient.getType().equals(type)).collect(Collectors.toList()));
        }

        model.addAttribute("design", new Taco());

        return "design";

    }

    @PostMapping
    public String processDesign (
            @Valid Taco design, Errors errors, @ModelAttribute Order order) {
        if (errors.hasErrors()) {
            return "design";
        }

        Taco newTaco = tacoRepository.save(design);

        log.info("Processing design: " + design);
        return "redirect:/orders/current";
    }

}