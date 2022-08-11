package com.z.useraccountmgmt.model.mapper;

public interface Mapper<E, D, Q, P> {
    public P mapDtoToResponse(D d);
    public D mapRequestToDto(Q q);
    public E mapDtotoEntity(D d);
    public D mapEntityToDto(E e);
}
