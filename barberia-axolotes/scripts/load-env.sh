#!/bin/bash
# ============================================================
# Script para cargar variables de entorno desde .env
# Barbería Axolotes - Proyecto Escolar
# ============================================================
# Uso:
#   source scripts/load-env.sh
#   o
#   . scripts/load-env.sh
# ============================================================

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"
ENV_FILE="$PROJECT_ROOT/.env"

# Colores
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

if [ -f "$ENV_FILE" ]; then
    echo -e "${YELLOW}Cargando variables de entorno desde .env...${NC}"
    
    # Exportar variables del archivo .env
    set -a
    source "$ENV_FILE"
    set +a
    
    echo -e "${GREEN}✓ Variables de entorno cargadas exitosamente${NC}"
    echo ""
    echo "Variables configuradas:"
    echo "  DB_USERNAME:         ${DB_USERNAME:-no definida}"
    echo "  DB_PASSWORD:         ********"
    echo "  DB_IDENTITY_URL:     ${DB_IDENTITY_URL:-no definida}"
    echo "  DB_ORGANIZATION_URL: ${DB_ORGANIZATION_URL:-no definida}"
    echo "  DB_CATALOG_URL:      ${DB_CATALOG_URL:-no definida}"
    echo "  DB_BOOKING_URL:      ${DB_BOOKING_URL:-no definida}"
    echo "  EUREKA_URL:          ${EUREKA_URL:-no definida}"
    echo ""
    echo -e "${GREEN}Ahora puedes ejecutar los microservicios:${NC}"
    echo "  ./mvnw spring-boot:run -pl eureka-server"
    echo "  ./mvnw spring-boot:run -pl identity-service"
    echo "  ./mvnw spring-boot:run -pl organization-service"
    echo "  ./mvnw spring-boot:run -pl catalog-service"
    echo "  ./mvnw spring-boot:run -pl booking-service"
    echo "  ./mvnw spring-boot:run -pl gateway-server"
else
    echo -e "${RED}Archivo .env no encontrado en: $ENV_FILE${NC}"
    echo -e "${YELLOW}Copia .env.example como .env y configura tus valores:${NC}"
    echo "  cp $PROJECT_ROOT/.env.example $ENV_FILE"
    return 1 2>/dev/null || exit 1
fi
