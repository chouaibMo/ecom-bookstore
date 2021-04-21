package fr.uga.ecom.bookstore.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.uga.ecom.bookstore.web.rest.TestUtil;

public class CustomerCommentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerCommentDTO.class);
        CustomerCommentDTO customerCommentDTO1 = new CustomerCommentDTO();
        customerCommentDTO1.setId(1L);
        CustomerCommentDTO customerCommentDTO2 = new CustomerCommentDTO();
        assertThat(customerCommentDTO1).isNotEqualTo(customerCommentDTO2);
        customerCommentDTO2.setId(customerCommentDTO1.getId());
        assertThat(customerCommentDTO1).isEqualTo(customerCommentDTO2);
        customerCommentDTO2.setId(2L);
        assertThat(customerCommentDTO1).isNotEqualTo(customerCommentDTO2);
        customerCommentDTO1.setId(null);
        assertThat(customerCommentDTO1).isNotEqualTo(customerCommentDTO2);
    }
}
