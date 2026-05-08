
USE inventario_taller;

INSERT INTO ubicacion(id_ubi,nombre,descripcion)
VALUES
	('arm01','armario01','Almacena cables'),
    ('arm02','armario02','Almacena tornillos'),
    ('arm03','armario03','Almacena cables'),
    ('arm04','armario04','Almacena '),
    ('arm05','armario05','Almacena'),
    ('est01','estacion01','Se encuentran'),
    ('est02','estacion02','Se encuentran'),
    ('est03','estacion03','Se encuentran'),
    ('est04','estacion04','Se encuentran'),
    ('est05','estacion05','Se encuentran'),
    ('est06','estacion06','Se encuentran'),
    ('est07','estacion07','Se encuentran'),
    ('est08','estacion08','Se encuentran');
    
    
INSERT INTO armario(id_ubi)
VALUES 
	('arm01'),
    ('arm02'),
    ('arm03'),
    ('arm04'),
    ('arm05');

INSERT INTO estacion(id_ubi)
VALUES 
	('est01'),
    ('est03'),
    ('est04'),
    ('est06'),
    ('est07'),
    ('est08');
    
INSERT INTO estacion (id_ubi,tipo)
VALUES
    ('est02','reparacion'),
	('est05','reparacion');
 
INSERT INTO balda(id_balda,id_armario)
VALUES
	(1,'arm01'),(2,'arm01'),(3,'arm01'),(4,'arm01'),(5,'arm01'),(6,'arm01'),
    (1,'arm02'),(2,'arm02'),(3,'arm02'),(4,'arm02'),(5,'arm02'),(6,'arm02'),
    (1,'arm03'),(2,'arm03'),(3,'arm03'),(4,'arm03'),(5,'arm03'),(6,'arm03'),
    (1,'arm04'),(2,'arm04'),(3,'arm04'),(4,'arm04'),(5,'arm04'),(6,'arm04'),
    (1,'arm05'),(2,'arm05'),(3,'arm05'),(4,'arm05'),(5,'arm05'),(6,'arm05');

