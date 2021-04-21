package fr.uga.ecom.bookstore.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BillingInfoMapperTest {

    private BillingInfoMapper billingInfoMapper;

    @BeforeEach
    public void setUp() {
        billingInfoMapper = new BillingInfoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(billingInfoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(billingInfoMapper.fromId(null)).isNull();
    }
}
