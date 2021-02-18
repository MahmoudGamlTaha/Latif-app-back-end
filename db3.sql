-- Table: public.users

-- DROP TABLE public.users;

CREATE TABLE public.users
(
    id bigint NOT NULL,
    company_id bigint,
    fname character varying(255) COLLATE pg_catalog."default" NOT NULL,
    lname character varying(255) COLLATE pg_catalog."default" NOT NULL,
    email character varying(50) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    address1 character varying(255) COLLATE pg_catalog."default" NOT NULL,
    address2 character varying(255) COLLATE pg_catalog."default" DEFAULT NULL::character varying,
    mobile character varying(255) COLLATE pg_catalog."default" NOT NULL,
    active boolean,
    user_type integer,
    remember_token character varying(100) COLLATE pg_catalog."default" DEFAULT NULL::character varying,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    country_id bigint,
    state_id bigint,
    city_id bigint,
    country character varying(250) COLLATE pg_catalog."default",
    state character varying(250) COLLATE pg_catalog."default",
    city character varying(250) COLLATE pg_catalog."default",
    email_verified boolean,
    zip character varying(20) COLLATE pg_catalog."default"
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.users
    OWNER to postgres;



-- Table: public.blogs

-- DROP TABLE public.blogs;

CREATE TABLE public.blogs
(
    title character varying(250) COLLATE pg_catalog."default",
    category_id bigint,
    image character varying(250) COLLATE pg_catalog."default",
    path character varying(250) COLLATE pg_catalog."default",
    description character varying(250) COLLATE pg_catalog."default",
    user_id bigint,
    date timestamp without time zone,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    id bigint NOT NULL DEFAULT nextval('blogs_id_seq'::regclass),
    CONSTRAINT blogs_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.blogs
    OWNER to postgres;




-- Table: public.blog_categories

-- DROP TABLE public.blog_categories;

CREATE TABLE public.blog_categories
(
    name character varying(250) COLLATE pg_catalog."default",
    description character varying(250) COLLATE pg_catalog."default",
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    id integer NOT NULL DEFAULT nextval('blog_categories_id_seq'::regclass),
    CONSTRAINT blog_categories_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.blog_categories
    OWNER to postgres;



-- Table: public.shopping_cart

-- DROP TABLE public.shopping_cart;

CREATE TABLE public.shopping_cart
(
    id bigint NOT NULL,
    identifier character varying(255) COLLATE pg_catalog."default" NOT NULL,
    instance character varying(255) COLLATE pg_catalog."default" NOT NULL,
    content text COLLATE pg_catalog."default" NOT NULL,
    user_id bigint NOT NULL,
    company_id bigint NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    discount_id bigint,
    total_cargo_price money,
    total_cart_price money,
    total_price money,
    CONSTRAINT shopping_cart_pkey PRIMARY KEY (id),
    CONSTRAINT unk UNIQUE (identifier)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.shopping_cart
    OWNER to postgres;



-- Table: public.discount

-- DROP TABLE public.discount;

CREATE TABLE public.discount
(
    id bigint NOT NULL,
    order_id bigint,
    discount_percent integer,
    status integer,
    code character varying(100) COLLATE pg_catalog."default",
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    CONSTRAINT discount_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.discount
    OWNER to postgres;




-- SEQUENCE: public.blog_categories_id_seq

-- DROP SEQUENCE public.blog_categories_id_seq;

CREATE SEQUENCE public.blog_categories_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.blog_categories_id_seq
    OWNER TO postgres;



-- SEQUENCE: public.blogs_id_seq

-- DROP SEQUENCE public.blogs_id_seq;

CREATE SEQUENCE public.blogs_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.blogs_id_seq
    OWNER TO postgres;


