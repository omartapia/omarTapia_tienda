insert into shop(id,name) values(1,'tienda-1');
insert into shop(id,name) values(2,'tienda-2');
insert into shop(id,name) values(3,'tienda-3');
insert into shop(id,name) values(4,'tienda-4');
insert into inventory(id,shop_id,product_id) values(1,1,1);
insert into inventory(id,shop_id,product_id) values(2,1,2);
insert into inventory(id,shop_id,product_id) values(3,1,3);
insert into inventory(id,shop_id,product_id) values(4,2,1);
insert into inventory(id,shop_id,product_id) values(5,3,1);
insert into inventory(id,shop_id,product_id) values(6,3,4);
insert into client(id,identificacion,foto,nombre) values(1,'1000000008','client-1.jpg','client-1');