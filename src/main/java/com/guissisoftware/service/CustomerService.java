package com.guissisoftware.service;

import com.guissisoftware.models.CustomerEntity;
import com.guissisoftware.models.dto.Customer;
import com.guissisoftware.repository.CustomerRepository;
import com.guissisoftware.util.CustomerMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
public class CustomerService {
    @Inject
    CustomerRepository customerRepository;

    @Inject
    CustomerMapper customerMapper;

    public List<Customer> findAll(){
        return this.customerMapper.toDtoList(customerRepository.findAll().list());
    }

    public Optional<Customer> findById(@NonNull Long id){
        return this.customerRepository.findByIdOptional(id).map(customerMapper::toDto);
    }

    @Transactional
    public void save(@Valid Customer customer){
        log.debug("Saving customer : {}", customer);
        CustomerEntity customerEntity = customerMapper.toEntity(customer);
        customerRepository.persist(customerEntity);
        customerMapper.updateDtoFromEntity(customerEntity, customer);
    }

}
