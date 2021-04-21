package fr.uga.ecom.bookstore.web.rest;

import fr.uga.ecom.bookstore.service.CustomUserService;
import fr.uga.ecom.bookstore.web.rest.errors.BadRequestAlertException;
import fr.uga.ecom.bookstore.service.dto.CustomUserDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.uga.ecom.bookstore.domain.CustomUser}.
 */
@RestController
@RequestMapping("/api")
public class CustomUserResource {

    private final Logger log = LoggerFactory.getLogger(CustomUserResource.class);

    private static final String ENTITY_NAME = "customUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomUserService customUserService;

    public CustomUserResource(CustomUserService customUserService) {
        this.customUserService = customUserService;
    }

    /**
     * {@code POST  /custom-users} : Create a new customUser.
     *
     * @param customUserDTO the customUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customUserDTO, or with status {@code 400 (Bad Request)} if the customUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/custom-users")
    public ResponseEntity<CustomUserDTO> createCustomUser(@RequestBody CustomUserDTO customUserDTO) throws URISyntaxException {
        log.debug("REST request to save CustomUser : {}", customUserDTO);
        if (customUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new customUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomUserDTO result = customUserService.save(customUserDTO);
        return ResponseEntity.created(new URI("/api/custom-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /custom-users} : Updates an existing customUser.
     *
     * @param customUserDTO the customUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customUserDTO,
     * or with status {@code 400 (Bad Request)} if the customUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/custom-users")
    public ResponseEntity<CustomUserDTO> updateCustomUser(@RequestBody CustomUserDTO customUserDTO) throws URISyntaxException {
        log.debug("REST request to update CustomUser : {}", customUserDTO);
        if (customUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomUserDTO result = customUserService.save(customUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /custom-users} : get all the customUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customUsers in body.
     */
    @GetMapping("/custom-users")
    public List<CustomUserDTO> getAllCustomUsers() {
        log.debug("REST request to get all CustomUsers");
        return customUserService.findAll();
    }

    /**
     * {@code GET  /custom-users/:id} : get the "id" customUser.
     *
     * @param id the id of the customUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/custom-users/{id}")
    public ResponseEntity<CustomUserDTO> getCustomUser(@PathVariable Long id) {
        log.debug("REST request to get CustomUser : {}", id);
        Optional<CustomUserDTO> customUserDTO = customUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customUserDTO);
    }

    /**
     * {@code DELETE  /custom-users/:id} : delete the "id" customUser.
     *
     * @param id the id of the customUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/custom-users/{id}")
    public ResponseEntity<Void> deleteCustomUser(@PathVariable Long id) {
        log.debug("REST request to delete CustomUser : {}", id);
        customUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }


    /**
     * {@code GET  /user/{userId}/custom-users} : get CustomUser by userId
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customUsers in body.
     */
    @GetMapping("user/{userid}/custom-users")
    public ResponseEntity<CustomUserDTO> getCustomUsersByQuery(@PathVariable Long userid){
        log.debug(" REST request to get a page of CustomUsers by UserId for : " + userid);
        Optional<CustomUserDTO> customUserDTO = customUserService.findByJID(userid);
        return ResponseUtil.wrapOrNotFound(customUserDTO);
    }

    /**
     * {@code GET  /custom-users/:id} : get the "id" customUser.
     *
     * @param id the id of the customUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/custom-users/users/{id}")
    public ResponseEntity<CustomUserDTO> getCustomUserByUserId(@PathVariable Long id) {
        log.debug("REST request to get CustomUser with jhi_user id : {}", id);
        Optional<CustomUserDTO> customUserDTO = customUserService.findByJID(id);
        return ResponseUtil.wrapOrNotFound(customUserDTO);
    }


    /**
     * {@code GET  /custom-users/:id/username} : get the "id" customUser.
     *
     * @param id the id of the customUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/custom-users/{id}/username")
    public Optional<String> getUsernameById(@PathVariable Long id) {
        log.debug("REST request to get username of CustomUser with jhi_user id : {}", id);
        Optional<String> username = customUserService.findUsernameById(id);
        return username;
    }
}
