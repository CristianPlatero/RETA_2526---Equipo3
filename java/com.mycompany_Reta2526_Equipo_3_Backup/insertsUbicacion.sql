-- ============================================================
-- INSERTS DE UBICACIONES 
-- Paso previo necesario para la inserción de materiales en la base
-- ============================================================

-- ============================================================
-- 1. UBICACION (tabla padre, debe ir primero)
-- ============================================================
INSERT INTO ubicacion (id_ubi, nombre, descripcion) VALUES
('ARM01', 'Armario 1', NULL),
('ARM02', 'Armario 2', NULL),
('ARM03', 'Armario 3', NULL),
('ARM04', 'Armario 4', NULL),
('ARM05', 'Armario 5', NULL),
('ARM06', 'Armario 6', NULL),
('EST01', 'Estación 1', NULL),
('EST02', 'Estación 2', NULL),
('EST03', 'Estación 3', NULL),
('EST04', 'Estación 4', NULL),
('EST05', 'Estación 5', NULL),
('EST06', 'Estación 6', NULL),
('EST07', 'Estación 7', NULL),
('EST08', 'Estación 8', NULL);

-- ============================================================
-- 2. ARMARIO (referencia ubicacion, solo los ARM)
-- ============================================================
INSERT INTO armario (id_ubi, movilidad) VALUES
('ARM01', 'fija'),
('ARM02', 'fija'),
('ARM03', 'fija'),
('ARM04', 'fija'),
('ARM05', 'fija'),
('ARM06', 'movil');

-- ============================================================
-- 3. ESTACION (referencia ubicacion, solo los EST)
-- ============================================================
INSERT INTO estacion (id_ubi, tipo) VALUES
('EST01', 'operativo'),
('EST02', 'operativo'),
('EST03', 'operativo'),
('EST04', 'operativo'),
('EST05', 'operativo'),
('EST06', 'operativo'),
('EST07', 'reparacion'),
('EST08', 'reparacion');

-- ============================================================
-- 4. BALDA (referencia armario — 6 baldas por armario)
-- ============================================================
INSERT INTO balda (id_armario, id_balda) VALUES
('ARM01', 1), ('ARM01', 2), ('ARM01', 3), ('ARM01', 4), ('ARM01', 5), ('ARM01', 6),
('ARM02', 1), ('ARM02', 2), ('ARM02', 3), ('ARM02', 4), ('ARM02', 5), ('ARM02', 6),
('ARM03', 1), ('ARM03', 2), ('ARM03', 3), ('ARM03', 4), ('ARM03', 5), ('ARM03', 6),
('ARM04', 1), ('ARM04', 2), ('ARM04', 3), ('ARM04', 4), ('ARM04', 5), ('ARM04', 6),
('ARM05', 1), ('ARM05', 2), ('ARM05', 3), ('ARM05', 4), ('ARM05', 5), ('ARM05', 6),
('ARM06', 1), ('ARM06', 2), ('ARM06', 3), ('ARM06', 4), ('ARM06', 5), ('ARM06', 6);