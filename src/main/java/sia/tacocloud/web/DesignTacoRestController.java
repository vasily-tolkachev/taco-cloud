package sia.tacocloud.web;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sia.tacocloud.data.TacoRepository;
import sia.tacocloud.model.Taco;

import javax.swing.text.html.parser.Entity;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

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
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        Optional<Taco> optionalTaco = tacoRepository.findById(id);
        return optionalTaco.map(taco -> new ResponseEntity<>(taco, OK))
                .orElseGet(() -> new ResponseEntity<>((Taco) null, NOT_FOUND));
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(CREATED)
    public Taco postTaco(@RequestBody Taco taco) {
        System.out.println(taco);
        return tacoRepository.save(taco);
    }

   /* @PutMapping("/{tacoId}")
    public Taco putTaco(@RequestBody Taco taco) {
        return tacoRepository.save(taco);
    }*/

    @DeleteMapping("/{tacoId}")
    public ResponseEntity<Taco> deleteTaco(@PathVariable("tacoId") long tacoId) {
        System.out.println("Fetching & Deleting Taco with id " + tacoId);
        try {
            tacoRepository.deleteById(tacoId);
            return new ResponseEntity<Taco>(HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<Taco>(HttpStatus.NOT_FOUND);
        }
    }

}

