package sia.tacocloud;

import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class Taco {
    String name;
    List<Ingredient> ingredients;
}
