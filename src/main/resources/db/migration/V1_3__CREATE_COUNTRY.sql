CREATE TABLE country
(
  id serial NOT NULL,
  code character varying(10),
  name character varying(40),
  version integer,
  majuser character varying(40),
  majdate date,
  CONSTRAINT country_code_key UNIQUE (code)
)
WITH (
  OIDS=FALSE
);

CREATE SEQUENCE seq_country
    START WITH 100
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
