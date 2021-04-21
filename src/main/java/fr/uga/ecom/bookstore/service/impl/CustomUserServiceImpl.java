package fr.uga.ecom.bookstore.service.impl;

import fr.uga.ecom.bookstore.domain.User;
import fr.uga.ecom.bookstore.service.CustomUserService;
import fr.uga.ecom.bookstore.domain.CustomUser;
import fr.uga.ecom.bookstore.repository.CustomUserRepository;
import fr.uga.ecom.bookstore.service.dto.CustomUserDTO;
import fr.uga.ecom.bookstore.service.mapper.CustomUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CustomUser}.
 */
@Service
@Transactional
public class CustomUserServiceImpl implements CustomUserService {

    private final Logger log = LoggerFactory.getLogger(CustomUserServiceImpl.class);

    private final CustomUserRepository customUserRepository;

    private final CustomUserMapper customUserMapper;

    public CustomUserServiceImpl(CustomUserRepository customUserRepository, CustomUserMapper customUserMapper) {
        this.customUserRepository = customUserRepository;
        this.customUserMapper = customUserMapper;
    }

    @Override
    public CustomUserDTO save(CustomUserDTO customUserDTO) {
        log.debug("Request to save CustomUser : {}", customUserDTO);
        CustomUser customUser = customUserMapper.toEntity(customUserDTO);
        customUser = customUserRepository.save(customUser);
        return customUserMapper.toDto(customUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomUserDTO> findAll() {
        log.debug("Request to get all CustomUsers");
        return customUserRepository.findAll().stream()
            .map(customUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CustomUserDTO> findOne(Long id) {
        log.debug("Request to get CustomUser : {}", id);
        return customUserRepository.findById(id)
            .map(customUserMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomUser : {}", id);
        customUserRepository.deleteById(id);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CustomUserDTO> findByJID(Long id) {
        log.debug("Request to get CustomUser with jhi_user id: {}", id);
        return customUserRepository.findByUserId(id)
            .map(customUserMapper::toDto);
    }



    @Override
    @Transactional(readOnly = true)
    public Optional<String> findUsernameById(Long id) {
        //log.debug("Request to get CustomUser with id: {}", id);
        Optional<CustomUser> customUser = customUserRepository.findById(id);
        if(customUser.isPresent()) {
            User userId = customUser.get().getUser();
            return Optional.of(userId.getLogin());
        }else {
            return Optional.empty();
        }
    }
}
