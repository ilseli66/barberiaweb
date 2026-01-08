#!/bin/bash
# ============================================================
# Script para iniciar todos los microservicios
# Barbería Axolotes - Proyecto Escolar
# ============================================================

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Directorio del proyecto
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"

echo -e "${BLUE}============================================================${NC}"
echo -e "${BLUE}       Barbería Axolotes - Inicio de Microservicios${NC}"
echo -e "${BLUE}============================================================${NC}"
echo ""

# Cargar variables de entorno desde .env
ENV_FILE="$PROJECT_DIR/.env"
if [ -f "$ENV_FILE" ]; then
    echo -e "${GREEN}✓ Cargando variables de entorno desde .env${NC}"
    set -a
    source "$ENV_FILE"
    set +a
    echo -e "  DB_USERNAME: $DB_USERNAME"
    echo -e "  DB_IDENTITY_URL: $DB_IDENTITY_URL"
    echo ""
else
    echo -e "${YELLOW}⚠ Archivo .env no encontrado, usando valores por defecto${NC}"
    echo -e "${YELLOW}  Copia .env.example a .env para configurar credenciales${NC}"
    echo ""
fi

cd "$PROJECT_DIR"

# Función para iniciar un servicio
start_service() {
    local service_name=$1
    local port=$2
    echo -e "${BLUE}→ Iniciando $service_name (puerto $port)...${NC}"
    ./mvnw spring-boot:run -pl "$service_name" &
    sleep 3
}

# Verificar que PostgreSQL está corriendo
echo -e "${YELLOW}Verificando PostgreSQL...${NC}"
if ! pg_isready -h localhost -p 5432 > /dev/null 2>&1; then
    echo -e "${RED}✗ PostgreSQL no está corriendo en localhost:5432${NC}"
    echo -e "${YELLOW}  Ejecuta: podman-compose up -d${NC}"
    exit 1
fi
echo -e "${GREEN}✓ PostgreSQL está corriendo${NC}"
echo ""

# Menú de opciones
echo -e "${YELLOW}¿Qué deseas iniciar?${NC}"
echo "  1) Solo Eureka Server"
echo "  2) Eureka + Identity Service"
echo "  3) Todos los servicios"
echo "  4) Servicio específico"
echo ""
read -p "Opción [1-4]: " option

case $option in
    1)
        echo ""
        start_service "eureka-server" "8761"
        echo -e "${GREEN}✓ Eureka iniciado en http://localhost:8761${NC}"
        ;;
    2)
        echo ""
        start_service "eureka-server" "8761"
        sleep 5
        start_service "identity-service" "8081"
        echo ""
        echo -e "${GREEN}============================================================${NC}"
        echo -e "${GREEN}  Servicios iniciados:${NC}"
        echo -e "${GREEN}    - Eureka Server:    http://localhost:8761${NC}"
        echo -e "${GREEN}    - Identity Service: http://localhost:8081${NC}"
        echo -e "${GREEN}    - Swagger UI:       http://localhost:8081/swagger-ui.html${NC}"
        echo -e "${GREEN}============================================================${NC}"
        ;;
    3)
        echo ""
        echo -e "${YELLOW}Iniciando todos los servicios (esto puede tomar unos minutos)...${NC}"
        echo ""
        
        start_service "eureka-server" "8761"
        sleep 10  # Esperar a que Eureka esté listo
        
        start_service "identity-service" "8081"
        sleep 5
        start_service "organization-service" "8082"
        sleep 5
        start_service "catalog-service" "8083"
        sleep 5
        start_service "booking-service" "8084"
        sleep 5
        start_service "gateway-server" "8080"
        
        echo ""
        echo -e "${GREEN}============================================================${NC}"
        echo -e "${GREEN}  Todos los servicios iniciados:${NC}"
        echo -e "${GREEN}    - Eureka Server:        http://localhost:8761${NC}"
        echo -e "${GREEN}    - Gateway:              http://localhost:8080${NC}"
        echo -e "${GREEN}    - Identity Service:     http://localhost:8081${NC}"
        echo -e "${GREEN}    - Organization Service: http://localhost:8082${NC}"
        echo -e "${GREEN}    - Catalog Service:      http://localhost:8083${NC}"
        echo -e "${GREEN}    - Booking Service:      http://localhost:8084${NC}"
        echo -e "${GREEN}============================================================${NC}"
        echo ""
        echo -e "${YELLOW}Para detener todos: pkill -f 'spring-boot:run'${NC}"
        ;;
    4)
        echo ""
        echo -e "${YELLOW}Servicios disponibles:${NC}"
        echo "  1) eureka-server (8761)"
        echo "  2) identity-service (8081)"
        echo "  3) organization-service (8082)"
        echo "  4) catalog-service (8083)"
        echo "  5) booking-service (8084)"
        echo "  6) gateway-server (8080)"
        echo ""
        read -p "Selecciona servicio [1-6]: " svc
        
        case $svc in
            1) start_service "eureka-server" "8761" ;;
            2) start_service "identity-service" "8081" ;;
            3) start_service "organization-service" "8082" ;;
            4) start_service "catalog-service" "8083" ;;
            5) start_service "booking-service" "8084" ;;
            6) start_service "gateway-server" "8080" ;;
            *) echo -e "${RED}Opción inválida${NC}" ;;
        esac
        ;;
    *)
        echo -e "${RED}Opción inválida${NC}"
        exit 1
        ;;
esac

echo ""
echo -e "${BLUE}Presiona Ctrl+C para detener los servicios${NC}"
wait
