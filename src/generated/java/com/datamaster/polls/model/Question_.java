package com.datamaster.polls.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Question.class)
public abstract class Question_ extends com.datamaster.polls.model.meta.BaseEntity_ {

	public static volatile SingularAttribute<Question, Integer> sort;
	public static volatile SingularAttribute<Question, Poll> poll;
	public static volatile SingularAttribute<Question, String> content;

	public static final String SORT = "sort";
	public static final String POLL = "poll";
	public static final String CONTENT = "content";

}

