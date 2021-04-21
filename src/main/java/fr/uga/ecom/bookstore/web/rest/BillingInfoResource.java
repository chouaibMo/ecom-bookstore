package fr.uga.ecom.bookstore.web.rest;

import fr.uga.ecom.bookstore.service.BillingInfoService;
import fr.uga.ecom.bookstore.web.rest.errors.BadRequestAlertException;
import fr.uga.ecom.bookstore.service.dto.BillingInfoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.uga.ecom.bookstore.domain.BillingInfo}.
 */
@RestController
@RequestMapping("/api")
public class BillingInfoResource {

    private final Logger log = LoggerFactory.getLogger(BillingInfoResource.class);

    private static final String ENTITY_NAME = "billingInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BillingInfoService billingInfoService;

    public BillingInfoResource(BillingInfoService billingInfoService) {
        this.billingInfoService = billingInfoService;
    }

    /**
     * {@code POST  /billing-infos} : Create a new billingInfo.
     *
     * @param billingInfoDTO the billingInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new billingInfoDTO, or with status {@code 400 (Bad Request)} if the billingInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/billing-infos")
    public ResponseEntity<BillingInfoDTO> createBillingInfo(@Valid @RequestBody BillingInfoDTO billingInfoDTO) throws URISyntaxException {
        log.debug("REST request to save BillingInfo : {}", billingInfoDTO);
        if (billingInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new billingInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BillingInfoDTO result = billingInfoService.save(billingInfoDTO);
        return ResponseEntity.created(new URI("/api/billing-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /billing-infos} : Updates an existing billingInfo.
     *
     * @param billingInfoDTO the billingInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated billingInfoDTO,
     * or with status {@code 400 (Bad Request)} if the billingInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the billingInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/billing-infos")
    public ResponseEntity<BillingInfoDTO> updateBillingInfo(@Valid @RequestBody BillingInfoDTO billingInfoDTO) throws URISyntaxException {
        log.debug("REST request to update BillingInfo : {}", billingInfoDTO);
        if (billingInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BillingInfoDTO result = billingInfoService.save(billingInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, billingInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /billing-infos} : get all the billingInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of billingInfos in body.
     */
    @GetMapping("/billing-infos")
    public List<BillingInfoDTO> getAllBillingInfos() {
        log.debug("REST request to get all BillingInfos");
        return billingInfoService.findAll();
    }

    /**
     * {@code GET  /billing-infos/:id} : get the "id" billingInfo.
     *
     * @param id the id of the billingInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the billingInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/billing-infos/{id}")
    public ResponseEntity<BillingInfoDTO> getBillingInfo(@PathVariable Long id) {
        log.debug("REST request to get BillingInfo : {}", id);
        Optional<BillingInfoDTO> billingInfoDTO = billingInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(billingInfoDTO);
    }

    /**
     * {@code DELETE  /billing-infos/:id} : delete the "id" billingInfo.
     *
     * @param id the id of the billingInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/billing-infos/{id}")
    public ResponseEntity<Void> deleteBillingInfo(@PathVariable Long id) {
        log.debug("REST request to delete BillingInfo : {}", id);
        billingInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
