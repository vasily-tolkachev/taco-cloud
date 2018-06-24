package sia.tacocloud;

import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;


@Data
@FieldDefaults(level = PRIVATE)
public class Taco {

    Long id;
    Date createdAt;

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    String name;

    @NotNull
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    List<String> ingredients;
}