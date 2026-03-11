-- =====================================================
-- SCRIPT COMPLETO PARA CREAR LA BASE DE DATOS
-- =====================================================

-- 1. Eliminar BD si existe (OPCIONAL - solo si quieres empezar de cero)
DROP DATABASE IF EXISTS minipigs_db;

-- 2. Crear BD
CREATE DATABASE minipigs_db;
USE minipigs_db;

-- 3. Crear tabla
CREATE TABLE minipigs (
    codigo VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    genero ENUM('MACHO', 'HEMBRA') NOT NULL,
    id_microchip VARCHAR(50) UNIQUE NOT NULL,
    raza VARCHAR(50) NOT NULL,
    color VARCHAR(50),
    peso DECIMAL(5,2),
    altura DECIMAL(5,2),
    caracteristica1 VARCHAR(200),
    caracteristica2 VARCHAR(200),
    url_foto VARCHAR(500)
);

-- 4. Verificar que la tabla está vacía
SELECT 'Tabla creada correctamente' AS 'Estado';
SELECT COUNT(*) AS 'Registros actuales' FROM minipigs;

-- 5. Insertar UN registro de prueba MANUALMENTE para verificar
INSERT INTO minipigs (codigo, nombre, genero, id_microchip, raza, color, peso, altura) 
VALUES ('TEST01', 'Prueba', 'MACHO', 'TEST123', 'Juliana', 'Negro', 30, 40);

-- 6. Verificar inserción
SELECT * FROM minipigs;