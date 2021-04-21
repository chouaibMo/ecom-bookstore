package fr.uga.ecom.bookstore.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.uga.ecom.bookstore.web.rest.TestUtil;

public class CustomUserDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomUserDTO.class);
        CustomUserDTO customUserDTO1 = new CustomUserDTO();
        customUserDTO1.setId(1L);
        CustomUserDTO customUserDTO2 = new CustomUserDTO();
        assertThat(customUserDTO1).isNotEqualTo(customUserDTO2);
        customUserDTO2.setId(customUserDTO1.getId());
        assertThat(customUserDTO1).isEqualTo(customUserDTO2);
        customUserDTO2.setId(2L);
        assertThat(customUserDTO1).isNotEqualTo(customUserDTO2);
        customUserDTO1.setId(null);
        assertThat(customUserDTO1).isNotEqualTo(customUserDTO2);
    }
}
