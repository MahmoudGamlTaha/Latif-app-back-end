ALTER TABLE public.item_category ADD active bool NULL DEFAULT true;
____________________________________________________________________________
insert into category_type (id, name, name_ar) values(1,'PETS', 'حيوانات أليفة');
insert into category_type (id, name, name_ar) values(3,'ACCESSORIES', 'خدمات');
insert into category_type (id, name, name_ar) values(3,'SERVICE', 'خدمات');
insert into category_type (id, name, name_ar) values(4,'PET CARE', 'عناية الحيوان');
insert into category_type (id, name, name_ar) values(6,'DELIVERY', 'توصيل');
insert into category_type (id, name, name_ar) values(5,'Food', 'طعام حيوانات');
insert into category_type (id, name, name_ar) values(7,'VETERINARY', 'بيطري');
select nextval('category_type_id_seq') ;
select nextval('category_type_id_seq') ;
select nextval('category_type_id_seq') ;
select nextval('category_type_id_seq') ;
select nextval('category_type_id_seq') ;
select nextval('category_type_id_seq') ;
___________________________________________________________________________________________
CREATE SEQUENCE category_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
	
	CREATE TABLE category_type (
    id bigint NOT NULL,
    name character varying(250) NOT NULL,
    name_ar character varying(250),
	active bool,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);
