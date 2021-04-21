package fr.uga.ecom.bookstore.web.rest;

import fr.uga.ecom.bookstore.BookstoreApp;
import fr.uga.ecom.bookstore.domain.CustomUser;
import fr.uga.ecom.bookstore.repository.CustomUserRepository;
import fr.uga.ecom.bookstore.service.CustomUserService;
import fr.uga.ecom.bookstore.service.dto.CustomUserDTO;
import fr.uga.ecom.bookstore.service.mapper.CustomUserMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CustomUserResource} REST controller.
 */
@SpringBootTest(classes = BookstoreApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustomUserResourceIT {

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private CustomUserMapper customUserMapper;

    @Autowired
    private CustomUserService customUserService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomUserMockMvc;

    private CustomUser customUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomUser createEntity(EntityManager em) {
        CustomUser customUser = new CustomUser()
            .phoneNumber(DEFAULT_PHONE_NUMBER);
        return customUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomUser createUpdatedEntity(EntityManager em) {
        CustomUser customUser = new CustomUser()
            .phoneNumber(UPDATED_PHONE_NUMBER);
        return customUser;
    }

    @BeforeEach
    public void initTest() {
        customUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomUser() throws Exception {
        int databaseSizeBeforeCreate = customUserRepository.findAll().size();
        // Create the CustomUser
        CustomUserDTO customUserDTO = customUserMapper.toDto(customUser);
        restCustomUserMockMvc.perform(post("/api/custom-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customUserDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomUser in the database
        List<CustomUser> customUserList = customUserRepository.findAll();
        assertThat(customUserList).hasSize(databaseSizeBeforeCreate + 1);
        CustomUser testCustomUser = customUserList.get(customUserList.size() - 1);
        assertThat(testCustomUser.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void createCustomUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customUserRepository.findAll().size();

        // Create the CustomUser with an existing ID
        customUser.setId(1L);
        CustomUserDTO customUserDTO = customUserMapper.toDto(customUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomUserMockMvc.perform(post("/api/custom-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomUser in the database
        List<CustomUser> customUserList = customUserRepository.findAll();
        assertThat(customUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCustomUsers() throws Exception {
        // Initialize the database
        customUserRepository.saveAndFlush(customUser);

        // Get all the customUserList
        restCustomUserMockMvc.perform(get("/api/custom-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getCustomUser() throws Exception {
        // Initialize the database
        customUserRepository.saveAndFlush(customUser);

        // Get the customUser
        restCustomUserMockMvc.perform(get("/api/custom-users/{id}", customUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customUser.getId().intValue()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER));
    }
    @Test
    @Transactional
    public void getNonExistingCustomUser() throws Exception {
        // Get the customUser
        restCustomUserMockMvc.perform(get("/api/custom-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomUser() throws Exception {
        // Initialize the database
        customUserRepository.saveAndFlush(customUser);

        int databaseSizeBeforeUpdate = customUserRepository.findAll().size();

        // Update the customUser
        CustomUser updatedCustomUser = customUserRepository.findById(customUser.getId()).get();
        // Disconnect from session so that the updates on updatedCustomUser are not directly saved in db
        em.detach(updatedCustomUser);
        updatedCustomUser
            .phoneNumber(UPDATED_PHONE_NUMBER);
        CustomUserDTO customUserDTO = customUserMapper.toDto(updatedCustomUser);

        restCustomUserMockMvc.perform(put("/api/custom-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customUserDTO)))
            .andExpect(status().isOk());

        // Validate the CustomUser in the database
        List<CustomUser> customUserList = customUserRepository.findAll();
        assertThat(customUserList).hasSize(databaseSizeBeforeUpdate);
        CustomUser testCustomUser = customUserList.get(customUserList.size() - 1);
        assertThat(testCustomUser.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomUser() throws Exception {
        int databaseSizeBeforeUpdate = customUserRepository.findAll().size();

        // Create the CustomUser
        CustomUserDTO customUserDTO = customUserMapper.toDto(customUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomUserMockMvc.perform(put("/api/custom-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomUser in the database
        List<CustomUser> customUserList = customUserRepository.findAll();
        assertThat(customUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomUser() throws Exception {
        // Initialize the database
        customUserRepository.saveAndFlush(customUser);

        int databaseSizeBeforeDelete = customUserRepository.findAll().size();

        // Delete the customUser
        restCustomUserMockMvc.perform(delete("/api/custom-users/{id}", customUser.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomUser> customUserList = customUserRepository.findAll();
        assertThat(customUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
