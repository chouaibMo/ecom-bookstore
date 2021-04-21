package fr.uga.ecom.bookstore.service.impl;

import fr.uga.ecom.bookstore.service.OrderService;
import fr.uga.ecom.bookstore.domain.Order;
import fr.uga.ecom.bookstore.repository.OrderRepository;
import fr.uga.ecom.bookstore.service.dto.OrderDTO;
import fr.uga.ecom.bookstore.service.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Order}.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        log.debug("Request to save Order : {}", orderDTO);
        Order order = orderMapper.toEntity(orderDTO);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Orders");
        return orderRepository.findAll(pageable)
            .map(orderMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<OrderDTO> findOne(Long id) {
        log.debug("Request to get Order : {}", id);
        return orderRepository.findById(id)
            .map(orderMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Order : {}", id);
        orderRepository.deleteById(id);
    }

    /**
     * @author : Emez
     * Get the Order of client with id "userId" if it is still in cart or null otherwise
     *
     * @param userId
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderDTO> getUserCart(Long userId) {
        log.debug("Request to get a user's unvalidated cart");
        Order order = orderRepository.getUserCart(userId);
        return Optional.ofNullable(orderMapper.toDto(order));
    }

    /**
     * @author QCR
     *
     * @param customUserId
     * @param pageable
     * @return the entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrderDTO> findOrderHistory(Long customUserId, Pageable pageable){
        log.debug("Request to get order history for CustomUserId {}", customUserId);
        return orderRepository.findOrderHistory(customUserId, pageable)
            .map(orderMapper::toDto);
    }
}
