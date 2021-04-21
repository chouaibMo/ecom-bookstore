package fr.uga.ecom.bookstore.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.uga.ecom.bookstore.web.rest.TestUtil;

public class BillingInfoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillingInfoDTO.class);
        BillingInfoDTO billingInfoDTO1 = new BillingInfoDTO();
        billingInfoDTO1.setId(1L);
        BillingInfoDTO billingInfoDTO2 = new BillingInfoDTO();
        assertThat(billingInfoDTO1).isNotEqualTo(billingInfoDTO2);
        billingInfoDTO2.setId(billingInfoDTO1.getId());
        assertThat(billingInfoDTO1).isEqualTo(billingInfoDTO2);
        billingInfoDTO2.setId(2L);
        assertThat(billingInfoDTO1).isNotEqualTo(billingInfoDTO2);
        billingInfoDTO1.setId(null);
        assertThat(billingInfoDTO1).isNotEqualTo(billingInfoDTO2);
    }
}
