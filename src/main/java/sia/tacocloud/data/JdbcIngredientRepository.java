package sia.tacocloud.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sia.tacocloud.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {
    private static final String SELECT_INGREDIENT = "select id, name. type from Ingredient where id=?";
    private static final String SELECT_ALL_INGREDIENTS = "select id, name, type from Ingredient";
    public static final String INSERT_INGREDIENT = "insert into Ingredient (id, name, type) values (?,?,?)";

    private JdbcTemplate jdbc;

    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {
        return new Ingredient(
                rs.getString("id"),
                rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("type"))
        );
    }

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Ingredient findOne(String id) {
        return jdbc.queryForObject(SELECT_INGREDIENT, this::mapRowToIngredient, id);
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbc.query(SELECT_ALL_INGREDIENTS, this::mapRowToIngredient);
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbc.update(INSERT_INGREDIENT,
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString()
        );
        return null;
    }

}
