package fr.uga.ecom.bookstore.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderLineMapperTest {

    private OrderLineMapper orderLineMapper;

    @BeforeEach
    public void setUp() {
        orderLineMapper = new OrderLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(orderLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(orderLineMapper.fromId(null)).isNull();
    }
}
