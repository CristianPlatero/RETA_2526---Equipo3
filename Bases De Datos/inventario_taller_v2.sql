/* BASE DE DATOS Invertario del taller de informática del IES Miguel Herrero */
DROP DATABASE if exists inventario_taller;
CREATE DATABASE if not exists inventario_taller;
USE inventario_taller;


/* CREACIÓN DE TABLAS */

-- TABLAS DE UBICACION

DROP TABLE if exists ubicacion;
CREATE TABLE if not exists ubicacion(
id_ubi VARCHAR(25) not null,


PRIMARY KEY (id_ubi)

);

DROP TABLE if exists armario;
CREATE TABLE if not exists armario(
id_ubi VARCHAR(25) not null,
num_baldas int not null DEFAULT 6,

PRIMARY KEY (id_ubi),
FOREIGN KEY (id_ubi) REFERENCES ubicacion(id_ubi)
);


DROP TABLE if exists balda;
CREATE TABLE if not exists balda(
id_balda int,
id_armario VARCHAR(25) not null,

PRIMARY KEY (id_balda, id_armario),
FOREIGN KEY (id_armario) REFERENCES armario(id_ubi)
);



DROP TABLE if exists estacion;
CREATE TABLE if not exists estacion (
id_ubi VARCHAR(25) not null,
tipo enum('operativo', 'reparacion') not null DEFAULT 'operativo',

PRIMARY KEY (id_ubi),
FOREIGN KEY (id_ubi) REFERENCES ubicacion(id_ubi)
);


-- ==========================================

DROP TABLE if exists materialesTaller;
CREATE TABLE if not exists materialesTaller(
id_matTa int not null auto_increment,
nombre varchar(50) not null,
descripcion varchar(150) not null,
estado enum('operativo','averiado','en reparacion', 'obsoleto') not null DEFAULT 'operativo',
cantidad int not null,

id_ubi VARCHAR(25) not null,



fecha_alta DATE DEFAULT (current_date),
observaciones varchar(150),

PRIMARY KEY (id_matTa),
FOREIGN KEY (id_ubi) REFERENCES ubicacion(id_ubi),


CHECK (cantidad >= 0)
);

-- ==============================================

DROP TABLE if exists pcs;
CREATE TABLE if not exists pcs (
id_pc int not null auto_increment,
nombre varchar(50) not null,
descripcion varchar(150) not null,
estado enum('operativo','averiado','en reparacion', 'obsoleto') not null DEFAULT 'operativo',
cantidad int not null,
categoria enum('portatil', 'sobremesa') not null,

id_estacion VARCHAR(25) not null,

PRIMARY KEY (id_pc),
FOREIGN KEY (id_estacion) REFERENCES estacion(id_ubi) ON DELETE CASCADE

);

-- =====================================

-- TABLAS MATERIALES


DROP TABLE if exists componentes;
CREATE TABLE if not exists componentes(
id_matTa INT,
id_pc int NULL,

PRIMARY KEY (id_matTa),

FOREIGN KEY (id_matTa) REFERENCES materialesTaller(id_matTa) ON DELETE CASCADE,
FOREIGN KEY (id_pc) REFERENCES pcs(id_pc) ON DELETE CASCADE

);


DROP TABLE if exists perifericos;
CREATE TABLE if not exists perifericos(
id_matTa INT,
id_pc int null,

PRIMARY KEY (id_matTa),

FOREIGN KEY (id_matTa) REFERENCES materialesTaller(id_matTa) ON DELETE CASCADE,
FOREIGN KEY (id_pc) REFERENCES pcs(id_pc) ON DELETE CASCADE
);

DROP TABLE if exists equipos_red;
CREATE TABLE if not exists equipos_red(
id_matTa INT,

PRIMARY KEY (id_matTa),
FOREIGN KEY (id_matTa) REFERENCES materialesTaller(id_matTa) ON DELETE CASCADE
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
tipo enum('soldadura','generales') not null,

PRIMARY KEY (id_matTa),
FOREIGN KEY (id_matTa) REFERENCES materialesTaller(id_matTa) ON DELETE CASCADE
);


DROP TABLE if exists material_fungible;
CREATE TABLE if not exists material_fungible(
id_matTa INT,

PRIMARY KEY (id_matTa),
FOREIGN KEY (id_matTa) REFERENCES materialesTaller(id_matTa) ON DELETE CASCADE
);

