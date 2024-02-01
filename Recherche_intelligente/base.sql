create role recherche login password 'recherche';

drop database recherche;

create database recherche;

alter database recherche owner to recherche;

\q

psql -U recherche recherche
recherche

create table produit(
    id serial primary key,
    designation text
);
insert into produit values (default,'telephone');
insert into produit values (default,'voiture');
insert into produit values (default,'ordinateur');

create table parametre_recherche(
    cle text primary key,
    valeur text,
    ordre int
);
insert into parametre_recherche values ('meilleur','order by $ asc',10);
insert into parametre_recherche values ('pire','order by $ desc',10);
insert into parametre_recherche values ('recent','order by date_sortie desc',10);
insert into parametre_recherche values ('ancien','order by date_sortie asc',10);
insert into parametre_recherche values ('superieur','and #<$',5);
insert into parametre_recherche values ('inferieur','and $<#',5);
insert into parametre_recherche values ('entre','and $ between # and @',5);

'telephone prix entre 200,800'
'select * from produit_view where designation like '%(telephone)%' and (prix) between (200) and (800)'

'prix voiture meilleur'
'select * from produit_view where designation like '%(voiture)%' order by (prix) asc'

'meilleur telephone qualite/prix'
'select * from produit_view where designation like '%(telephone)%' order by (qualite/prix) asc'

'ordinateur entre 10,54 prix recent date_sortie'
'select * from produit_view where designation like '%(ordinateur)%' and (prix) between 10 and 54 order by (date_sortie) asc'

'samsung pire telephone prix inferieur 40000'
'select * from produit_view where designation like '%(telephone)%' and model like '%(samsung)%' and (prix)<40000 order by (prix) desc'

create table produit_detail(
    id_produit int,
    date_sortie date,
    model text unique,
    qualite int,
    prix double precision
);
insert into produit_detail values (1,'2020-1-5','samsung S10',7,2500.0);
insert into produit_detail values (1,'2022-4-4','iphone XE',8,4000.0);
insert into produit_detail values (1,'2022-11-18','google PIXEL6',8,3200.0);
insert into produit_detail values (2,'2006-1-2','mitsubishi L200',6,2500000.0);
insert into produit_detail values (2,'2019-6-6','ford RAPTOR',7,9200000.0);
insert into produit_detail values (3,'2020-1-5','nissan NP300',5,200000.0);
insert into produit_detail values (3,'2021-2-12','fujitsu',6,820000.0);
insert into produit_detail values (3,'2023-7-14','acer',8,1200000.0);

create or replace view v_produit_detail as
    select 
    id_produit,designation,date_sortie,model,qualite,prix 
    from produit 
    join produit_detail on id=id_produit;