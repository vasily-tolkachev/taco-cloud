package sia.tacocloud.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sia.tacocloud.Ingredient;
import sia.tacocloud.Taco;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcTacoRepository implements TacoRepository {
    private static final String INSERT_TACO = "insert into Taco (name, createdAt) values (?, ?)";
    private static final String INSERT_INGREDIENTS_OF_TACO = "insert into Taco_Ingredients(taco, ingredient) values (?,?)";
    private JdbcTemplate jdbc;

    public JdbcTacoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        System.out.println(taco);
        for (String ingredientId : taco.getIngredients()) {
            saveTacoIngredientToTaco(ingredientId, tacoId);
        }
        return taco;
    }

    private void saveTacoIngredientToTaco(String ingredientId, long tacoId) {
        jdbc.update(INSERT_INGREDIENTS_OF_TACO, tacoId, ingredientId);
    }

    private long saveTacoInfo(Taco taco) {
        taco.setCreatedAt(new Date());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement (INSERT_TACO, new String[]{"ID"});
                    ps.setString(1, taco.getName());
                    ps.setTimestamp(2, new Timestamp(taco.getCreatedAt().getTime()));
                    return ps;
                }, keyHolder);
        return keyHolder.getKey().longValue();
    }


}
