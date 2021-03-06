# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table account (
  id                        bigint not null,
  account_id                bigint,
  account_type              varchar(19),
  owner_id                  bigint,
  status                    varchar(9),
  company_id                bigint,
  created_at                timestamp not null,
  constraint ck_account_account_type check (account_type in ('established_company','startup','large_company','free')),
  constraint ck_account_status check (status in ('active','suspended')),
  constraint pk_account primary key (id))
;

create table applicant (
  id                        bigint not null,
  account_id                bigint,
  firstname                 varchar(255),
  lastname                  varchar(255),
  email                     varchar(255),
  appliance_status          varchar(30),
  created_at                timestamp not null,
  constraint ck_applicant_appliance_status check (appliance_status in ('rejected_after_boss_interview','schedule_tech_interview','to_contact','refused_our_offer','hired','rejected_after_tech_interview','rejected','rejected_after_phone_interview','schedule_boss_interview','contacted')),
  constraint pk_applicant primary key (id))
;

create table base_model (
  id                        bigint not null,
  account_id                bigint,
  created_at                timestamp not null,
  constraint pk_base_model primary key (id))
;

create table blog_post (
  id                        bigint not null,
  title                     varchar(255),
  text                      clob,
  created_at                timestamp not null,
  constraint pk_blog_post primary key (id))
;

create table comment (
  id                        bigint not null,
  account_id                bigint,
  text                      varchar(255),
  opening_id                bigint,
  applicant_id              bigint,
  author_id                 bigint,
  created_at                timestamp not null,
  constraint pk_comment primary key (id))
;

create table company (
  id                        bigint not null,
  account_id                bigint,
  name                      varchar(255),
  created_at                timestamp not null,
  constraint pk_company primary key (id))
;

create table document (
  id                        bigint not null,
  account_id                bigint,
  name                      varchar(255),
  url                       varchar(255),
  size                      bigint,
  content_type              varchar(255),
  opening_id                bigint,
  applicant_id              bigint,
  created_at                timestamp not null,
  constraint pk_document primary key (id))
;

create table feedback (
  id                        bigint not null,
  account_id                bigint,
  text                      clob,
  author_id                 bigint,
  feedback_type             varchar(7),
  created_at                timestamp not null,
  constraint ck_feedback_feedback_type check (feedback_type in ('support','bug','remark','idea')),
  constraint pk_feedback primary key (id))
;

create table member (
  id                        bigint not null,
  account_id                bigint,
  email                     varchar(255),
  firstname                 varchar(255),
  lastname                  varchar(255),
  password                  varchar(255),
  last_login                timestamp,
  profile                   varchar(13),
  created_at                timestamp not null,
  constraint ck_member_profile check (profile in ('customer','administrator')),
  constraint pk_member primary key (id))
;

create table opening (
  id                        bigint not null,
  account_id                bigint,
  name                      varchar(255),
  created_at                timestamp not null,
  constraint pk_opening primary key (id))
;

create table tag (
  id                        bigint not null,
  account_id                bigint,
  text                      varchar(255),
  applicant_id              bigint,
  created_at                timestamp not null,
  constraint pk_tag primary key (id))
;


create table applicant_opening (
  applicant_id                   bigint not null,
  opening_id                     bigint not null,
  constraint pk_applicant_opening primary key (applicant_id, opening_id))
;
create sequence account_seq;

create sequence applicant_seq;

create sequence base_model_seq;

create sequence blog_post_seq;

create sequence comment_seq;

create sequence company_seq;

create sequence document_seq;

create sequence feedback_seq;

create sequence member_seq;

create sequence opening_seq;

create sequence tag_seq;

alter table account add constraint fk_account_account_1 foreign key (account_id) references account (id) on delete restrict on update restrict;
create index ix_account_account_1 on account (account_id);
alter table account add constraint fk_account_owner_2 foreign key (owner_id) references member (id) on delete restrict on update restrict;
create index ix_account_owner_2 on account (owner_id);
alter table account add constraint fk_account_company_3 foreign key (company_id) references company (id) on delete restrict on update restrict;
create index ix_account_company_3 on account (company_id);
alter table applicant add constraint fk_applicant_account_4 foreign key (account_id) references account (id) on delete restrict on update restrict;
create index ix_applicant_account_4 on applicant (account_id);
alter table base_model add constraint fk_base_model_account_5 foreign key (account_id) references account (id) on delete restrict on update restrict;
create index ix_base_model_account_5 on base_model (account_id);
alter table comment add constraint fk_comment_account_6 foreign key (account_id) references account (id) on delete restrict on update restrict;
create index ix_comment_account_6 on comment (account_id);
alter table comment add constraint fk_comment_opening_7 foreign key (opening_id) references opening (id) on delete restrict on update restrict;
create index ix_comment_opening_7 on comment (opening_id);
alter table comment add constraint fk_comment_applicant_8 foreign key (applicant_id) references applicant (id) on delete restrict on update restrict;
create index ix_comment_applicant_8 on comment (applicant_id);
alter table comment add constraint fk_comment_author_9 foreign key (author_id) references member (id) on delete restrict on update restrict;
create index ix_comment_author_9 on comment (author_id);
alter table company add constraint fk_company_account_10 foreign key (account_id) references account (id) on delete restrict on update restrict;
create index ix_company_account_10 on company (account_id);
alter table document add constraint fk_document_account_11 foreign key (account_id) references account (id) on delete restrict on update restrict;
create index ix_document_account_11 on document (account_id);
alter table document add constraint fk_document_opening_12 foreign key (opening_id) references opening (id) on delete restrict on update restrict;
create index ix_document_opening_12 on document (opening_id);
alter table document add constraint fk_document_applicant_13 foreign key (applicant_id) references applicant (id) on delete restrict on update restrict;
create index ix_document_applicant_13 on document (applicant_id);
alter table feedback add constraint fk_feedback_account_14 foreign key (account_id) references account (id) on delete restrict on update restrict;
create index ix_feedback_account_14 on feedback (account_id);
alter table feedback add constraint fk_feedback_author_15 foreign key (author_id) references member (id) on delete restrict on update restrict;
create index ix_feedback_author_15 on feedback (author_id);
alter table member add constraint fk_member_account_16 foreign key (account_id) references account (id) on delete restrict on update restrict;
create index ix_member_account_16 on member (account_id);
alter table opening add constraint fk_opening_account_17 foreign key (account_id) references account (id) on delete restrict on update restrict;
create index ix_opening_account_17 on opening (account_id);
alter table tag add constraint fk_tag_account_18 foreign key (account_id) references account (id) on delete restrict on update restrict;
create index ix_tag_account_18 on tag (account_id);
alter table tag add constraint fk_tag_applicant_19 foreign key (applicant_id) references applicant (id) on delete restrict on update restrict;
create index ix_tag_applicant_19 on tag (applicant_id);



alter table applicant_opening add constraint fk_applicant_opening_applican_01 foreign key (applicant_id) references applicant (id) on delete restrict on update restrict;

alter table applicant_opening add constraint fk_applicant_opening_opening_02 foreign key (opening_id) references opening (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists account;

drop table if exists applicant;

drop table if exists applicant_opening;

drop table if exists base_model;

drop table if exists blog_post;

drop table if exists comment;

drop table if exists company;

drop table if exists document;

drop table if exists feedback;

drop table if exists member;

drop table if exists opening;

drop table if exists tag;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists account_seq;

drop sequence if exists applicant_seq;

drop sequence if exists base_model_seq;

drop sequence if exists blog_post_seq;

drop sequence if exists comment_seq;

drop sequence if exists company_seq;

drop sequence if exists document_seq;

drop sequence if exists feedback_seq;

drop sequence if exists member_seq;

drop sequence if exists opening_seq;

drop sequence if exists tag_seq;

