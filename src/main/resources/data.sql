insert into shop(id,name) values((select max(id)+1 from shop),'tienda-1');
insert into shop(id, name) values((select max(id)+1 from shop),'tienda-2');
insert into shop(id, name) values((select max(id)+1 from shop),'tienda-3');
insert into shop(id, name) values((select max(id)+1 from shop),'tienda-4');
insert into client(id, identificacion, foto, nombre) values((select max(id)+1 from client),'1000000008','client-1.jpg','client-1');