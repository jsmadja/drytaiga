# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table candidate (
  email                     varchar(255) not null,
  firstname                 varchar(255),
  lastname                  varchar(255),
  constraint pk_candidate primary key (email))
;

create table s3file (
  id                        varchar(40) not null,
  bucket                    varchar(255),
  name                      varchar(255),
  constraint pk_s3file primary key (id))
;

create sequence candidate_seq;




# --- !Downs

#SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists candidate;

drop table if exists s3file;

#SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists candidate_seq;

