# Load Balancer

Desarrollo de un load balancer implementado con el algoritmo Round Robin y con health checks para comprobar el estado de los servidores y ver su
disponibilidad.

## ¿Cómo probarlo?

1. Clona el repositorio y descarga las dependencias necesarias.
2. Ejecuta los tres servidores. Estos son: **MainServer**, **Server1** y **Server2**.

   Para ello, habrá que agregar las siguientes
   variables de entorno, de forma que cada servidor sepa en qué puerto debe ejecutarse.
    - **MainServer**: spring.profiles.active=lb
    - **Server1**: spring.profiles.active=server1
    - **Server2**: spring.profiles.active=server2

En IntelliJ, esto se puede hacer desde **Run -> Edit Configurations -> Application -> Environment variables**.

Una vez hecho esto, se pueden levantar los tres servidores y probar a realizar peticiones al load balancer, por ejemplo, usando `curl http://localhost:3000/` o directamente
con dicha URL desde un navegador.

Si los dos servidores (Server1 y Server2) se encuentran levantados, el load balacer redirigirá las peticiones de forma alterna entre
ambos servidores, según el algortimo Round Robin.

A su vez, de forma asíncrona cada 10 segundos en el background, se realizan health checks, para detectar si uno de los servidores ha dejado de
funcionar o se ha parado y en ese caso, las peticiones solo se enviarán a los servidores disponibles. Se puede probar esto parando el Server1 y/o Server2
y viendo como las peticiones del load balancer se adaptan correctamente.

## ¿Qué es un load balancer?

Un load balancer se encarga de distribuir las peticiones entre varios servidores, de forma que se reparte la carga sin saturar un único
servidor. Esto incrementa la disponibilidad del sistema y permite que sea escalable, pudiendo añadir más servidores si la carga aumenta
sin afectar al servicio.

Puede implementarse en distintos niveles del modelo OSI. En este caso, se ha implementado en la capa siete, que corresponde a la capa
de aplicación.

Por último, el load balancer se ha desarrollado usando el algoritmo Round Robin, que consiste en ir rotando las peticiones entre los servidores
que se encuentren disponibles en la lista de servidores, empezando por el primero. Cuando se llega al final de la lista, se vuelve
a comenzar.

Por ejemplo, si se tienen los servidores 1 y 2, y se realizan cuatro peticiones, el orden
sería:

- Primera petición -> Servidor 1
- Segunda petición -> Servidor 2
- Tercera petición -> Servidor 1
- Cuarta petición -> Servidor 2
