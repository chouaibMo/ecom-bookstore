package fr.uga.ecom.bookstore.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.uga.ecom.bookstore.web.rest.TestUtil;

public class BillingInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillingInfo.class);
        BillingInfo billingInfo1 = new BillingInfo();
        billingInfo1.setId(1L);
        BillingInfo billingInfo2 = new BillingInfo();
        billingInfo2.setId(billingInfo1.getId());
        assertThat(billingInfo1).isEqualTo(billingInfo2);
        billingInfo2.setId(2L);
        assertThat(billingInfo1).isNotEqualTo(billingInfo2);
        billingInfo1.setId(null);
        assertThat(billingInfo1).isNotEqualTo(billingInfo2);
    }
}
