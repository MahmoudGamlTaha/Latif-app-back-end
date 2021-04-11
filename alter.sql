drop table public.user_ads_image
CREATE TABLE public.user_ads_image (
	id int8 NOT NULL,
	is_external_url bool NULL,
	image varchar(250) NULL,
	user_ads_id int8 NOT NULL,
	created_at date NULL,
	update_at date NULL
);
__________________________________________________________________________________________
ALTER TABLE public.user_ads ALTER COLUMN training TYPE varchar(200) USING training::varchar;
__________________________________________________
update category_type set code = 'PETS' where id = 1;
update category_type set code = 'ACCESSORIES' where id = 2;
update category_type set code = 'SERVICE' where id = 3;
update category_type set code = 'PET_CARE' where id = 4;
update category_type set code = 'DELIVERY' where id = 6;
update category_type set code = 'Food' where id = 5;
update category_type set code = 'VETERINARY' where id = 7;
___________________________________________________________________
ALTER TABLE public.category_type ADD code varchar(250) NULL;
ALTER TABLE public.item_category ADD active bool NULL DEFAULT true;
____________________________________________________________________________
insert into category_type (id, name, name_ar) values(1,'PETS', 'حيوانات أليفة');
insert into category_type (id, name, name_ar) values(2,'ACCESSORIES', 'خدمات');
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
