package sia.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.model.Taco;

public interface TacoRepository
        extends CrudRepository<Taco, Long> {
}
