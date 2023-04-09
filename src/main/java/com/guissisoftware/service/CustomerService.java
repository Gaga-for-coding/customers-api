package com.guissisoftware.service;

import com.guissisoftware.models.CustomerEntity;
import com.guissisoftware.models.dto.Customer;
import com.guissisoftware.repository.CustomerRepositoy;
import com.guissisoftware.util.CustomerMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
public class CustomerService {
    private final CustomerRepositoy customerRepositoy;
    private final CustomerMapper customerMapper;

    public List<Customer> findAll(){
        return this.customerMapper.toDtoList(customerRepositoy.findAll().list());
    }

    public Optional<Customer> findById(@NonNull Long id){
        return this.customerRepositoy.findByIdOptional(id).map(customerMapper::toDto);
    }

    public void save(@Valid Customer customer){
        log.debug("Saving customer : {}", customer);
        CustomerEntity customerEntity = customerMapper.toEntity(customer);
        customerRepositoy.persist(customerEntity);
        customerMapper.updateDtoFromEntity(customerEntity, customer);
    }

}
