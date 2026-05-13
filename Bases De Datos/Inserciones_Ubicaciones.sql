
USE inventario_taller;

INSERT INTO ubicacion(id_ubi,nombre,descripcion)
VALUES
	('ARM01','armario01','Almacena cables'),
    ('ARM02','armario02','Almacena tornillos'),
    ('ARM03','armario03','Almacena cables'),
    ('ARM04','armario04','Almacena '),
    ('ARM05','armario05','Almacena'),
    ('EST01','estacion01','Se encuentran'),
    ('EST02','estacion02','Se encuentran'),
    ('EST03','estacion03','Se encuentran'),
    ('EST04','estacion04','Se encuentran'),
    ('EST05','estacion05','Se encuentran'),
    ('EST06','estacion06','Se encuentran'),
    ('EST07','estacion07','Se encuentran'),
    ('EST08','estacion08','Se encuentran');
    
    
INSERT INTO armario(id_ubi,movilidad)
VALUES 
	('ARM01','movil'),
    ('ARM02','movil'),
    ('ARM03','fija'),
    ('ARM04','movil'),
    ('ARM05','fija');

INSERT INTO estacion(id_ubi)
VALUES 
	('EST01'),
    ('EST03'),
    ('EST04'),
    ('EST06'),
    ('EST07'),
    ('EST08');
    
INSERT INTO estacion (id_ubi,tipo)
VALUES
    ('EST02','reparacion'),
	('EST05','reparacion');
 
INSERT INTO balda(id_balda,id_armario)
VALUES
	(1,'ARM01'),(2,'ARM01'),(3,'ARM01'),(4,'ARM01'),(5,'ARM01'),(6,'ARM01'),
    (1,'ARM02'),(2,'ARM02'),(3,'ARM02'),(4,'ARM02'),(5,'ARM02'),(6,'ARM02'),
    (1,'ARM03'),(2,'ARM03'),(3,'ARM03'),(4,'ARM03'),(5,'ARM03'),(6,'ARM03'),
    (1,'ARM04'),(2,'ARM04'),(3,'ARM04'),(4,'ARM04'),(5,'ARM04'),(6,'ARM04'),
    (1,'ARM05'),(2,'ARM05'),(3,'ARM05'),(4,'ARM05'),(5,'ARM05'),(6,'ARM05');

