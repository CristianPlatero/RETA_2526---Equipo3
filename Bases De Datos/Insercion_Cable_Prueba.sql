
BEGIN;


INSERT INTO materialestaller(nombre,descripcion,estado,cantidad,id_ubi,id_balda,observaciones)
VALUES ('cable','es un cable','operativo',10,'arm01',1,'funciona');

INSERT INTO cableado(id_matTa,longitud,conector1,conector2)
VALUES (last_insert_id(),10,'C1','C2');

COMMIT;
