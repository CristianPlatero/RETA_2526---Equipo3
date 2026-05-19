use inventario_taller;


begin;

-- INSERTs PCs

insert into pcs (nombre,descripcion,estado,categoria,id_estacion,observaciones)
values  ('pc01', 'ninguno','operativo','sobremesa','EST01', 'Op'),
		('pc02', 'ninguno','operativo','sobremesa','EST01', 'Op'),
		('pc03', 'ninguno','reparacion','portatil','EST03', 'ER'),
		('pc04', 'ninguno','obsoleto','sobremesa','EST04', 'Ob'),
     --   ('pc06', 'ninguno','obsoleto','sobremesa','ARM04', 'Ob'), -- arm04 no existe en la tabla estacion
		('pc05', 'ninguno','averiado','portatil','EST02', 'Ob');
        
commit;

-- inster into para Componentes

 -- insert into materialestaller (uno)
 INSERT INTO materialestaller(nombre,descripcion,estado,cantidad,id_ubi,observaciones)
VALUES ('componente1','es un comp','obsoleto',9,'EST01','no funciona');

insert into componentes(id_matTa,id_pc)
values(last_insert_id(),1);

INSERT INTO materialestaller(nombre,descripcion,estado,cantidad,id_ubi,id_balda,observaciones)
VALUES ('componente2','es un comp','averiado',9,'ARM02',2,'no funciona');

insert into componentes(id_matTa,id_pc)
values(last_insert_id(),null);

INSERT INTO materialestaller(nombre,descripcion,estado,cantidad,id_ubi,id_balda,observaciones)
VALUES ('componente3','es un comp','operativo',9,'EST02',null,'funciona');

insert into componentes(id_matTa,id_pc)
values(last_insert_id(),5);

INSERT INTO materialestaller(nombre,descripcion,estado,cantidad,id_ubi,id_balda,observaciones)
VALUES ('componente4','es un comp','reparacion',9,'ARM02',2,'no funciona');

insert into componentes(id_matTa,id_pc)
values(last_insert_id(),null);

/*  -- error porque en la tabla balda no existe 'est02'
INSERT INTO materialestaller(nombre,descripcion,estado,cantidad,id_ubi,id_balda,observaciones)
VALUES ('componente5','es un comp','operativo',9,'EST02',1,'funciona');

insert into componentes(id_matTa,id_pc)
values(last_insert_id(),3);
*/
commit;


-- =============================

-- insert into equipos_red
INSERT INTO materialestaller(nombre,descripcion,estado,cantidad,id_ubi,id_balda,observaciones)
VALUES ('equipoR1','es un equ_red','operativo',8,'ARM05',3,'en funiconamiento');

insert into equipos_red(id_matTa,num_puertos)
values(last_insert_id(), 2);

INSERT INTO materialestaller(nombre,descripcion,estado,cantidad,id_ubi,id_balda,observaciones)
VALUES ('equipoR2','es un equ_red','obsoleto',8,'EST01',null,'en desuso');

insert into equipos_red(id_matTa,num_puertos)
values(last_insert_id(), 2);




commit;



-- ============================
-- insert into de herramientas
INSERT INTO materialestaller(nombre,descripcion,estado,cantidad,id_ubi,id_balda,observaciones)
VALUES ('herr. soldadura','es una herramienta','obsoleto',2,'ARM01',1,'Quemado el soplete');

insert into herramientas(id_matTa,tipo)
values(last_insert_id(), 'soldadura');

commit;


-- insert into de herramientas generales
INSERT INTO materialestaller(nombre,descripcion,estado,cantidad,id_ubi,id_balda,observaciones)
VALUES ('herr. general','es una herramienta','averiado',1,'ARM04',1,'Oxidado');

insert into herramientas(id_matTa,tipo)
values(last_insert_id(), 'generales');

commit;


-- insert into material fungible
INSERT INTO materialestaller(nombre,descripcion,estado,cantidad,id_ubi,id_balda,observaciones)
VALUES ('material fungible1','es un material','operativo',2,'EST01',null,'ninguno');

insert into material_fungible(id_matTa,estado)
values(last_insert_id(), 'lleno');

commit;

INSERT INTO materialestaller(nombre,descripcion,estado,cantidad,id_ubi,id_balda,observaciones)
VALUES ('material fungible2','es un material','operativo',2,'EST01',null,'ninguno');

insert into material_fungible(id_matTa,estado)
values(last_insert_id(), 'medio');

commit;

INSERT INTO materialestaller(nombre,descripcion,estado,cantidad,id_ubi,id_balda,observaciones)
VALUES ('material fungible3','es un material','operativo',2,'EST01',null,'ninguno');

insert into material_fungible(id_matTa,estado)
values(last_insert_id(), 'vacio');

commit;




-- INSERTs PERIFERICOS

INSERT INTO materialestaller(nombre,descripcion,estado,cantidad,id_ubi,id_balda,observaciones)
VALUES ('periferico1','es un periferico','operativo',2,'EST01',null,'ninguno');

INSERT INTO perifericos(id_matTa,conexion)
VALUES (last_insert_id(),'inalambrica');



-- INSERTs PERIFERICOS // PCs

INSERT INTO perifericos_pcs
VALUES(last_insert_id(),1),
		(last_insert_id(),2);

commit;

INSERT INTO materialestaller(nombre,descripcion,estado,cantidad,id_ubi,id_balda,observaciones)
VALUES ('periferico2','es un periferico','operativo',2,'EST02',null,'ninguno');

INSERT INTO perifericos(id_matTa,conexion)
VALUES (last_insert_id(),'cable');


commit;

 INSERT INTO materialestaller(nombre,descripcion,estado,cantidad,id_ubi,id_balda,observaciones)
VALUES ('componente','es un comp','averiado',9,'ARM02',2,'no funciona');

insert into componentes(id_matTa,id_pc)
values(last_insert_id(),5);

commit;



