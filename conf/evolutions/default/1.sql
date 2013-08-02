# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table candidate (
  id                        bigint not null,
  firstname                 varchar(255),
  lastname                  varchar(255),
  email                     varchar(255),
  constraint pk_candidate primary key (id))
;

create table company (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_company primary key (id))
;

create table document (
  id                        bigint not null,
  candidate_id              bigint not null,
  name                      varchar(255),
  constraint pk_document primary key (id))
;

create table position (
  id                        bigint not null,
  name                      varchar(255),
  company_id                bigint,
  constraint pk_position primary key (id))
;

create table s3file (
  id                        varchar(40) not null,
  bucket                    varchar(255),
  name                      varchar(255),
  constraint pk_s3file primary key (id))
;

create table user (
  id                        bigint not null,
  company_id                bigint not null,
  email                     varchar(255),
  firstname                 varchar(255),
  lastname                  varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (id))
;


create table candidate_position (
  candidate_id                   bigint not null,
  position_id                    bigint not null,
  constraint pk_candidate_position primary key (candidate_id, position_id))
;

create table position_candidate (
  position_id                    bigint not null,
  candidate_id                   bigint not null,
  constraint pk_position_candidate primary key (position_id, candidate_id))
;
create sequence candidate_seq;

create sequence company_seq;

create sequence document_seq;

create sequence position_seq;

create sequence user_seq;

alter table document add constraint fk_document_candidate_1 foreign key (candidate_id) references candidate (id) on delete restrict on update restrict;
create index ix_document_candidate_1 on document (candidate_id);
alter table position add constraint fk_position_company_2 foreign key (company_id) references company (id) on delete restrict on update restrict;
create index ix_position_company_2 on position (company_id);
alter table user add constraint fk_user_company_3 foreign key (company_id) references company (id) on delete restrict on update restrict;
create index ix_user_company_3 on user (company_id);



alter table candidate_position add constraint fk_candidate_position_candida_01 foreign key (candidate_id) references candidate (id) on delete restrict on update restrict;

alter table candidate_position add constraint fk_candidate_position_positio_02 foreign key (position_id) references position (id) on delete restrict on update restrict;

alter table position_candidate add constraint fk_position_candidate_positio_01 foreign key (position_id) references position (id) on delete restrict on update restrict;

alter table position_candidate add constraint fk_position_candidate_candida_02 foreign key (candidate_id) references candidate (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists candidate;

drop table if exists candidate_position;

drop table if exists company;

drop table if exists document;

drop table if exists position;

drop table if exists position_candidate;

drop table if exists s3file;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists candidate_seq;

drop sequence if exists company_seq;

drop sequence if exists document_seq;

drop sequence if exists position_seq;

drop sequence if exists user_seq;

