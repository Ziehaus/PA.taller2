-- ============================================================
-- SCRIPT DE CREACIÓN DE BASE DE DATOS - SISTEMA MINIPIGS
-- Universidad Distrital Francisco José de Caldas
-- Programación Avanzada
-- Autores: Julian, Miguel, Andres
-- ============================================================

-- 1. Crear y seleccionar la base de datos
CREATE DATABASE IF NOT EXISTS minipigs_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_spanish_ci;

USE minipigs_db;

-- 2. Crear la tabla principal de MiniPigs
CREATE TABLE IF NOT EXISTS minipig (
    codigo          VARCHAR(20)     NOT NULL,
    nombre          VARCHAR(60)     NOT NULL,
    genero          VARCHAR(10)     NOT NULL,
    id_microchip    VARCHAR(30)     NOT NULL,
    raza            VARCHAR(30)     NOT NULL,
    color           VARCHAR(40)     NOT NULL,
    peso            DOUBLE          NOT NULL,
    altura          DOUBLE          NOT NULL,
    caracteristica1 VARCHAR(80)     NOT NULL,
    caracteristica2 VARCHAR(80)     NOT NULL,
    url_foto        VARCHAR(200)    NOT NULL,

    CONSTRAINT pk_minipig       PRIMARY KEY (codigo),
    CONSTRAINT uq_microchip     UNIQUE (id_microchip),
    CONSTRAINT chk_genero       CHECK (genero IN ('MACHO', 'HEMBRA')),
    CONSTRAINT chk_raza         CHECK (raza IN (
        'JULIANA', 'GOTTINGEN', 'VIETNAMITA',
        'KUNEKUNE', 'YUCATAN', 'GUINEA_AMERICANA'
    )),
    CONSTRAINT chk_peso         CHECK (peso > 0),
    CONSTRAINT chk_altura       CHECK (altura > 0)
);

-- ============================================================
-- DATOS DE PRUEBA
-- ============================================================
INSERT INTO minipig VALUES
('001', 'Rosita',   'HEMBRA', '75AF56', 'JULIANA',    'Rosado manchado', 32.0, 43.0, 'esbelto',  'atletico',  'data/fotos/rosita.jpg'),
('002', 'Pepito',   'MACHO',  'AB1234', 'GOTTINGEN',  'Blanco',          38.0, 45.0, 'pequeño',  'tranquilo', 'data/fotos/pepito.jpg'),
('003', 'Manchitas','HEMBRA', 'CD5678', 'VIETNAMITA', 'Negro con manchas',55.0, 50.0, 'robusto',  'sociable',  'data/fotos/manchitas.jpg');

-- ============================================================
-- USUARIO Y CONTRASEÑA (XAMPP por defecto)
-- Usuario  : root
-- Contraseña: (vacía)
-- Host     : localhost
-- Puerto   : 3306
-- Base     : minipigs_db
-- ============================================================
