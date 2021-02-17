PGDMP     "    .        
        y         	   Latif_APP    10.15    10.15 t    D           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            E           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false            F           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                       false            G           1262    16393 	   Latif_APP    DATABASE     �   CREATE DATABASE "Latif_APP" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'English_United States.1252' LC_CTYPE = 'English_United States.1252';
    DROP DATABASE "Latif_APP";
             postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
             postgres    false            H           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                  postgres    false    3                        3079    12924    plpgsql 	   EXTENSION     ?   CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;
    DROP EXTENSION plpgsql;
                  false            I           0    0    EXTENSION plpgsql    COMMENT     @   COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';
                       false    1            �            1259    32861    acc_item_attributes    TABLE     �   CREATE TABLE public.acc_item_attributes (
    id bigint NOT NULL,
    item_id bigint NOT NULL,
    price money,
    color character varying,
    height double precision,
    width double precision,
    stock double precision
);
 '   DROP TABLE public.acc_item_attributes;
       public         postgres    false    3            �            1259    24632 
   activities    TABLE     �   CREATE TABLE public.activities (
    id bigint NOT NULL,
    name character varying(250),
    description character varying(250),
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);
    DROP TABLE public.activities;
       public         postgres    false    3            �            1259    24585 
   admin_menu    TABLE     �  CREATE TABLE public.admin_menu (
    id bigint NOT NULL,
    parent_id integer DEFAULT 0 NOT NULL,
    "order" integer DEFAULT 0 NOT NULL,
    title character varying(50) NOT NULL,
    icon character varying(50) NOT NULL,
    uri character varying(50) DEFAULT NULL::character varying,
    permission character varying(255) DEFAULT NULL::character varying,
    active boolean DEFAULT true NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);
    DROP TABLE public.admin_menu;
       public         postgres    false    3            �            1259    24593    admin_operation_log    TABLE     g  CREATE TABLE public.admin_operation_log (
    id bigint NOT NULL,
    company_id integer,
    user_id integer NOT NULL,
    path character varying(255) NOT NULL,
    method character varying(10) NOT NULL,
    ip character varying(15) NOT NULL,
    input text NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);
 '   DROP TABLE public.admin_operation_log;
       public         postgres    false    3            �            1259    32884    ads_sequence    SEQUENCE     u   CREATE SEQUENCE public.ads_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.ads_sequence;
       public       postgres    false    3            �            1259    24646    blog_categories    TABLE     �   CREATE TABLE public.blog_categories (
    id bigint NOT NULL,
    name character varying(250),
    description character varying(250),
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);
 #   DROP TABLE public.blog_categories;
       public         postgres    false    3            �            1259    24640    blogs    TABLE     g  CREATE TABLE public.blogs (
    id bigint,
    title character varying(250),
    category_id bigint,
    image character varying(250),
    path character varying(250),
    "description " character varying(250),
    user_id bigint,
    date timestamp without time zone,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);
    DROP TABLE public.blogs;
       public         postgres    false    3            �            1259    24797    brands    TABLE     �   CREATE TABLE public.brands (
    id bigint NOT NULL,
    name character varying(250) NOT NULL,
    active boolean,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);
    DROP TABLE public.brands;
       public         postgres    false    3            �            1259    24807    colors    TABLE     �   CREATE TABLE public.colors (
    id bigint NOT NULL,
    name character varying(250),
    hex character varying(250),
    active boolean DEFAULT true
);
    DROP TABLE public.colors;
       public         postgres    false    3            �            1259    16548 	   companies    TABLE     V  CREATE TABLE public.companies (
    id integer NOT NULL,
    name character varying(250) NOT NULL,
    activity_id integer NOT NULL,
    seller boolean DEFAULT true NOT NULL,
    iban character varying(255) DEFAULT NULL::character varying,
    logo character varying(250) DEFAULT NULL::character varying,
    path character varying(250) DEFAULT NULL::character varying,
    active boolean DEFAULT true NOT NULL,
    fax character varying(250) DEFAULT NULL::character varying,
    general_manger character varying(250) DEFAULT NULL::character varying,
    swiftcode character varying(100) DEFAULT NULL::character varying,
    fixed numeric(10,2) DEFAULT NULL::numeric,
    percentage numeric(10,2) DEFAULT NULL::numeric,
    payment_type character varying(100) DEFAULT NULL::character varying,
    tansaction_fee numeric(10,2) DEFAULT NULL::numeric,
    manager character varying(250) DEFAULT NULL::character varying,
    code character varying(250) DEFAULT NULL::character varying,
    published_date timestamp without time zone,
    parent_id integer,
    longitude character varying(250) DEFAULT NULL::character varying,
    latitude character varying(250) DEFAULT NULL::character varying,
    notes character varying(300) DEFAULT NULL::character varying,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);
    DROP TABLE public.companies;
       public         postgres    false    3            �            1259    16417    item_category    TABLE     !  CREATE TABLE public.item_category (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    parent_id integer,
    company_id bigint,
    icon character varying(250),
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    type integer
);
 !   DROP TABLE public.item_category;
       public         postgres    false    3            �            1259    32882    item_category_sequence    SEQUENCE        CREATE SEQUENCE public.item_category_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.item_category_sequence;
       public       postgres    false    3    199            J           0    0    item_category_sequence    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.item_category_sequence OWNED BY public.item_category.id;
            public       postgres    false    232            �            1259    24802    item_images    TABLE     �   CREATE TABLE public.item_images (
    id bigint NOT NULL,
    is_external_url boolean,
    image character varying(250),
    item_id bigint NOT NULL
);
    DROP TABLE public.item_images;
       public         postgres    false    3            �            1259    16495 
   item_order    TABLE     �  CREATE TABLE public.item_order (
    id bigint NOT NULL,
    vendor_id integer,
    user_id integer DEFAULT 0 NOT NULL,
    subtotal numeric(10,2) DEFAULT 0,
    shipping numeric(10,2) DEFAULT 0,
    discount numeric(10,2) DEFAULT 0,
    payment_status integer DEFAULT 0 NOT NULL,
    shipping_status integer DEFAULT 0 NOT NULL,
    status integer DEFAULT 0 NOT NULL,
    tax numeric(10,2) DEFAULT 0,
    total integer DEFAULT 0,
    currency character varying(50) DEFAULT NULL::character varying,
    exchange_rate double precision,
    received integer DEFAULT 0,
    balance integer DEFAULT 0,
    toname character varying(100) DEFAULT NULL::character varying,
    address1 character varying(100) DEFAULT NULL::character varying,
    address2 character varying(100) DEFAULT NULL::character varying,
    s_address_id integer,
    country character varying(200) DEFAULT NULL::character varying,
    phone character(50) DEFAULT NULL::bpchar,
    email character(100) DEFAULT NULL::bpchar,
    address_id integer,
    delivery_id integer,
    company_name character varying(200) DEFAULT NULL::character varying,
    comment character varying(300) DEFAULT NULL::character varying,
    payment_method character varying(100) DEFAULT NULL::character varying,
    transaction character varying(100) DEFAULT NULL::character varying,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    company_id bigint
);
    DROP TABLE public.item_order;
       public         postgres    false    3            �            1259    16523    item_order_detail    TABLE     �  CREATE TABLE public.item_order_detail (
    id bigint NOT NULL,
    vendor_id integer,
    order_id bigint DEFAULT 0 NOT NULL,
    item_id bigint NOT NULL,
    name character varying(255) DEFAULT NULL::character varying,
    price numeric(10,2) DEFAULT 0,
    qty integer DEFAULT 0,
    total_price numeric(10,2) DEFAULT 0,
    code character varying(25) DEFAULT NULL::character varying,
    status integer DEFAULT 0 NOT NULL,
    type character varying(100) DEFAULT NULL::character varying,
    currency character varying(50) DEFAULT NULL::character varying,
    exchange_rate double precision,
    attribute character varying(100),
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    item_attribute_id bigint
);
 %   DROP TABLE public.item_order_detail;
       public         postgres    false    3            �            1259    24742    items    TABLE     �  CREATE TABLE public.items (
    id bigint NOT NULL,
    code character varying(20) DEFAULT NULL::character varying,
    image character varying(100) DEFAULT NULL::character varying,
    brand_id integer,
    category_id integer,
    category_other character varying(50) DEFAULT NULL::character varying,
    sort integer DEFAULT 0 NOT NULL,
    active integer,
    created_by bigint,
    date_lastview timestamp without time zone,
    date_available timestamp without time zone,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    company_id bigint,
    type integer,
    name character varying(250),
    url character varying(250)
);
    DROP TABLE public.items;
       public         postgres    false    3            �            1259    16394    oauth_access_token    TABLE     J  CREATE TABLE public.oauth_access_token (
    token_id character varying(255),
    token character varying(350),
    authentication_id character varying(255) NOT NULL,
    user_name character varying(255),
    client_id character varying(255),
    authentication character varying(350),
    refresh_token character varying(255)
);
 &   DROP TABLE public.oauth_access_token;
       public         postgres    false    3            �            1259    16402    oauth_approvals    TABLE     2  CREATE TABLE public.oauth_approvals (
    userid character varying(255),
    clientid character varying(255),
    scope character varying(255),
    status character varying(10),
    expiresat timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    lastmodifiedat timestamp without time zone NOT NULL
);
 #   DROP TABLE public.oauth_approvals;
       public         postgres    false    3            �            1259    16409    oauth_client_details    TABLE       CREATE TABLE public.oauth_client_details (
    client_id character varying(255) NOT NULL,
    resource_ids character varying(255),
    client_secret character varying(255),
    scope character varying(255),
    authorized_grant_types character varying(255),
    web_server_redirect_uri character varying(255),
    authorities character varying(255),
    access_token_validity integer,
    refresh_token_validity integer,
    additional_information character varying(4096),
    autoapprove character varying(255)
);
 (   DROP TABLE public.oauth_client_details;
       public         postgres    false    3            �            1259    24789    other_shopping_cart    TABLE       CREATE TABLE public.other_shopping_cart (
    id bigint,
    user_id bigint,
    item_id bigint,
    item_name character varying(250),
    item_price money,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    quantity integer
);
 '   DROP TABLE public.other_shopping_cart;
       public         postgres    false    3            K           0    0    TABLE other_shopping_cart    COMMENT     s   COMMENT ON TABLE public.other_shopping_cart IS 'check different and use the best between shopping_cart and this ';
            public       postgres    false    224            �            1259    24719    pets_category    TABLE     �   CREATE TABLE public.pets_category (
    id bigint NOT NULL,
    name character varying(250),
    description character varying(250),
    icon character varying(250),
    category_id bigint,
    create_at date,
    update_at time without time zone
);
 !   DROP TABLE public.pets_category;
       public         postgres    false    3            �            1259    24722    pets_category_id_seq    SEQUENCE     �   ALTER TABLE public.pets_category ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.pets_category_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public       postgres    false    218    3            �            1259    24769    pets_item_attributes    TABLE     �  CREATE TABLE public.pets_item_attributes (
    id bigint NOT NULL,
    item_id bigint NOT NULL,
    price numeric(10,2) NOT NULL,
    cost numeric(10,2) DEFAULT 0,
    date_of_birth date,
    breed character varying(250) DEFAULT NULL::character varying,
    origin character varying(250) DEFAULT NULL::character varying,
    stock integer DEFAULT 0 NOT NULL,
    path character varying(250) DEFAULT NULL::character varying,
    sold integer DEFAULT 0 NOT NULL,
    weaned boolean,
    color character varying(250) DEFAULT NULL::character varying,
    neutering boolean,
    vaccination_certificate boolean,
    treatment integer DEFAULT 0,
    training boolean,
    barking_problem boolean,
    passport boolean,
    food integer,
    play_with_kids boolean,
    diseases_disabilities boolean,
    diseases_disabilities_desc character varying(300) DEFAULT NULL::character varying,
    type integer,
    option character varying(200) DEFAULT NULL::character varying,
    longitude character varying(200) DEFAULT NULL::character varying,
    latitude character varying(200) DEFAULT NULL::character varying,
    description character varying(350),
    short_description character varying(250),
    image character varying(250)
);
 (   DROP TABLE public.pets_item_attributes;
       public         postgres    false    3            �            1259    24792    roles    TABLE     �   CREATE TABLE public.roles (
    id bigint NOT NULL,
    name character varying(250) NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);
    DROP TABLE public.roles;
       public         postgres    false    3            �            1259    32869    service_type    TABLE     �   CREATE TABLE public.service_type (
    id bigint NOT NULL,
    name character varying(250),
    active boolean,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);
     DROP TABLE public.service_type;
       public         postgres    false    3            �            1259    32874    services    TABLE     �   CREATE TABLE public.services (
    id bigint NOT NULL,
    icon character varying(250),
    name character varying(250),
    created_at timestamp without time zone,
    updated_at time without time zone
);
    DROP TABLE public.services;
       public         postgres    false    3            �            1259    16580    shopping_cart    TABLE     L  CREATE TABLE public.shopping_cart (
    id bigint NOT NULL,
    identifier character varying(255) NOT NULL,
    instance character varying(255) NOT NULL,
    content text NOT NULL,
    user_id bigint NOT NULL,
    company_id bigint NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);
 !   DROP TABLE public.shopping_cart;
       public         postgres    false    3            �            1259    24626    subscription types    TABLE     �   CREATE TABLE public."subscription types" (
    id bigint,
    name character varying(250),
    ads_number integer,
    period_in_days integer,
    price numeric(10,2),
    number_user integer,
    description character varying(250)
);
 (   DROP TABLE public."subscription types";
       public         postgres    false    3            �            1259    24693    user_ads    TABLE     �  CREATE TABLE public.user_ads (
    id bigint NOT NULL,
    code character varying(20) DEFAULT NULL::character varying,
    image character varying(100) DEFAULT NULL::character varying,
    brand_id integer DEFAULT 0,
    category_id integer DEFAULT 0 NOT NULL,
    price numeric(10,2) NOT NULL,
    cost numeric(10,2) DEFAULT 0,
    date_of_birth date,
    breed character varying(250) DEFAULT NULL::character varying,
    origin character varying(250) DEFAULT NULL::character varying,
    stock integer DEFAULT 0 NOT NULL,
    path character varying(250) DEFAULT NULL::character varying,
    sold integer DEFAULT 0 NOT NULL,
    weaned boolean,
    neutering boolean,
    vaccination_certificate boolean,
    treatment integer DEFAULT 0,
    training boolean,
    barking_problem boolean,
    passport boolean,
    food integer,
    play_with_kids boolean,
    diseases_disabilities boolean,
    diseases_disabilities_desc character varying(300) DEFAULT NULL::character varying,
    option character varying(200) DEFAULT NULL::character varying,
    longitude character varying(200) DEFAULT NULL::character varying,
    latitude character varying(200) DEFAULT NULL::character varying,
    sort integer DEFAULT 0 NOT NULL,
    status integer,
    created_by bigint,
    date_lastview timestamp without time zone,
    date_available timestamp without time zone,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    company_id bigint,
    type character varying(100) NOT NULL,
    description character varying(500),
    short_description character varying(250),
    category_type bigint,
    name character varying(250),
    used boolean
);
    DROP TABLE public.user_ads;
       public         postgres    false    3            �            1259    24731    user_ads_image    TABLE     �   CREATE TABLE public.user_ads_image (
    id bigint NOT NULL,
    is_external_url boolean,
    image character varying(250),
    user_ads_id bigint NOT NULL
);
 "   DROP TABLE public.user_ads_image;
       public         postgres    false    3            �            1259    32888    user_interested     TABLE     �   CREATE TABLE public."user_interested " (
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    item_cat_id bigint,
    "interested " integer,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);
 &   DROP TABLE public."user_interested ";
       public         postgres    false    3            �            1259    32886    user_interested _id_seq    SEQUENCE     �   CREATE SEQUENCE public."user_interested _id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 0   DROP SEQUENCE public."user_interested _id_seq";
       public       postgres    false    235    3            L           0    0    user_interested _id_seq    SEQUENCE OWNED BY     W   ALTER SEQUENCE public."user_interested _id_seq" OWNED BY public."user_interested ".id;
            public       postgres    false    234            �            1259    24736    user_permission_service    TABLE     �   CREATE TABLE public.user_permission_service (
    id bigint,
    service character varying(250),
    "permission_id " bigint,
    function character varying(250),
    created_at date,
    updated_at date,
    allow boolean
);
 +   DROP TABLE public.user_permission_service;
       public         postgres    false    3            �            1259    24599    user_permissions    TABLE     _  CREATE TABLE public.user_permissions (
    id bigint NOT NULL,
    company_id integer,
    name character varying(50) NOT NULL,
    slug character varying(50) NOT NULL,
    http_method character varying(255) DEFAULT NULL::character varying,
    http_path text,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);
 $   DROP TABLE public.user_permissions;
       public         postgres    false    3            �            1259    24610    user_role_permissions    TABLE     �   CREATE TABLE public.user_role_permissions (
    role_id integer NOT NULL,
    permission_id integer NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);
 )   DROP TABLE public.user_role_permissions;
       public         postgres    false    3            �            1259    24613    user_role_users    TABLE     �   CREATE TABLE public.user_role_users (
    id integer NOT NULL,
    role_id integer NOT NULL,
    user_id integer NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);
 #   DROP TABLE public.user_role_users;
       public         postgres    false    3            �            1259    24606 
   user_roles    TABLE     �   CREATE TABLE public.user_roles (
    id bigint NOT NULL,
    company_id integer,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    role_id bigint,
    user_id bigint
);
    DROP TABLE public.user_roles;
       public         postgres    false    3            �            1259    24621    user_subscription    TABLE     )  CREATE TABLE public.user_subscription (
    id bigint NOT NULL,
    user_id bigint,
    subscription_id bigint,
    start_date timestamp without time zone NOT NULL,
    end_date timestamp without time zone,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);
 %   DROP TABLE public.user_subscription;
       public         postgres    false    3            �            1259    24616 	   user_type    TABLE     �   CREATE TABLE public.user_type (
    id bigint NOT NULL,
    name character varying(250),
    created_at timestamp without time zone,
    updated_at timestamp with time zone
);
    DROP TABLE public.user_type;
       public         postgres    false    3            �            1259    16572    users    TABLE     +  CREATE TABLE public.users (
    id bigint NOT NULL,
    company_id bigint,
    fname character varying(255) NOT NULL,
    lname character varying(255) NOT NULL,
    email character varying(50) NOT NULL,
    password character varying(255) NOT NULL,
    address1 character varying(255) NOT NULL,
    address2 character varying(255) DEFAULT NULL::character varying,
    mobile character varying(255) NOT NULL,
    active boolean,
    user_type integer,
    remember_token character varying(100) DEFAULT NULL::character varying,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    country_id bigint,
    state_id bigint,
    city_id bigint,
    country character varying(250),
    state character varying(250),
    city character varying(250),
    email_verified boolean
);
    DROP TABLE public.users;
       public         postgres    false    3            p           2604    32891    user_interested  id    DEFAULT     ~   ALTER TABLE ONLY public."user_interested " ALTER COLUMN id SET DEFAULT nextval('public."user_interested _id_seq"'::regclass);
 D   ALTER TABLE public."user_interested " ALTER COLUMN id DROP DEFAULT;
       public       postgres    false    234    235    235            ;          0    32861    acc_item_attributes 
   TABLE DATA               ^   COPY public.acc_item_attributes (id, item_id, price, color, height, width, stock) FROM stdin;
    public       postgres    false    229   �       ,          0    24632 
   activities 
   TABLE DATA               S   COPY public.activities (id, name, description, created_at, updated_at) FROM stdin;
    public       postgres    false    214   )�       #          0    24585 
   admin_menu 
   TABLE DATA               z   COPY public.admin_menu (id, parent_id, "order", title, icon, uri, permission, active, created_at, updated_at) FROM stdin;
    public       postgres    false    205   F�       $          0    24593    admin_operation_log 
   TABLE DATA               w   COPY public.admin_operation_log (id, company_id, user_id, path, method, ip, input, created_at, updated_at) FROM stdin;
    public       postgres    false    206   c�       .          0    24646    blog_categories 
   TABLE DATA               X   COPY public.blog_categories (id, name, description, created_at, updated_at) FROM stdin;
    public       postgres    false    216   ��       -          0    24640    blogs 
   TABLE DATA               {   COPY public.blogs (id, title, category_id, image, path, "description ", user_id, date, created_at, updated_at) FROM stdin;
    public       postgres    false    215   ��       8          0    24797    brands 
   TABLE DATA               J   COPY public.brands (id, name, active, created_at, updated_at) FROM stdin;
    public       postgres    false    226   ��       :          0    24807    colors 
   TABLE DATA               7   COPY public.colors (id, name, hex, active) FROM stdin;
    public       postgres    false    228   ׭                  0    16548 	   companies 
   TABLE DATA                 COPY public.companies (id, name, activity_id, seller, iban, logo, path, active, fax, general_manger, swiftcode, fixed, percentage, payment_type, tansaction_fee, manager, code, published_date, parent_id, longitude, latitude, notes, created_at, updated_at) FROM stdin;
    public       postgres    false    202   ��                 0    16417    item_category 
   TABLE DATA               l   COPY public.item_category (id, name, parent_id, company_id, icon, created_at, updated_at, type) FROM stdin;
    public       postgres    false    199   �       9          0    24802    item_images 
   TABLE DATA               J   COPY public.item_images (id, is_external_url, image, item_id) FROM stdin;
    public       postgres    false    227   ��                 0    16495 
   item_order 
   TABLE DATA               m  COPY public.item_order (id, vendor_id, user_id, subtotal, shipping, discount, payment_status, shipping_status, status, tax, total, currency, exchange_rate, received, balance, toname, address1, address2, s_address_id, country, phone, email, address_id, delivery_id, company_name, comment, payment_method, transaction, created_at, updated_at, company_id) FROM stdin;
    public       postgres    false    200   ��                 0    16523    item_order_detail 
   TABLE DATA               �   COPY public.item_order_detail (id, vendor_id, order_id, item_id, name, price, qty, total_price, code, status, type, currency, exchange_rate, attribute, created_at, updated_at, item_attribute_id) FROM stdin;
    public       postgres    false    201   Ȯ       4          0    24742    items 
   TABLE DATA               �   COPY public.items (id, code, image, brand_id, category_id, category_other, sort, active, created_by, date_lastview, date_available, created_at, updated_at, company_id, type, name, url) FROM stdin;
    public       postgres    false    222   �                 0    16394    oauth_access_token 
   TABLE DATA               �   COPY public.oauth_access_token (token_id, token, authentication_id, user_name, client_id, authentication, refresh_token) FROM stdin;
    public       postgres    false    196   �                 0    16402    oauth_approvals 
   TABLE DATA               e   COPY public.oauth_approvals (userid, clientid, scope, status, expiresat, lastmodifiedat) FROM stdin;
    public       postgres    false    197   �                 0    16409    oauth_client_details 
   TABLE DATA               �   COPY public.oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove) FROM stdin;
    public       postgres    false    198   <�       6          0    24789    other_shopping_cart 
   TABLE DATA               |   COPY public.other_shopping_cart (id, user_id, item_id, item_name, item_price, created_at, updated_at, quantity) FROM stdin;
    public       postgres    false    224   Y�       0          0    24719    pets_category 
   TABLE DATA               g   COPY public.pets_category (id, name, description, icon, category_id, create_at, update_at) FROM stdin;
    public       postgres    false    218   v�       5          0    24769    pets_item_attributes 
   TABLE DATA               o  COPY public.pets_item_attributes (id, item_id, price, cost, date_of_birth, breed, origin, stock, path, sold, weaned, color, neutering, vaccination_certificate, treatment, training, barking_problem, passport, food, play_with_kids, diseases_disabilities, diseases_disabilities_desc, type, option, longitude, latitude, description, short_description, image) FROM stdin;
    public       postgres    false    223   ��       7          0    24792    roles 
   TABLE DATA               A   COPY public.roles (id, name, created_at, updated_at) FROM stdin;
    public       postgres    false    225   ��       <          0    32869    service_type 
   TABLE DATA               P   COPY public.service_type (id, name, active, created_at, updated_at) FROM stdin;
    public       postgres    false    230   ͯ       =          0    32874    services 
   TABLE DATA               J   COPY public.services (id, icon, name, created_at, updated_at) FROM stdin;
    public       postgres    false    231   �       "          0    16580    shopping_cart 
   TABLE DATA               w   COPY public.shopping_cart (id, identifier, instance, content, user_id, company_id, created_at, updated_at) FROM stdin;
    public       postgres    false    204   �       +          0    24626    subscription types 
   TABLE DATA               u   COPY public."subscription types" (id, name, ads_number, period_in_days, price, number_user, description) FROM stdin;
    public       postgres    false    213   $�       /          0    24693    user_ads 
   TABLE DATA               �  COPY public.user_ads (id, code, image, brand_id, category_id, price, cost, date_of_birth, breed, origin, stock, path, sold, weaned, neutering, vaccination_certificate, treatment, training, barking_problem, passport, food, play_with_kids, diseases_disabilities, diseases_disabilities_desc, option, longitude, latitude, sort, status, created_by, date_lastview, date_available, created_at, updated_at, company_id, type, description, short_description, category_type, name, used) FROM stdin;
    public       postgres    false    217   A�       2          0    24731    user_ads_image 
   TABLE DATA               Q   COPY public.user_ads_image (id, is_external_url, image, user_ads_id) FROM stdin;
    public       postgres    false    220   ^�       A          0    32888    user_interested  
   TABLE DATA               m   COPY public."user_interested " (id, user_id, item_cat_id, "interested ", created_at, updated_at) FROM stdin;
    public       postgres    false    235   {�       3          0    24736    user_permission_service 
   TABLE DATA               y   COPY public.user_permission_service (id, service, "permission_id ", function, created_at, updated_at, allow) FROM stdin;
    public       postgres    false    221   ��       %          0    24599    user_permissions 
   TABLE DATA               v   COPY public.user_permissions (id, company_id, name, slug, http_method, http_path, created_at, updated_at) FROM stdin;
    public       postgres    false    207   ��       '          0    24610    user_role_permissions 
   TABLE DATA               _   COPY public.user_role_permissions (role_id, permission_id, created_at, updated_at) FROM stdin;
    public       postgres    false    209   Ұ       (          0    24613    user_role_users 
   TABLE DATA               W   COPY public.user_role_users (id, role_id, user_id, created_at, updated_at) FROM stdin;
    public       postgres    false    210   �       &          0    24606 
   user_roles 
   TABLE DATA               ^   COPY public.user_roles (id, company_id, created_at, updated_at, role_id, user_id) FROM stdin;
    public       postgres    false    208   �       *          0    24621    user_subscription 
   TABLE DATA               w   COPY public.user_subscription (id, user_id, subscription_id, start_date, end_date, created_at, updated_at) FROM stdin;
    public       postgres    false    212   )�       )          0    24616 	   user_type 
   TABLE DATA               E   COPY public.user_type (id, name, created_at, updated_at) FROM stdin;
    public       postgres    false    211   F�       !          0    16572    users 
   TABLE DATA               �   COPY public.users (id, company_id, fname, lname, email, password, address1, address2, mobile, active, user_type, remember_token, created_at, updated_at, country_id, state_id, city_id, country, state, city, email_verified) FROM stdin;
    public       postgres    false    203   c�       M           0    0    ads_sequence    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.ads_sequence', 1, false);
            public       postgres    false    233            N           0    0    item_category_sequence    SEQUENCE SET     E   SELECT pg_catalog.setval('public.item_category_sequence', 15, true);
            public       postgres    false    232            O           0    0    pets_category_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.pets_category_id_seq', 1, false);
            public       postgres    false    219            P           0    0    user_interested _id_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public."user_interested _id_seq"', 1, false);
            public       postgres    false    234            �           2606    24796    roles Roles_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.roles
    ADD CONSTRAINT "Roles_pkey" PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.roles DROP CONSTRAINT "Roles_pkey";
       public         postgres    false    225            �           2606    32868 ,   acc_item_attributes acc_item_attributes_pkey 
   CONSTRAINT     j   ALTER TABLE ONLY public.acc_item_attributes
    ADD CONSTRAINT acc_item_attributes_pkey PRIMARY KEY (id);
 V   ALTER TABLE ONLY public.acc_item_attributes DROP CONSTRAINT acc_item_attributes_pkey;
       public         postgres    false    229            �           2606    24639    activities activities_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.activities
    ADD CONSTRAINT activities_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.activities DROP CONSTRAINT activities_pkey;
       public         postgres    false    214            �           2606    24655    admin_menu admin_menu_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.admin_menu
    ADD CONSTRAINT admin_menu_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.admin_menu DROP CONSTRAINT admin_menu_pkey;
       public         postgres    false    205            �           2606    24653 $   blog_categories blog_categories_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.blog_categories
    ADD CONSTRAINT blog_categories_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY public.blog_categories DROP CONSTRAINT blog_categories_pkey;
       public         postgres    false    216            �           2606    24801    brands brands_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.brands
    ADD CONSTRAINT brands_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.brands DROP CONSTRAINT brands_pkey;
       public         postgres    false    226            �           2606    24815    colors color_pkey 
   CONSTRAINT     O   ALTER TABLE ONLY public.colors
    ADD CONSTRAINT color_pkey PRIMARY KEY (id);
 ;   ALTER TABLE ONLY public.colors DROP CONSTRAINT color_pkey;
       public         postgres    false    228            v           2606    16421     item_category item_category_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.item_category
    ADD CONSTRAINT item_category_pkey PRIMARY KEY (id);
 J   ALTER TABLE ONLY public.item_category DROP CONSTRAINT item_category_pkey;
       public         postgres    false    199            �           2606    24806    item_images item_image_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY public.item_images
    ADD CONSTRAINT item_image_pkey PRIMARY KEY (id);
 E   ALTER TABLE ONLY public.item_images DROP CONSTRAINT item_image_pkey;
       public         postgres    false    227            z           2606    24665    item_order item_order_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.item_order
    ADD CONSTRAINT item_order_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.item_order DROP CONSTRAINT item_order_pkey;
       public         postgres    false    200            �           2606    24788 $   pets_item_attributes items_attr_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.pets_item_attributes
    ADD CONSTRAINT items_attr_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY public.pets_item_attributes DROP CONSTRAINT items_attr_pkey;
       public         postgres    false    223            �           2606    24750    items items_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.items
    ADD CONSTRAINT items_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.items DROP CONSTRAINT items_pkey;
       public         postgres    false    222            x           2606    16423    item_category name 
   CONSTRAINT     M   ALTER TABLE ONLY public.item_category
    ADD CONSTRAINT name UNIQUE (name);
 <   ALTER TABLE ONLY public.item_category DROP CONSTRAINT name;
       public         postgres    false    199            r           2606    16401 *   oauth_access_token oauth_access_token_pkey 
   CONSTRAINT     w   ALTER TABLE ONLY public.oauth_access_token
    ADD CONSTRAINT oauth_access_token_pkey PRIMARY KEY (authentication_id);
 T   ALTER TABLE ONLY public.oauth_access_token DROP CONSTRAINT oauth_access_token_pkey;
       public         postgres    false    196            t           2606    16416 .   oauth_client_details oauth_client_details_pkey 
   CONSTRAINT     s   ALTER TABLE ONLY public.oauth_client_details
    ADD CONSTRAINT oauth_client_details_pkey PRIMARY KEY (client_id);
 X   ALTER TABLE ONLY public.oauth_client_details DROP CONSTRAINT oauth_client_details_pkey;
       public         postgres    false    198            �           2606    32873    service_type service_type_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.service_type
    ADD CONSTRAINT service_type_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.service_type DROP CONSTRAINT service_type_pkey;
       public         postgres    false    230            �           2606    32878    services services_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.services
    ADD CONSTRAINT services_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.services DROP CONSTRAINT services_pkey;
       public         postgres    false    231            |           2606    24663 (   item_order_detail shop_order_detail_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.item_order_detail
    ADD CONSTRAINT shop_order_detail_pkey PRIMARY KEY (id);
 R   ALTER TABLE ONLY public.item_order_detail DROP CONSTRAINT shop_order_detail_pkey;
       public         postgres    false    201            ~           2606    24659     shopping_cart shopping_cart_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.shopping_cart
    ADD CONSTRAINT shopping_cart_pkey PRIMARY KEY (id);
 J   ALTER TABLE ONLY public.shopping_cart DROP CONSTRAINT shopping_cart_pkey;
       public         postgres    false    204            �           2606    24661    shopping_cart unk 
   CONSTRAINT     R   ALTER TABLE ONLY public.shopping_cart
    ADD CONSTRAINT unk UNIQUE (identifier);
 ;   ALTER TABLE ONLY public.shopping_cart DROP CONSTRAINT unk;
       public         postgres    false    204            �           2606    24735 "   user_ads_image user_ads_image_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.user_ads_image
    ADD CONSTRAINT user_ads_image_pkey PRIMARY KEY (id);
 L   ALTER TABLE ONLY public.user_ads_image DROP CONSTRAINT user_ads_image_pkey;
       public         postgres    false    220            �           2606    24718    user_ads user_ads_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.user_ads
    ADD CONSTRAINT user_ads_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.user_ads DROP CONSTRAINT user_ads_pkey;
       public         postgres    false    217            �           2606    24625 (   user_subscription user_subscription_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.user_subscription
    ADD CONSTRAINT user_subscription_pkey PRIMARY KEY (id);
 R   ALTER TABLE ONLY public.user_subscription DROP CONSTRAINT user_subscription_pkey;
       public         postgres    false    212            �           2606    24620    user_type user_type_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.user_type
    ADD CONSTRAINT user_type_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.user_type DROP CONSTRAINT user_type_pkey;
       public         postgres    false    211            ;      x������ � �      ,      x������ � �      #      x������ � �      $      x������ � �      .      x������ � �      -      x������ � �      8      x������ � �      :      x������ � �             x������ � �         m   x�3�t�w��CA�\Ɯ���!�A�R&�n���⦜Ύ!X�2��:�`�1�t
rtv�q��f�e��ZR\ZP���Z�)o�Y�Z�e&���M54�a������ X�8p      9      x������ � �            x������ � �            x������ � �      4      x������ � �            x������ � �            x������ � �            x������ � �      6      x������ � �      0      x������ � �      5      x������ � �      7      x������ � �      <      x������ � �      =      x������ � �      "      x������ � �      +      x������ � �      /      x������ � �      2      x������ � �      A      x������ � �      3      x������ � �      %      x������ � �      '      x������ � �      (      x������ � �      &      x������ � �      *      x������ � �      )      x������ � �      !      x������ � �     