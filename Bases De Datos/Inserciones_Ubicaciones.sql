
USE inventario_taller;

INSERT INTO ubicacion(id_ubi)
VALUES
	('arm01'),
    ('arm02'),
    ('arm03'),
    ('arm04'),
    ('arm05'),
    ('est01'),
    ('est02'),
    ('est03'),
    ('est04'),
    ('est05'),
    ('est06'),
    ('est07'),
    ('est08');
    
    
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

