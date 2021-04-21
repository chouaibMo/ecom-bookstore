package fr.uga.ecom.bookstore.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.uga.ecom.bookstore.web.rest.TestUtil;

public class DiscountDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiscountDTO.class);
        DiscountDTO discountDTO1 = new DiscountDTO();
        discountDTO1.setId(1L);
        DiscountDTO discountDTO2 = new DiscountDTO();
        assertThat(discountDTO1).isNotEqualTo(discountDTO2);
        discountDTO2.setId(discountDTO1.getId());
        assertThat(discountDTO1).isEqualTo(discountDTO2);
        discountDTO2.setId(2L);
        assertThat(discountDTO1).isNotEqualTo(discountDTO2);
        discountDTO1.setId(null);
        assertThat(discountDTO1).isNotEqualTo(discountDTO2);
    }
}
