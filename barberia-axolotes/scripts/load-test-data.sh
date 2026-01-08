#!/bin/bash
# ============================================================
# Script para cargar datos de prueba en la base de datos
# Barbería Axolotes - Proyecto Escolar
# ============================================================

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Configuración de la base de datos (ajustar según tu entorno)
DB_HOST="${DB_HOST:-localhost}"
DB_PORT="${DB_PORT:-5432}"
DB_NAME="${DB_NAME:-barberia_db}"
DB_USER="${DB_USER:-myuser}"
DB_PASSWORD="${DB_PASSWORD:-secret}"

# Directorio del script
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SQL_FILE="$SCRIPT_DIR/init-test-data.sql"

echo -e "${YELLOW}============================================================${NC}"
echo -e "${YELLOW}       Barbería Axolotes - Carga de Datos de Prueba${NC}"
echo -e "${YELLOW}============================================================${NC}"
echo ""

# Verificar que el archivo SQL existe
if [ ! -f "$SQL_FILE" ]; then
    echo -e "${RED}Error: No se encontró el archivo $SQL_FILE${NC}"
    exit 1
fi

echo -e "Configuración:"
echo -e "  Host:     ${GREEN}$DB_HOST${NC}"
echo -e "  Puerto:   ${GREEN}$DB_PORT${NC}"
echo -e "  Base:     ${GREEN}$DB_NAME${NC}"
echo -e "  Usuario:  ${GREEN}$DB_USER${NC}"
echo ""

# Ejecutar el script SQL
echo -e "${YELLOW}Ejecutando script SQL...${NC}"
PGPASSWORD="$DB_PASSWORD" psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d "$DB_NAME" -f "$SQL_FILE"

if [ $? -eq 0 ]; then
    echo ""
    echo -e "${GREEN}============================================================${NC}"
    echo -e "${GREEN}       ¡Datos de prueba cargados exitosamente!${NC}"
    echo -e "${GREEN}============================================================${NC}"
    echo ""
    echo -e "Usuarios disponibles para login:"
    echo -e "┌──────────────┬──────────────────┬───────────────┐"
    echo -e "│ Usuario      │ Contraseña       │ Rol           │"
    echo -e "├──────────────┼──────────────────┼───────────────┤"
    echo -e "│ ${GREEN}admin${NC}        │ ${YELLOW}admin123${NC}         │ ADMINISTRADOR │"
    echo -e "│ ${GREEN}barbero1${NC}     │ ${YELLOW}barbero123${NC}       │ EMPLEADO      │"
    echo -e "│ ${GREEN}cliente1${NC}     │ ${YELLOW}cliente123${NC}       │ CLIENTE       │"
    echo -e "└──────────────┴──────────────────┴───────────────┘"
    echo ""
    echo -e "Ejemplo de login con curl:"
    echo -e "${YELLOW}curl -X POST http://localhost:8081/api/auth/login \\${NC}"
    echo -e "${YELLOW}  -H 'Content-Type: application/json' \\${NC}"
    echo -e "${YELLOW}  -d '{\"login\":\"admin\",\"password\":\"admin123\"}'${NC}"
    echo ""
else
    echo -e "${RED}Error al ejecutar el script SQL${NC}"
    exit 1
fi
