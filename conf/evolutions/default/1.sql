# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table candidate (
  email                     varchar(255) not null,
  firstname                 varchar(255),
  lastname                  varchar(255),
  constraint pk_candidate primary key (email))
;

create sequence candidate_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists candidate;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists candidate_seq;

