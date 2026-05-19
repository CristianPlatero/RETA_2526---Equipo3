/* BASE DE DATOS Invertario del taller de informática del IES Miguel Herrero */
DROP DATABASE if exists inventario_taller;
CREATE DATABASE if not exists inventario_taller;
USE inventario_taller;


/* CREACIÓN DE TABLAS */

-- TABLAS DE UBICACION

DROP TABLE if exists ubicacion;
CREATE TABLE if not exists ubicacion(
id_ubi int not null auto_increment,
nombre varchar(50) not null,

PRIMARY KEY (id_ubi)

);

DROP TABLE if exists armario;
CREATE TABLE if not exists armario(
id_armario int not null auto_increment,
id_ubi int not null,
nombre varchar(50) not null,

PRIMARY KEY (id_armario),
FOREIGN KEY (id_ubi) REFERENCES ubicacion(id_ubi)
);


DROP TABLE if exists balda;
CREATE TABLE if not exists balda(
id_balda int not null auto_increment,
id_ubi int not null,
nombre varchar(50) not null,

PRIMARY KEY (id_balda),
FOREIGN KEY (id_ubi) REFERENCES ubicacion(id_ubi)
);



DROP TABLE if exists estacion;
CREATE TABLE if not exists estacion (

id_estacion int not null auto_increment,
id_ubi int not null,
nombre varchar(50) not null,
tipo enum('operativo', 'reparación') not null,

PRIMARY KEY (id_estacion),
FOREIGN KEY (id_ubi) REFERENCES ubicacion(id_ubi)
);


-- ==========================================


DROP TABLE if exists pcs;
CREATE TABLE if not exists pcs (
id_pc int not null auto_increment,
nombre varchar(50) not null,
descripcion varchar(150) not null,
categoria enum('portatil', 'sobremesa') not null,
estado enum('operativo','averiado','en reparación', 'obsoleto') not null DEFAULT 'operativo',
cantidad int not null,
id_estacion int null,
id_armario int null,
id_balda int null,
fecha_alta DATETIME not null DEFAULT (CURRENT_DATE),
observaciones varchar(150) null,

PRIMARY KEY (id_pc),
FOREIGN KEY (id_estacion) REFERENCES estacion(id_estacion),
FOREIGN KEY (id_armario) REFERENCES armario(id_armario),
FOREIGN KEY (id_balda) REFERENCES balda(id_balda),

CHECK (cantidad >= 0)

);

-- =====================================

-- TABLAS MATERIALES





DROP TABLE if exists componentes;
CREATE TABLE if not exists componentes(
id_comp int not null auto_increment,
nombre varchar(50) not null,
descripcion varchar(150) not null,
estado enum('operativo','averiado','en reparación', 'obsoleto') not null DEFAULT 'operativo',
cantidad int not null,

id_armario int,
id_estacion int null,
id_balda int null,
id_pc int,

fecha_alta DATE DEFAULT (current_date),
observaciones varchar(150),

PRIMARY KEY (id_comp),
FOREIGN KEY (id_armario) REFERENCES armario(id_armario),
FOREIGN KEY (id_estacion) REFERENCES estacion(id_estacion),
FOREIGN KEY (id_balda) REFERENCES balda(id_balda),

FOREIGN KEY (id_pc) REFERENCES pcs(id_pc),

CHECK (cantidad >= 0)

);


DROP TABLE if exists perifericos;
CREATE TABLE if not exists perifericos(
id_peri int not null auto_increment,
nombre varchar(50) not null,
descripcion varchar(150) not null,
estado enum('operativo','averiado','en reparación', 'obsoleto') not null DEFAULT 'operativo',
cantidad int not null,

id_estacion int,
id_armario int null,
id_balda int null,
id_pc int not null,

fecha_alta DATE DEFAULT (current_date),
observaciones varchar(150),

PRIMARY KEY (id_peri),
FOREIGN KEY (id_estacion) REFERENCES estacion(id_estacion),
FOREIGN KEY (id_armario) REFERENCES armario(id_armario),
FOREIGN KEY (id_balda) REFERENCES balda(id_balda),
FOREIGN KEY (id_pc) REFERENCES pcs(id_pc),

CHECK (cantidad >= 0)

);

DROP TABLE if exists equipos_red;
CREATE TABLE if not exists equipos_red(
id_red int not null auto_increment,
nombre varchar(50) not null,
descripcion varchar(150) not null,
estado enum('operativo','averiado','en reparación', 'obsoleto') not null DEFAULT 'operativo',
cantidad int not null,

id_estacion int,
id_armario int,
id_balda int null,

fecha_alta DATE DEFAULT (current_date),
observaciones varchar(150),

PRIMARY KEY (id_red),
FOREIGN KEY (id_estacion) REFERENCES estacion(id_estacion),
FOREIGN KEY (id_armario) REFERENCES armario(id_armario),
FOREIGN KEY (id_balda) REFERENCES balda(id_balda),

CHECK (cantidad > 0)
);




DROP TABLE if exists cableado;
CREATE TABLE if not exists cableado(
id_cable int not null auto_increment,
nombre varchar(50) not null,
descripcion varchar(150) not null,
estado enum('operativo','averiado','en reparación', 'obsoleto') not null DEFAULT 'operativo',
cantidad int not null,

id_pc int,
id_estacion int,
id_armario int,
id_balda int null,

fecha_alta DATE DEFAULT (current_date),
observaciones varchar(150),
longitud int not null,
conector1 varchar(50) not null,
conector2 varchar(50) not null,

PRIMARY KEY (id_cable),
FOREIGN KEY (id_estacion) REFERENCES estacion(id_estacion),
FOREIGN KEY (id_armario) REFERENCES armario(id_armario),
FOREIGN KEY (id_balda) REFERENCES balda(id_balda),
FOREIGN KEY (id_pc) REFERENCES pcs(id_pc),

CHECK (cantidad > 0)
);




DROP TABLE if exists herramientas;
CREATE TABLE if not exists herramientas(
id_her int not null auto_increment,
nombre varchar(50) not null,
descripcion varchar(150) not null,
estado enum('operativo','averiado','en reparación', 'obsoleto') not null DEFAULT 'operativo',
cantidad int not null,

id_estacion int,
id_armario int,
id_balda int null,

fecha_alta DATE DEFAULT (current_date),
observaciones varchar(150),

PRIMARY KEY (id_her),
FOREIGN KEY (id_estacion) REFERENCES estacion(id_estacion),
FOREIGN KEY (id_armario) REFERENCES armario(id_armario),
FOREIGN KEY (id_balda) REFERENCES balda(id_balda),

CHECK (cantidad > 0)
);


DROP TABLE if exists material_fungible;
CREATE TABLE if not exists material_fungible(
id_matF int not null auto_increment,
nombre varchar(50) not null,
descripcion varchar(150) not null,
estado enum('operativo','averiado','en reparación', 'obsoleto') not null DEFAULT 'operativo',
cantidad int not null,

id_estacion int,
id_armario int,
id_balda int null,

fecha_alta DATE DEFAULT (current_date),
observaciones varchar(150),

PRIMARY KEY (id_matF),
FOREIGN KEY (id_estacion) REFERENCES estacion(id_estacion),
FOREIGN KEY (id_armario) REFERENCES armario(id_armario),
FOREIGN KEY (id_balda) REFERENCES balda(id_balda),

CHECK (cantidad > 0)
);







