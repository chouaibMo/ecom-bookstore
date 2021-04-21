package fr.uga.ecom.bookstore.web.rest;

import fr.uga.ecom.bookstore.service.OrderLineService;
import fr.uga.ecom.bookstore.web.rest.errors.BadRequestAlertException;
import fr.uga.ecom.bookstore.service.dto.OrderLineDTO;

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
 * REST controller for managing {@link fr.uga.ecom.bookstore.domain.OrderLine}.
 */
@RestController
@RequestMapping("/api")
public class OrderLineResource {

    private final Logger log = LoggerFactory.getLogger(OrderLineResource.class);

    private static final String ENTITY_NAME = "orderLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderLineService orderLineService;

    public OrderLineResource(OrderLineService orderLineService) {
        this.orderLineService = orderLineService;
    }

    /**
     * {@code POST  /order-lines} : Create a new orderLine.
     *
     * @param orderLineDTO the orderLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderLineDTO, or with status {@code 400 (Bad Request)} if the orderLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-lines")
    public ResponseEntity<OrderLineDTO> createOrderLine(@Valid @RequestBody OrderLineDTO orderLineDTO) throws URISyntaxException {
        log.debug("REST request to save OrderLine : {}", orderLineDTO);
        if (orderLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderLineDTO result = orderLineService.save(orderLineDTO);
        return ResponseEntity.created(new URI("/api/order-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-lines} : Updates an existing orderLine.
     *
     * @param orderLineDTO the orderLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderLineDTO,
     * or with status {@code 400 (Bad Request)} if the orderLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-lines")
    public ResponseEntity<OrderLineDTO> updateOrderLine(@Valid @RequestBody OrderLineDTO orderLineDTO) throws URISyntaxException {
        log.debug("REST request to update OrderLine : {}", orderLineDTO);
        if (orderLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderLineDTO result = orderLineService.save(orderLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-lines} : get all the orderLines.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderLines in body.
     */
    @GetMapping("/order-lines")
    public List<OrderLineDTO> getAllOrderLines() {
        log.debug("REST request to get all OrderLines");
        return orderLineService.findAll();
    }

    /**
     * {@code GET  /order-lines/:id} : get the "id" orderLine.
     *
     * @param id the id of the orderLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-lines/{id}")
    public ResponseEntity<OrderLineDTO> getOrderLine(@PathVariable Long id) {
        log.debug("REST request to get OrderLine : {}", id);
        Optional<OrderLineDTO> orderLineDTO = orderLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderLineDTO);
    }

    /**
     * {@code DELETE  /order-lines/:id} : delete the "id" orderLine.
     *
     * @param id the id of the orderLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-lines/{id}")
    public ResponseEntity<Void> deleteOrderLine(@PathVariable Long id) {
        log.debug("REST request to delete OrderLine : {}", id);
        orderLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * @author QCR
     * {@code GET  /order-lines/orderid/:id} : get the "id" orderLine.
     *
     * @param orderid the id of the orderDTO related to the orderLineDTO  to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-lines/orderid/{orderid}")
    public List<OrderLineDTO> getOrderLinesByOrderId(@PathVariable Long orderid){
        log.debug("REST request to get OrderLines by OrderId");
        return orderLineService.findAllByOrderId(orderid);
    }
}
