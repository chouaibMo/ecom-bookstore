package fr.uga.ecom.bookstore.web.rest;

import fr.uga.ecom.bookstore.BookstoreApp;
import fr.uga.ecom.bookstore.domain.BillingInfo;
import fr.uga.ecom.bookstore.repository.BillingInfoRepository;
import fr.uga.ecom.bookstore.service.BillingInfoService;
import fr.uga.ecom.bookstore.service.dto.BillingInfoDTO;
import fr.uga.ecom.bookstore.service.mapper.BillingInfoMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.uga.ecom.bookstore.domain.enumeration.PaymentMethod;
/**
 * Integration tests for the {@link BillingInfoResource} REST controller.
 */
@SpringBootTest(classes = BookstoreApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BillingInfoResourceIT {

    private static final String DEFAULT_INFO_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_INFO_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CARD_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CARD_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CARD_EXPIRY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CARD_EXPIRY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CRYPTOGRAM = "AAAAAAAAAA";
    private static final String UPDATED_CRYPTOGRAM = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final PaymentMethod DEFAULT_BILLING_METHOD = PaymentMethod.CREDITCARD;
    private static final PaymentMethod UPDATED_BILLING_METHOD = PaymentMethod.PAYPAL;

    @Autowired
    private BillingInfoRepository billingInfoRepository;

    @Autowired
    private BillingInfoMapper billingInfoMapper;

    @Autowired
    private BillingInfoService billingInfoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBillingInfoMockMvc;

    private BillingInfo billingInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BillingInfo createEntity(EntityManager em) {
        BillingInfo billingInfo = new BillingInfo()
            .infoTitle(DEFAULT_INFO_TITLE)
            .cardNumber(DEFAULT_CARD_NUMBER)
            .cardExpiryDate(DEFAULT_CARD_EXPIRY_DATE)
            .cryptogram(DEFAULT_CRYPTOGRAM)
            .email(DEFAULT_EMAIL)
            .billingMethod(DEFAULT_BILLING_METHOD);
        return billingInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BillingInfo createUpdatedEntity(EntityManager em) {
        BillingInfo billingInfo = new BillingInfo()
            .infoTitle(UPDATED_INFO_TITLE)
            .cardNumber(UPDATED_CARD_NUMBER)
            .cardExpiryDate(UPDATED_CARD_EXPIRY_DATE)
            .cryptogram(UPDATED_CRYPTOGRAM)
            .email(UPDATED_EMAIL)
            .billingMethod(UPDATED_BILLING_METHOD);
        return billingInfo;
    }

    @BeforeEach
    public void initTest() {
        billingInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createBillingInfo() throws Exception {
        int databaseSizeBeforeCreate = billingInfoRepository.findAll().size();
        // Create the BillingInfo
        BillingInfoDTO billingInfoDTO = billingInfoMapper.toDto(billingInfo);
        restBillingInfoMockMvc.perform(post("/api/billing-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billingInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the BillingInfo in the database
        List<BillingInfo> billingInfoList = billingInfoRepository.findAll();
        assertThat(billingInfoList).hasSize(databaseSizeBeforeCreate + 1);
        BillingInfo testBillingInfo = billingInfoList.get(billingInfoList.size() - 1);
        assertThat(testBillingInfo.getInfoTitle()).isEqualTo(DEFAULT_INFO_TITLE);
        assertThat(testBillingInfo.getCardNumber()).isEqualTo(DEFAULT_CARD_NUMBER);
        assertThat(testBillingInfo.getCardExpiryDate()).isEqualTo(DEFAULT_CARD_EXPIRY_DATE);
        assertThat(testBillingInfo.getCryptogram()).isEqualTo(DEFAULT_CRYPTOGRAM);
        assertThat(testBillingInfo.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testBillingInfo.getBillingMethod()).isEqualTo(DEFAULT_BILLING_METHOD);
    }

    @Test
    @Transactional
    public void createBillingInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billingInfoRepository.findAll().size();

        // Create the BillingInfo with an existing ID
        billingInfo.setId(1L);
        BillingInfoDTO billingInfoDTO = billingInfoMapper.toDto(billingInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillingInfoMockMvc.perform(post("/api/billing-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billingInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BillingInfo in the database
        List<BillingInfo> billingInfoList = billingInfoRepository.findAll();
        assertThat(billingInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkBillingMethodIsRequired() throws Exception {
        int databaseSizeBeforeTest = billingInfoRepository.findAll().size();
        // set the field null
        billingInfo.setBillingMethod(null);

        // Create the BillingInfo, which fails.
        BillingInfoDTO billingInfoDTO = billingInfoMapper.toDto(billingInfo);


        restBillingInfoMockMvc.perform(post("/api/billing-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billingInfoDTO)))
            .andExpect(status().isBadRequest());

        List<BillingInfo> billingInfoList = billingInfoRepository.findAll();
        assertThat(billingInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBillingInfos() throws Exception {
        // Initialize the database
        billingInfoRepository.saveAndFlush(billingInfo);

        // Get all the billingInfoList
        restBillingInfoMockMvc.perform(get("/api/billing-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billingInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].infoTitle").value(hasItem(DEFAULT_INFO_TITLE)))
            .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DEFAULT_CARD_NUMBER)))
            .andExpect(jsonPath("$.[*].cardExpiryDate").value(hasItem(DEFAULT_CARD_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].cryptogram").value(hasItem(DEFAULT_CRYPTOGRAM)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].billingMethod").value(hasItem(DEFAULT_BILLING_METHOD.toString())));
    }
    
    @Test
    @Transactional
    public void getBillingInfo() throws Exception {
        // Initialize the database
        billingInfoRepository.saveAndFlush(billingInfo);

        // Get the billingInfo
        restBillingInfoMockMvc.perform(get("/api/billing-infos/{id}", billingInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(billingInfo.getId().intValue()))
            .andExpect(jsonPath("$.infoTitle").value(DEFAULT_INFO_TITLE))
            .andExpect(jsonPath("$.cardNumber").value(DEFAULT_CARD_NUMBER))
            .andExpect(jsonPath("$.cardExpiryDate").value(DEFAULT_CARD_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.cryptogram").value(DEFAULT_CRYPTOGRAM))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.billingMethod").value(DEFAULT_BILLING_METHOD.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingBillingInfo() throws Exception {
        // Get the billingInfo
        restBillingInfoMockMvc.perform(get("/api/billing-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBillingInfo() throws Exception {
        // Initialize the database
        billingInfoRepository.saveAndFlush(billingInfo);

        int databaseSizeBeforeUpdate = billingInfoRepository.findAll().size();

        // Update the billingInfo
        BillingInfo updatedBillingInfo = billingInfoRepository.findById(billingInfo.getId()).get();
        // Disconnect from session so that the updates on updatedBillingInfo are not directly saved in db
        em.detach(updatedBillingInfo);
        updatedBillingInfo
            .infoTitle(UPDATED_INFO_TITLE)
            .cardNumber(UPDATED_CARD_NUMBER)
            .cardExpiryDate(UPDATED_CARD_EXPIRY_DATE)
            .cryptogram(UPDATED_CRYPTOGRAM)
            .email(UPDATED_EMAIL)
            .billingMethod(UPDATED_BILLING_METHOD);
        BillingInfoDTO billingInfoDTO = billingInfoMapper.toDto(updatedBillingInfo);

        restBillingInfoMockMvc.perform(put("/api/billing-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billingInfoDTO)))
            .andExpect(status().isOk());

        // Validate the BillingInfo in the database
        List<BillingInfo> billingInfoList = billingInfoRepository.findAll();
        assertThat(billingInfoList).hasSize(databaseSizeBeforeUpdate);
        BillingInfo testBillingInfo = billingInfoList.get(billingInfoList.size() - 1);
        assertThat(testBillingInfo.getInfoTitle()).isEqualTo(UPDATED_INFO_TITLE);
        assertThat(testBillingInfo.getCardNumber()).isEqualTo(UPDATED_CARD_NUMBER);
        assertThat(testBillingInfo.getCardExpiryDate()).isEqualTo(UPDATED_CARD_EXPIRY_DATE);
        assertThat(testBillingInfo.getCryptogram()).isEqualTo(UPDATED_CRYPTOGRAM);
        assertThat(testBillingInfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testBillingInfo.getBillingMethod()).isEqualTo(UPDATED_BILLING_METHOD);
    }

    @Test
    @Transactional
    public void updateNonExistingBillingInfo() throws Exception {
        int databaseSizeBeforeUpdate = billingInfoRepository.findAll().size();

        // Create the BillingInfo
        BillingInfoDTO billingInfoDTO = billingInfoMapper.toDto(billingInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillingInfoMockMvc.perform(put("/api/billing-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(billingInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BillingInfo in the database
        List<BillingInfo> billingInfoList = billingInfoRepository.findAll();
        assertThat(billingInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBillingInfo() throws Exception {
        // Initialize the database
        billingInfoRepository.saveAndFlush(billingInfo);

        int databaseSizeBeforeDelete = billingInfoRepository.findAll().size();

        // Delete the billingInfo
        restBillingInfoMockMvc.perform(delete("/api/billing-infos/{id}", billingInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BillingInfo> billingInfoList = billingInfoRepository.findAll();
        assertThat(billingInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
