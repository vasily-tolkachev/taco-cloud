package sia.tacocloud.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import sia.tacocloud.data.TacoRepository;
import sia.tacocloud.model.Taco;

import javax.swing.text.html.parser.Entity;
import java.util.Optional;

@RestController
@RequestMapping(path = "/design",
        produces = "application/json")
public class DesignTacoRestController {

    private TacoRepository tacoRepository;

  /*  @Autowired
    EntityLinks entityLinks;*/

    public DesignTacoRestController(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    @GetMapping("/resent")
    public Iterable<Taco> recentTacos() {
       // PageRequest pageRequest = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        return tacoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Taco tacoById(@PathVariable("id") Long id) {
        Optional<Taco> optionalTaco = tacoRepository.findById(id);
        return optionalTaco.orElse(null);
    }

}
