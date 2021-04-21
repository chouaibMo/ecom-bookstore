package fr.uga.ecom.bookstore.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomUserMapperTest {

    private CustomUserMapper customUserMapper;

    @BeforeEach
    public void setUp() {
        customUserMapper = new CustomUserMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(customUserMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(customUserMapper.fromId(null)).isNull();
    }
}
