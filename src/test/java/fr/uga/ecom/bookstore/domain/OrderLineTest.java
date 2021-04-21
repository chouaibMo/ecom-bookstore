package fr.uga.ecom.bookstore.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.uga.ecom.bookstore.web.rest.TestUtil;

public class OrderLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderLine.class);
        OrderLine orderLine1 = new OrderLine();
        orderLine1.setId(1L);
        OrderLine orderLine2 = new OrderLine();
        orderLine2.setId(orderLine1.getId());
        assertThat(orderLine1).isEqualTo(orderLine2);
        orderLine2.setId(2L);
        assertThat(orderLine1).isNotEqualTo(orderLine2);
        orderLine1.setId(null);
        assertThat(orderLine1).isNotEqualTo(orderLine2);
    }
}