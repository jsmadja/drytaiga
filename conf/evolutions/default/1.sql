# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table account (
  id                        bigint auto_increment not null,
  account_type              varchar(19),
  owner_id                  bigint,
  status                    varchar(9),
  constraint ck_account_account_type check (account_type in ('established_company','startup','large_company','free')),
  constraint ck_account_status check (status in ('active','suspended')),
  constraint pk_account primary key (id))
;

create table candidate (
  id                        bigint auto_increment not null,
  firstname                 varchar(255),
  lastname                  varchar(255),
  email                     varchar(255),
  constraint pk_candidate primary key (id))
;

create table company (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  account_id                bigint,
  constraint pk_company primary key (id))
;

create table document (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  url                       varchar(255),
  size                      bigint,
  content_type              varchar(255),
  position_id               bigint,
  candidate_id              bigint,
  constraint pk_document primary key (id))
;

create table member (
  id                        bigint auto_increment not null,
  email                     varchar(255),
  firstname                 varchar(255),
  lastname                  varchar(255),
  password                  varchar(255),
  company_id                bigint,
  constraint pk_member primary key (id))
;

create table position (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  company_id                bigint,
  constraint pk_position primary key (id))
;


create table candidate_position (
  candidate_id                   bigint not null,
  position_id                    bigint not null,
  constraint pk_candidate_position primary key (candidate_id, position_id))
;
alter table account add constraint fk_account_owner_1 foreign key (owner_id) references member (id) on delete restrict on update restrict;
create index ix_account_owner_1 on account (owner_id);
alter table company add constraint fk_company_account_2 foreign key (account_id) references account (id) on delete restrict on update restrict;
create index ix_company_account_2 on company (account_id);
alter table document add constraint fk_document_position_3 foreign key (position_id) references position (id) on delete restrict on update restrict;
create index ix_document_position_3 on document (position_id);
alter table document add constraint fk_document_candidate_4 foreign key (candidate_id) references candidate (id) on delete restrict on update restrict;
create index ix_document_candidate_4 on document (candidate_id);
alter table member add constraint fk_member_company_5 foreign key (company_id) references company (id) on delete restrict on update restrict;
create index ix_member_company_5 on member (company_id);
alter table position add constraint fk_position_company_6 foreign key (company_id) references company (id) on delete restrict on update restrict;
create index ix_position_company_6 on position (company_id);



alter table candidate_position add constraint fk_candidate_position_candidate_01 foreign key (candidate_id) references candidate (id) on delete restrict on update restrict;

alter table candidate_position add constraint fk_candidate_position_position_02 foreign key (position_id) references position (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table account;

drop table candidate;

drop table candidate_position;

drop table company;

drop table document;

drop table member;

drop table position;

SET FOREIGN_KEY_CHECKS=1;

