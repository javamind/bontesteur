CREATE TABLE talk
(
  id serial NOT NULL,
  name character varying(250),
  description character varying(1000),
  place character varying(50),
  nbpeoplemax integer,
  datestart date,
  dateend date,
  level character varying(40),
  status character varying(40),
  version integer,
  majuser character varying(40),
  majdate date,
  CONSTRAINT talk_name_key UNIQUE (name)
)
WITH (
  OIDS=FALSE
);

CREATE SEQUENCE seq_talk
    START WITH 100
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;