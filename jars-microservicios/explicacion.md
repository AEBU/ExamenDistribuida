# Como iniciar las maquinas virtuales

Para este paso deberemo cambiar todos los "localhost" que tenemos dentro de nuestros contenedores por los nombres que necesitamos para poder realizarlo.



-   Iniciamos con la base de datos
docker pull postgres

docker create -v /var/lib/postgresql/data --name postgresdocker postgres:9.4


docker run   --volumes-from postgresdocker \
 --name postgresdocker \
 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=junior200 \
 -d -P postgres:9.4 \


### Para Rabbit
- Hac3mos que se use una red, para que se vean dentro las maquinas dentro, ya que si les ponemos ip sería mas tedios unirlas con --link


docker run -d --rm --hostname rabbit --network networkdistribuida.uce --name rabbit -p 15672:15672 rabbitmq:3-management


## Correr los microservicios
- Construimos las imagenes de lamisma manera 
    ConfigServer            :8888           configserver
    Servicios   
        -   m1-singer-alb    :8080          m1singalb
        -   m2-artist       :8081           m2artist
    Eureka                  :8761           eureka
    Gateway[Zuul]           :9090           gateway
    Cliente                 :8910           client

docker build -t app/config-server .

para correrlo 
docker run -d --network networkdistribuida.uce --name configserver app/config-server


docker build -t app/m1singalb .
docker run -d --network networkdistribuida.uce --name m1singalb app/m1singalb


