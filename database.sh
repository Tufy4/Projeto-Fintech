#!/bin/bash

# Script para restaurar o banco de dados Oracle (apenas adicionando schema/dados)

DBFILE=src/main/resources/banco.sql

if [ ! -f "$DBFILE" ]; then
    echo "File not found: $DBFILE"
    exit 1
fi

ORACLE_USER=system
ORACLE_PASS=root
ORACLE_PORT=1539
ORACLE_SVC=FREE   # altere se seu service name for outro

# Rodando o script SQL
echo "Creating database schema..."
sqlplus "$ORACLE_USER/$ORACLE_PASS@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=localhost)(PORT=$ORACLE_PORT))(CONNECT_DATA=(SERVICE_NAME=$ORACLE_SVC)))" <<EOF
@${DBFILE}
EXIT;
EOF

echo "Database schema creation complete."

