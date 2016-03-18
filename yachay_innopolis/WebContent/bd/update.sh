#/opt/PostgreSQL/9.2/bin
export PGPASSWORD=root
echo "inicio de proceso de la tarea"
psql -d bd_innopolis -U postgres -f sql.txt
echo "proceso finalizado"
 
