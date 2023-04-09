package com.guissisoftware.util;

import com.guissisoftware.models.CustomerEntity;
import com.guissisoftware.models.dto.Customer;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface CustomerMapper {
    List<Customer> toDtoList(List<CustomerEntity> entities);
    Customer toDto(CustomerEntity entity);

    @InheritInverseConfiguration(name = "toDto")
    CustomerEntity toEntity(Customer customerDto);

    void updateEntityFromDto(Customer customerDto, @MappingTarget CustomerEntity entity);
    void updateDtoFromEntity(CustomerEntity entity, @MappingTarget Customer customerDto);
}
