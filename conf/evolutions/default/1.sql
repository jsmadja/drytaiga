# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table account (
  id                        bigint not null,
  account_type              varchar(19),
  owner_id                  bigint,
  status                    varchar(9),
  constraint ck_account_account_type check (account_type in ('established_company','startup','large_company','free')),
  constraint ck_account_status check (status in ('active','suspended')),
  constraint pk_account primary key (id))
;

create table candidate (
  id                        bigint not null,
  firstname                 varchar(255),
  lastname                  varchar(255),
  email                     varchar(255),
  account_id                bigint,
  constraint pk_candidate primary key (id))
;

create table company (
  id                        bigint not null,
  name                      varchar(255),
  account_id                bigint,
  constraint pk_company primary key (id))
;

create table document (
  id                        bigint not null,
  name                      varchar(255),
  url                       varchar(255),
  size                      bigint,
  content_type              varchar(255),
  position_id               bigint,
  candidate_id              bigint,
  account_id                bigint,
  constraint pk_document primary key (id))
;

create table member (
  id                        bigint not null,
  email                     varchar(255),
  firstname                 varchar(255),
  lastname                  varchar(255),
  password                  varchar(255),
  company_id                bigint,
  account_id                bigint,
  last_login                date,
  constraint pk_member primary key (id))
;

create table position (
  id                        bigint not null,
  name                      varchar(255),
  company_id                bigint,
  account_id                bigint,
  constraint pk_position primary key (id))
;


create table candidate_position (
  candidate_id                   bigint not null,
  position_id                    bigint not null,
  constraint pk_candidate_position primary key (candidate_id, position_id))
;
create sequence account_seq;

create sequence candidate_seq;

create sequence company_seq;

create sequence document_seq;

create sequence member_seq;

create sequence position_seq;

alter table account add constraint fk_account_owner_1 foreign key (owner_id) references member (id) on delete restrict on update restrict;
create index ix_account_owner_1 on account (owner_id);
alter table candidate add constraint fk_candidate_account_2 foreign key (account_id) references account (id) on delete restrict on update restrict;
create index ix_candidate_account_2 on candidate (account_id);
alter table company add constraint fk_company_account_3 foreign key (account_id) references account (id) on delete restrict on update restrict;
create index ix_company_account_3 on company (account_id);
alter table document add constraint fk_document_position_4 foreign key (position_id) references position (id) on delete restrict on update restrict;
create index ix_document_position_4 on document (position_id);
alter table document add constraint fk_document_candidate_5 foreign key (candidate_id) references candidate (id) on delete restrict on update restrict;
create index ix_document_candidate_5 on document (candidate_id);
alter table document add constraint fk_document_account_6 foreign key (account_id) references account (id) on delete restrict on update restrict;
create index ix_document_account_6 on document (account_id);
alter table member add constraint fk_member_company_7 foreign key (company_id) references company (id) on delete restrict on update restrict;
create index ix_member_company_7 on member (company_id);
alter table member add constraint fk_member_account_8 foreign key (account_id) references account (id) on delete restrict on update restrict;
create index ix_member_account_8 on member (account_id);
alter table position add constraint fk_position_company_9 foreign key (company_id) references company (id) on delete restrict on update restrict;
create index ix_position_company_9 on position (company_id);
alter table position add constraint fk_position_account_10 foreign key (account_id) references account (id) on delete restrict on update restrict;
create index ix_position_account_10 on position (account_id);



alter table candidate_position add constraint fk_candidate_position_candida_01 foreign key (candidate_id) references candidate (id) on delete restrict on update restrict;

alter table candidate_position add constraint fk_candidate_position_positio_02 foreign key (position_id) references position (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists account;

drop table if exists candidate;

drop table if exists candidate_position;

drop table if exists company;

drop table if exists document;

drop table if exists member;

drop table if exists position;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists account_seq;

drop sequence if exists candidate_seq;

drop sequence if exists company_seq;

drop sequence if exists document_seq;

drop sequence if exists member_seq;

drop sequence if exists position_seq;

