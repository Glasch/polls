package com.datamaster.polls.model.meta;

import java.util.Date;
import java.util.UUID;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BaseEntity.class)
public abstract class BaseEntity_ {

	public static volatile SingularAttribute<BaseEntity, Date> createdAt;
	public static volatile SingularAttribute<BaseEntity, UUID> id;
	public static volatile SingularAttribute<BaseEntity, Date> updatedAt;

	public static final String CREATED_AT = "createdAt";
	public static final String ID = "id";
	public static final String UPDATED_AT = "updatedAt";

}

