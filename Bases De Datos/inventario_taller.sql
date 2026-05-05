/* BASE DE DATOS Invertario del taller de informática del IES Miguel Herrero */
DROP DATABASE if exists inventario_taller;
CREATE DATABASE if not exists inventario_taller;
USE inventario_taller;


/* CREACIÓN DE TABLAS */

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
FOREIGN KEY (id_ubi) REFERENCES ubicacion(id_ubi),

CHECK (num_balda > 0)
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


DROP TABLE if exists pcs;
CREATE TABLE if not exists pcs (
id_pc int not null auto_increment,
nombre varchar(50) not null,
descripcion varchar(150) not null,
categoria enum('portatil', 'sobremesa') not null,
estado enum('operativo','averiado','en reparación', 'obsoleto') not null DEFAULT 'operativo',
cantidad int not null,
id_ubi int not null ,
fecha_alta DATETIME DEFAULT (current_date),
observaciones varchar(150) null,

PRIMARY KEY (id_pc),
FOREIGN KEY (id_ubi) REFERENCES ubicacion(id_ubi),
CHECK (cantidad >= 0)
);

DROP TABLE if exists componentes;
CREATE TABLE if not exists componentes(
id_comp int not null auto_increment,
nombre varchar(50) not null,
descripcion varchar(150) not null,
estado enum('operativo','averiado','en reparación', 'obsoleto') not null DEFAULT 'operativo',
cantidad int not null,
id_ubi int not null,
id_pc int,
fecha_alta DATETIME DEFAULT (current_date),
observaciones varchar(150),

PRIMARY KEY (id_comp),
FOREIGN KEY (id_ubi) REFERENCES ubicacion(id_ubi),
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
id_ubi int not null,
id_pc int not null,
fecha_alta DATETIME DEFAULT (current_date),
observaciones varchar(150),

PRIMARY KEY (id_peri),
FOREIGN KEY (id_ubi) REFERENCES ubicacion(id_ubi),
FOREIGN KEY (id_pc) REFERENCES pcs(id_pc),

CHECK (cantidad >= 0)

);






