package com.store.grocery.fresh_express.shared.kernel;

public interface Mapper<E, D> {

    E mapToEntity(D dto);

    D mapToDTO(E entity);

}
