# Examen 2do Hemisemestre Programacion Distribuida
```xml
Puertos de nuestros servicios

    ConfigServer            :8888
    Servicios   
        -   m1-artis-alb    :8080
        -   m2-artist       :8081
    Eureka                  :8761
    Gateway[Zuul]           :9090
    Cliente                 :8910

```
Nota:Tomar en cuenta que primero se debe crear Singer, para poder Listarlos[lo mismo con Instrument]. y crear un mail valido.

> Explicacion
-   Tenemos que incorporar las diferentes herramientas de Spring Cloud con una funcionalidad
    -   Spring Cloud
        -   Spring Config Server
        -   Ribbon
        -   Eureka 
        -   Zuul (Gateway)
    -   Rabbit MQ
    -   Services
        -   M1- Album_Singer
        -   M2- Instr
    -   Database
        -   Postgres
-   Luego todo esto en contenedores docker

## Comenzamos con la realizacion de 

>   Configuracion de Spring Config Server:

1.- Agregar las dependencias necesarias
```gradle

ext {
	springCloudVersion = 'Edgware.SR2'
}
...
compile('org.springframework.cloud:spring-cloud-config-server')
```

2.-Habilitamos Config Server
```java
@SpringBootApplication
@EnableConfigServer

```
3.- Configuramos los recursos, con los properties necesarios.
-   application.properties
```
server.port=8888
```
-   bootstrap.properties
```
spring.application.name=config-server
spring.cloud.config.server.git.uri=https://github.com/AEBU/Config-Server.git
management.security.enabled=false
```
4.- como se muestra se va a tomar los datos de un uri, por defecto es archivo local, pero se lo puede vincular a GitHub para loq ue nuestros archivos de propiedades estan [AQUI]
-   Debemos colocar, nuestros archivos de configuracion haciendo referencia con el application.name definido dentro de cada microservicio para que haga referencia a nuestros archivos definidos en github



>   Configuracion de M1(Singer,Album)

Realizamos lo mismo que hemos hecho en m2
>   Configuracion de M2(Instrument)
1.- Dependencias necesarias
```gradle
compile('org.springframework.cloud:spring-cloud-starter-config')
compile('org.springframework.boot:spring-boot-starter-actuator')
runtime('org.postgresql:postgresql')

```
2.- Agregamos los datos en application.properties
```
spring.cloud.config.name=client2-instrum
spring.cloud.config.uri=http://localhost:8888
```
En el cual podemos ver que el config colocamos la ip de donde esta corriendo el config server y su respectivo puerto no configuramos nada mas dentro de este archivo, ya que todo toma del archivo client2-instrum.properties del configserver

3.- Comenzamos con la logica.

Damos la logica de negocio dentro de nuestro proyecto por lo que definimos paquetes para 
-   model
    -   Instrument
    -   [Hacemos uso de las anotaciones de JPA para que se nos haga mas rapida la ejecucion del proyecto], configuramos bien el archivo .properties del servidor la conexion y que hara JPA por nosotros cada vez que corramos la aplicacion
```
spring.datasource.url=jdbc:postgresql://localhost:5432/distribuida
spring.datasource.username=postgres
spring.datasource.password=junior200

spring.datasource.dbcp2.validation-query=SELECT 1
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

spring.jpa.generate-ddl=true
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=create-drop
```

-   repository
    -   La interfaz que hereda de JPARepository para hacer uso de los mtodos de JPQuery
-   service
    -   Los servicios que realizaran la insercion de Instrument, o envio de mensajes a RabbitMQ

Dentro de las dependencias tenemos
```
compile('org.springframework.boot:spring-boot-starter-data-jpa')
compile('org.springframework.cloud:spring-cloud-starter-config')
compile('org.springframework.boot:spring-boot-starter-actuator')
compile('org.springframework.boot:spring-boot-starter-web')
runtime('org.postgresql:postgresql')
```

-   Controllers
Dentro de los controllers tenemos que exponer un servicio rest, por lo que lo configuramos de la siguiente manera
```
@RestController
...
@RequestMapping(value = "/instrument/create",method = RequestMethod.POST)
public @ResponseBody Instrument createUser(@RequestBody Instrument instrument, UriComponentsBuilder ucBuilder) {
    logger.info("Creating Instrument : {}", instrument.getInstrumentName());
    Instrument instrumentSave=instrumentService.saveInstrument(instrument);
    return instrumentSave;
}

@RequestMapping(value = "/instrument/listar",method = RequestMethod.GET)
public List<Instrument>  listInstrument(){
    return instrumentService.listInstrument();
}

```
    





Nota: Para que funcione nuestro datos debemos tener en cuenta dos consideraciones que pueden afectar a que no se tome los datos desde el config server.

-   El .properties debe llevar el mismo nombre del proyecto, para que se referencie
-   O podemos ponerle un config application name{cliente1} y hacerle referencia dentro de nuestro .properties de el config server
- Debemos tener cuidado ya que configserver funciona en el puerto 8888 por defecto, por loq ue al usar este dato no nos configuraba en otro puerto, qeuda a consulta como cambiar ese puerto.



# Configuracion de RabbitMQ
[15672] puerto

Comot enemos la logica que cuando se inserte un instrumento se les notifique a los usuarios pues el productor sera, el microservicio 2[cli2-inst], y el que lo consume el microservicio1[cli1-alb-m]

Anadimos a las dependencias>
```
compile('org.springframework.boot:spring-boot-starter-amqp')

```

Dentro de La aplicacion ponemos la anotacion
```
@SpringBootApplication
@EnableRabbit
@EnableDiscoveryClient
```

Debemos definar las colas qeu vamos a consumir y la interfaz para declara la cola especifica. Para mejor entendimiento el mensaje lo mandamos en formato Json y con esto creamos el template de Rabbit.
```
	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
		return rabbitTemplate;
	}

```
Para mandar el mensjae nos creamos una clase Mensaje, y con esto pues solo lo mandamos con las configruaciones de la cola[nombre]
```
...
rabbitTemplate.convertAndSend(Client2InstrumApplication.EXCHANGE_NAME, Client2InstrumApplication.ROUTING_KEY, message);

```
## Para consumirlo

Obtenemos el nombre de la cola y lo anotamos con la anotacion @Rabbitlistener
```
@RabbitListener(queues = Client1AlbSingerApplication.QUEUE_GENERIC_NAME)
public void receiveMessage(final uce.edu.bautista.rabbitmq.Message message) {
    log.info("Recibiendo como mensaje generico: {}", message.toString());
}
```




## Para enviar emails
Esta parte es fresca porque spring ya nos ayuda con la configuracio y solo lo que debemos hacer es crear un .properties con las siguientes condiciones en nuestro caso va dentro del cliente1 porque este es el que envia el mensaje
```gradle
compile('org.springframework.boot:spring-boot-starter-mail')

```
.properties
```

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=galegale1024@gmail.com
spring.mail.password=****************
spring.mail.properties.smtp.auth=true
spring.mail.properties.smtp.starttls.enable=true

```

Creamos la clase Send Mail Service, para que *JavaMailSender*, mande el mensaje que necesitamos al mail del Singer.
```

SimpleMailMessage mailMessage=new SimpleMailMessage();
mailMessage.setTo(to);
mailMessage.setSubject("Notificacion");
mailMessage.setText("Te enviamos una notificacion de qiue se ingreso un nuevo instrumento");

javaMailSender.send(mailMessage);
```

## Eureka configuracion

Seguimos con la configuracion, y ahora nos toca con el servidor de Eureka, para lo que tenemos que configurar lo siguiente


Anadimos las dependencias

```gradle
compile('org.springframework.cloud:spring-cloud-starter-eureka-server')
compile('org.springframework.cloud:spring-cloud-starter-netflix-eureka-client')
compile('org.springframework.cloud:spring-cloud-starter-netflix-ribbon')

```

Dentro de la case de SpringBootApplication ponemos la naotacion
```
@SpringBootApplication
@EnableEurekaServer
```

Dentro de nuestras properties anadimos:
```
spring.application.name=eureka-server
server.port=8761
eureka.client.registerWithEureka=false
eureka.client.fetchRegistry=false
eureka.client.serviceUrl.defaultZone=http://localhost:8761/
```

>Dentro de ls clientes aumentamos>
-   la anotacion @EnableDiscoveryClient
-   compile('org.springframework.cloud:spring-cloud-starter-eureka')



## Para Zuul Gateway

En esta parte lo que tenemos es definir las rutas de acuerdo al gateway que hemos definido por lo que debemos dar estas anotaciones para que el servidor zuul funcione como proxy y se pueda conectar con el eureka

```
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
```

y defnidmos nuestros endpoints de donde har'a la

 redireccion como se muestra a continuacion, conectandolo al eureka de una vez
```
spring.application.name=zuul-server
server.port=9090

#para el cliente
eureka.client.registerWithEureka=false
eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/

zuul.prefix=/api
zuul.routes.eureka-cliente1.path=/eureka-client1/**
zuul.routes.eureka-cliente1.serviceId:eureka-cliente1

zuul.routes.eureka-cliente2.path=/eureka-client2/**
zuul.routes.eureka-cliente2.serviceId:eureka-cliente2

```


>Para el cliente
Dentro de lcliente solo tenemos que hacer las llamadas a los servicios, como nosotros definimos arriba, ademas deberemos usar Rest para comunicarnos con ellos por lo que en este caso usamos Thymeleaf con Spring boot.

```

```

Para que nos muestre la pagina, si ponemos /home, y no nos muestre el texto literal, debemos aumentar

```
@SpringBootApplication
@EnableWebMvc
```

Hacemos uso de RestTemplate que es propio de Java, se pod'ia usar PHP, C#, Python, etc pero para mayor comodidad hemos usado a Java como Cliente.

## Generamos los jar

Para generar el jar procedemos a usar los comandos de gradle desde el directorio

-   gradlew.bat
-   gradlew compileJava
-   gradlew build

Tenemos la carpeta> "projectFolder/build/libs[nombre.jar]"

[AQUI]:https://github.com/AEBU/Config-Server.git