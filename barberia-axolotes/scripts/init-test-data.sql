-- ============================================================
-- Script de Datos de Prueba para Barbería Axolotes
-- Proyecto Escolar - Skimo Barber
-- ============================================================
-- 
-- Este script crea usuarios de prueba para poder probar la API
-- sin necesidad de desproteger los endpoints.
--
-- CREDENCIALES DE PRUEBA:
-- +--------------+------------------+---------------+
-- | Usuario      | Contraseña       | Rol           |
-- +--------------+------------------+---------------+
-- | admin        | admin123         | ADMINISTRADOR |
-- | barbero1     | barbero123       | EMPLEADO      |
-- | cliente1     | cliente123       | CLIENTE       |
-- +--------------+------------------+---------------+
--
-- NOTA: Las contraseñas están hasheadas con BCrypt
-- ============================================================

-- Limpiar datos existentes (opcional, descomentar si es necesario)
-- DELETE FROM cliente WHERE persona_id IN (SELECT id FROM persona WHERE email LIKE '%@test.skimobarber.com');
-- DELETE FROM usuario WHERE login IN ('admin', 'barbero1', 'cliente1');
-- DELETE FROM persona WHERE email LIKE '%@test.skimobarber.com';
-- DELETE FROM genero WHERE nombre IN ('Masculino', 'Femenino', 'No binario');

-- ============================================================
-- 1. INSERTAR GÉNEROS
-- ============================================================
INSERT INTO genero (nombre, activo) VALUES 
    ('Masculino', true),
    ('Femenino', true),
    ('No binario', true)
ON CONFLICT DO NOTHING;

-- ============================================================
-- 2. INSERTAR PERSONAS
-- ============================================================

-- Administrador
INSERT INTO persona (genero_id, nombre, primer_apellido, segundo_apellido, fecha_nacimiento, telefono, email)
SELECT 
    (SELECT id FROM genero WHERE nombre = 'Masculino' LIMIT 1),
    'Admin',
    'Sistema',
    'Principal',
    '1990-01-15',
    '5551234567',
    'admin@test.skimobarber.com'
WHERE NOT EXISTS (SELECT 1 FROM persona WHERE email = 'admin@test.skimobarber.com');

-- Empleado/Barbero
INSERT INTO persona (genero_id, nombre, primer_apellido, segundo_apellido, fecha_nacimiento, telefono, email)
SELECT 
    (SELECT id FROM genero WHERE nombre = 'Masculino' LIMIT 1),
    'Carlos',
    'Barbero',
    'Experto',
    '1985-06-20',
    '5559876543',
    'barbero1@test.skimobarber.com'
WHERE NOT EXISTS (SELECT 1 FROM persona WHERE email = 'barbero1@test.skimobarber.com');

-- Cliente
INSERT INTO persona (genero_id, nombre, primer_apellido, segundo_apellido, fecha_nacimiento, telefono, email)
SELECT 
    (SELECT id FROM genero WHERE nombre = 'Femenino' LIMIT 1),
    'María',
    'Cliente',
    'Frecuente',
    '1995-03-10',
    '5551112233',
    'cliente1@test.skimobarber.com'
WHERE NOT EXISTS (SELECT 1 FROM persona WHERE email = 'cliente1@test.skimobarber.com');

-- ============================================================
-- 3. INSERTAR USUARIOS
-- ============================================================
-- Contraseñas hasheadas con BCrypt:
-- admin123    -> $2a$10$N9qo8uLOickgx2ZMRZoMy.MqrqLyPJRDKYB3xPVT6kqJGMX9CkE0e
-- barbero123  -> $2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG
-- cliente123  -> $2a$10$eImiTXuWVxfM37uY4JANjQ.k/A8wqQy6.HLm0f3y0vPSp.VKmM6Ue

-- Usuario Administrador
INSERT INTO usuario (persona_id, login, password, rol, activo)
SELECT 
    (SELECT id FROM persona WHERE email = 'admin@test.skimobarber.com'),
    'admin',
    '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrqLyPJRDKYB3xPVT6kqJGMX9CkE0e',
    'administrador',
    true
WHERE NOT EXISTS (SELECT 1 FROM usuario WHERE login = 'admin')
  AND EXISTS (SELECT 1 FROM persona WHERE email = 'admin@test.skimobarber.com');

-- Usuario Empleado/Barbero
INSERT INTO usuario (persona_id, login, password, rol, activo)
SELECT 
    (SELECT id FROM persona WHERE email = 'barbero1@test.skimobarber.com'),
    'barbero1',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    'empleado',
    true
WHERE NOT EXISTS (SELECT 1 FROM usuario WHERE login = 'barbero1')
  AND EXISTS (SELECT 1 FROM persona WHERE email = 'barbero1@test.skimobarber.com');

-- Usuario Cliente
INSERT INTO usuario (persona_id, login, password, rol, activo)
SELECT 
    (SELECT id FROM persona WHERE email = 'cliente1@test.skimobarber.com'),
    'cliente1',
    '$2a$10$eImiTXuWVxfM37uY4JANjQ.k/A8wqQy6.HLm0f3y0vPSp.VKmM6Ue',
    'cliente',
    true
WHERE NOT EXISTS (SELECT 1 FROM usuario WHERE login = 'cliente1')
  AND EXISTS (SELECT 1 FROM persona WHERE email = 'cliente1@test.skimobarber.com');

-- ============================================================
-- 4. INSERTAR CLIENTES (solo para usuarios con rol cliente)
-- ============================================================
INSERT INTO cliente (persona_id, puntos_fidelidad, notas_alergias)
SELECT 
    (SELECT id FROM persona WHERE email = 'cliente1@test.skimobarber.com'),
    100,
    'Sin alergias conocidas'
WHERE NOT EXISTS (
    SELECT 1 FROM cliente 
    WHERE persona_id = (SELECT id FROM persona WHERE email = 'cliente1@test.skimobarber.com')
)
AND EXISTS (SELECT 1 FROM persona WHERE email = 'cliente1@test.skimobarber.com');

-- ============================================================
-- 5. VERIFICAR DATOS INSERTADOS
-- ============================================================
-- Ejecutar estas consultas para verificar:
-- SELECT * FROM genero;
-- SELECT p.*, u.login, u.rol FROM persona p JOIN usuario u ON p.id = u.persona_id;
-- SELECT * FROM cliente;

-- ============================================================
-- FIN DEL SCRIPT
-- ============================================================
