package org.example.mapper;

import org.example.domain.entity.AccountEntity;
import org.example.domain.rest.AccountRest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AccountMapper {

    public static final AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountRest map(AccountEntity entity);

    AccountEntity map(AccountRest rest);

    List<AccountRest> mapRest(List<AccountEntity> entity);

    List<AccountEntity> mapEntity(List<AccountRest> rest);

}
