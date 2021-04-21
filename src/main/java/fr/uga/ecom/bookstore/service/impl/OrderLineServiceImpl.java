package fr.uga.ecom.bookstore.service.impl;

import fr.uga.ecom.bookstore.service.OrderLineService;
import fr.uga.ecom.bookstore.domain.OrderLine;
import fr.uga.ecom.bookstore.repository.OrderLineRepository;
import fr.uga.ecom.bookstore.service.dto.OrderLineDTO;
import fr.uga.ecom.bookstore.service.mapper.OrderLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link OrderLine}.
 */
@Service
@Transactional
public class OrderLineServiceImpl implements OrderLineService {

    private final Logger log = LoggerFactory.getLogger(OrderLineServiceImpl.class);

    private final OrderLineRepository orderLineRepository;

    private final OrderLineMapper orderLineMapper;

    public OrderLineServiceImpl(OrderLineRepository orderLineRepository, OrderLineMapper orderLineMapper) {
        this.orderLineRepository = orderLineRepository;
        this.orderLineMapper = orderLineMapper;
    }

    @Override
    public OrderLineDTO save(OrderLineDTO orderLineDTO) {
        log.debug("Request to save OrderLine : {}", orderLineDTO);
        OrderLine orderLine = orderLineMapper.toEntity(orderLineDTO);
        orderLine = orderLineRepository.save(orderLine);
        return orderLineMapper.toDto(orderLine);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderLineDTO> findAll() {
        log.debug("Request to get all OrderLines");
        return orderLineRepository.findAll().stream()
            .map(orderLineMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<OrderLineDTO> findOne(Long id) {
        log.debug("Request to get OrderLine : {}", id);
        return orderLineRepository.findById(id)
            .map(orderLineMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderLine : {}", id);
        orderLineRepository.deleteById(id);
    }

    @Override
    public OrderLineDTO saveOrderLine(OrderLineDTO orderLineDTO) {
        log.debug("Request to save OrderLine (add To Cart) : {}", orderLineDTO);
        OrderLine orderLine = orderLineMapper.toEntity(orderLineDTO);
        orderLine = orderLineRepository.save(orderLine);
        OrderLineDTO result = orderLineMapper.toDto(orderLine);
        return result;
    }

    /**
     * @author QCR
     * Recuperation de lignes de commandes a partir du numero de commande
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderLineDTO> findAllByOrderId(Long orderid){
        log.debug("Request to get all OrderLines by OrderId");
        return orderLineRepository.findAllByOrder_IdEquals(orderid).stream()
            .map(orderLineMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
