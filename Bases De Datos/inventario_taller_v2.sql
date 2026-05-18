/* BASE DE DATOS Invertario del taller de informática del IES Miguel Herrero */
DROP DATABASE if exists inventario_taller;
CREATE DATABASE if not exists inventario_taller;
USE inventario_taller;


/* CREACIÓN DE TABLAS */

-- TABLAS DE UBICACION

DROP TABLE if exists ubicacion;
CREATE TABLE if not exists ubicacion(
id_ubi VARCHAR(25) not null,
nombre VARCHAR(30) not null,
descripcion varchar(100) null,

PRIMARY KEY (id_ubi)

);

DROP TABLE if exists armario;
CREATE TABLE if not exists armario(
id_ubi VARCHAR(25) not null,
movilidad enum('movil','fija') not null DEFAULT 'fija', -- !


PRIMARY KEY (id_ubi),
FOREIGN KEY (id_ubi) REFERENCES ubicacion(id_ubi),

CHECK(id_ubi LIKE('ARM%'))
);


DROP TABLE if exists balda;
CREATE TABLE if not exists balda(
id_armario VARCHAR(25) not null,
id_balda int,

PRIMARY KEY (id_armario, id_balda),
FOREIGN KEY (id_armario) REFERENCES armario(id_ubi)
);



DROP TABLE if exists estacion;
CREATE TABLE if not exists estacion (
id_ubi VARCHAR(25) not null,
tipo enum('operativo', 'reparacion','operativo') not null DEFAULT 'operativo',

PRIMARY KEY (id_ubi),
FOREIGN KEY (id_ubi) REFERENCES ubicacion(id_ubi),

CHECK(id_ubi LIKE('EST%'))
);


-- ==========================================

DROP TABLE if exists materialesTaller;
CREATE TABLE if not exists materialesTaller(
id_matTa int not null auto_increment,
nombre varchar(50) not null,
descripcion varchar(150) not null,
estado enum('operativo','averiado','reparacion', 'obsoleto') not null DEFAULT 'operativo',
cantidad int not null,
-- categoria enum('cableado','equipo de red','herramientas','perifericos','componentes','consumibles') not null,

id_ubi VARCHAR(25) not null,

-- id_armario VARCHAR(25) null,
id_balda int  null,

fecha_alta DATE DEFAULT (current_date),
observaciones varchar(150),

PRIMARY KEY (id_matTa),
FOREIGN KEY (id_ubi) REFERENCES ubicacion(id_ubi),
-- id_ubi viene de ubicacion

-- FOREIGN KEY (id_armario) REFERENCES armario(id_ubi),
FOREIGN KEY (id_ubi,id_balda) REFERENCES balda(id_armario,id_balda),
/**
el id_balda viene de balda,
pero comprueba si el id_ubi coincide con el id_armario de la tabla balda
*/


CHECK (cantidad >= 0)
);
-- ==============================================
/*
CREATE TRIGGER est_nbalda BEFORE INSERT ON materialesTaller
FOR EACH ROW
BEGIN
	IF NEW.id_ubi LIKE('est%') THEN
		SET NEW.id_balda IS NULL;
	END IF;
END;
*/


-- ==============================================

DROP TABLE if exists pcs;
CREATE TABLE if not exists pcs (
id_pc int not null auto_increment,
nombre varchar(50) not null,
descripcion varchar(150) not null,
estado enum('operativo','averiado','reparacion', 'obsoleto') not null DEFAULT 'operativo',

categoria enum('portatil', 'sobremesa') not null,

id_estacion VARCHAR(25) not null,

fecha_alta DATE DEFAULT (current_date),
observaciones varchar(150),

PRIMARY KEY (id_pc),
FOREIGN KEY (id_estacion) REFERENCES estacion(id_ubi) ON DELETE CASCADE

);

-- =====================================


DROP TABLE if exists perifericos;
CREATE TABLE if not exists perifericos(
id_matTa INT,
conexion enum('inalambrica','cable') not null,-- !

PRIMARY KEY (id_matTa),

FOREIGN KEY (id_matTa) REFERENCES materialesTaller(id_matTa) ON DELETE CASCADE
);




DROP TABLE if exists perifericos_pcs;
CREATE TABLE if not exists perifericos_pcs(
id_periferico INT,
id_pc int,

PRIMARY KEY (id_periferico,id_pc),

FOREIGN KEY (id_periferico) REFERENCES perifericos(id_matTa) ON DELETE CASCADE,
FOREIGN KEY (id_pc) REFERENCES pcs(id_pc) ON DELETE CASCADE
);


-- ================================
-- TABLAS MATERIALES


DROP TABLE if exists componentes;
CREATE TABLE if not exists componentes(
id_matTa INT,
id_pc int NULL,

PRIMARY KEY (id_matTa),

FOREIGN KEY (id_matTa) REFERENCES materialesTaller(id_matTa) ON DELETE CASCADE,
FOREIGN KEY (id_pc) REFERENCES pcs(id_pc) ON DELETE CASCADE

);




DROP TABLE if exists equipos_red;
CREATE TABLE if not exists equipos_red(
id_matTa INT,
num_puertos int null,

PRIMARY KEY (id_matTa),
FOREIGN KEY (id_matTa) REFERENCES materialesTaller(id_matTa) ON DELETE CASCADE,

check (num_puertos>=0)
);




DROP TABLE if exists cableado;
CREATE TABLE if not exists cableado(
id_matTa INT,
longitud int not null,
conector1 varchar(50) not null,
conector2 varchar(50) not null,

PRIMARY KEY (id_matTa),
FOREIGN KEY (id_matTa) REFERENCES materialesTaller(id_matTa) ON DELETE CASCADE
);




DROP TABLE if exists herramientas;
CREATE TABLE if not exists herramientas(
id_matTa INT,
tipo enum('soldadura','generales') not null, -- !

PRIMARY KEY (id_matTa),
FOREIGN KEY (id_matTa) REFERENCES materialesTaller(id_matTa) ON DELETE CASCADE
);


DROP TABLE if exists material_fungible;
CREATE TABLE if not exists material_fungible(
id_matTa INT,
estado enum('lleno','vacio','medio') not null, -- !

PRIMARY KEY (id_matTa),
FOREIGN KEY (id_matTa) REFERENCES materialesTaller(id_matTa) ON DELETE CASCADE
);

