package fr.uga.ecom.bookstore.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.uga.ecom.bookstore.web.rest.TestUtil;

public class CustomerCommentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerComment.class);
        CustomerComment customerComment1 = new CustomerComment();
        customerComment1.setId(1L);
        CustomerComment customerComment2 = new CustomerComment();
        customerComment2.setId(customerComment1.getId());
        assertThat(customerComment1).isEqualTo(customerComment2);
        customerComment2.setId(2L);
        assertThat(customerComment1).isNotEqualTo(customerComment2);
        customerComment1.setId(null);
        assertThat(customerComment1).isNotEqualTo(customerComment2);
    }
}
