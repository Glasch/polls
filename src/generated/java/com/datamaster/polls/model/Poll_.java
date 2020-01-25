package com.datamaster.polls.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Poll.class)
public abstract class Poll_ extends com.datamaster.polls.model.meta.BaseEntity_ {

	public static volatile SingularAttribute<Poll, Date> endDate;
	public static volatile SingularAttribute<Poll, String> name;
	public static volatile ListAttribute<Poll, Question> questions;
	public static volatile SingularAttribute<Poll, Boolean> active;
	public static volatile SingularAttribute<Poll, Date> startDate;

	public static final String END_DATE = "endDate";
	public static final String NAME = "name";
	public static final String QUESTIONS = "questions";
	public static final String ACTIVE = "active";
	public static final String START_DATE = "startDate";

}

