package fr.uga.ecom.bookstore.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerCommentMapperTest {

    private CustomerCommentMapper customerCommentMapper;

    @BeforeEach
    public void setUp() {
        customerCommentMapper = new CustomerCommentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(customerCommentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(customerCommentMapper.fromId(null)).isNull();
    }
}
