package fr.uga.ecom.bookstore.service.impl;

import fr.uga.ecom.bookstore.service.BillingInfoService;
import fr.uga.ecom.bookstore.domain.BillingInfo;
import fr.uga.ecom.bookstore.repository.BillingInfoRepository;
import fr.uga.ecom.bookstore.service.dto.BillingInfoDTO;
import fr.uga.ecom.bookstore.service.mapper.BillingInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BillingInfo}.
 */
@Service
@Transactional
public class BillingInfoServiceImpl implements BillingInfoService {

    private final Logger log = LoggerFactory.getLogger(BillingInfoServiceImpl.class);

    private final BillingInfoRepository billingInfoRepository;

    private final BillingInfoMapper billingInfoMapper;

    public BillingInfoServiceImpl(BillingInfoRepository billingInfoRepository, BillingInfoMapper billingInfoMapper) {
        this.billingInfoRepository = billingInfoRepository;
        this.billingInfoMapper = billingInfoMapper;
    }

    @Override
    public BillingInfoDTO save(BillingInfoDTO billingInfoDTO) {
        log.debug("Request to save BillingInfo : {}", billingInfoDTO);
        BillingInfo billingInfo = billingInfoMapper.toEntity(billingInfoDTO);
        billingInfo = billingInfoRepository.save(billingInfo);
        return billingInfoMapper.toDto(billingInfo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BillingInfoDTO> findAll() {
        log.debug("Request to get all BillingInfos");
        return billingInfoRepository.findAll().stream()
            .map(billingInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BillingInfoDTO> findOne(Long id) {
        log.debug("Request to get BillingInfo : {}", id);
        return billingInfoRepository.findById(id)
            .map(billingInfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BillingInfo : {}", id);
        billingInfoRepository.deleteById(id);
    }
}
