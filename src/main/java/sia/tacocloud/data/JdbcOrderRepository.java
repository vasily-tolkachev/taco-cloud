package sia.tacocloud.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import sia.tacocloud.Order;
import sia.tacocloud.Taco;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcOrderRepository implements OrderRepository {
    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;


    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
        orderInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Taco_Order")
                .usingGeneratedKeyColumns("id");

        orderTacoInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Taco_Order_Tacos");

    }


    @Override
    public Order save(Order order) {
        order.setPlacedAt(new Date());
        long orderId = saveOrderDetails(order);
        order.setId(orderId);
        List<Taco> tacos = order.getTacos();
        for (Taco taco: tacos) {
            saveTacoToOrder(taco, orderId);
        }
        return order;
    }

    private void saveTacoToOrder(Taco taco, long orderId) {
        Map<String, Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());
        orderTacoInserter.execute(values);
    }

    private long saveOrderDetails(Order order) {
        Map<String, Object> values = new HashMap<>();
        values.put("deliveryName", order.getName());
        values.put("deliveryStreet", order.getStreet());
        values.put("deliveryCity", order.getCity());
        values.put("deliveryZip", order.getZip());
        values.put("deliveryState", order.getState());
        values.put("placedAt", order.getPlacedAt());
        return orderInserter
                .executeAndReturnKey(values)
                .longValue();
    }
}
