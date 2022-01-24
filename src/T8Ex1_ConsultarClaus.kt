import redis.clients.jedis.Jedis
import java.util.*

fun main() {
    val conexion = Jedis("89.36.214.106")
    conexion.connect()
    conexion.auth("ieselcaminas.ad")

    var contadorMenu : Int = 1
    var itemMenu : Int

    val listadoJedis = conexion.keys("*")  //c és un MutableSet

    for (claves in listadoJedis) {
        println("$contadorMenu.- $claves (${conexion.type(claves)})" )
        contadorMenu++
    }

    print("Introduce un numero (0 para salir): ")
    itemMenu = readLine()!!.toInt()

    do {

        for (claves in listadoJedis) {

            if (conexion.type(claves) == "string") {
                println("$claves: ${conexion.get(claves)}")
            } else if (conexion.type(claves) == "hash") {
                val listaHash = conexion.hkeys(claves)
                for (subcamps in listaHash)
                    println("$subcamps: ${conexion.hget(claves, subcamps)}")

            } else if (conexion.type(claves) == "zset") {
                val listaZSet = conexion.zrangeWithScores(claves, 0, -1)
                println("$claves: ")
                for (subcamps in listaZSet)
                    println("\t ${subcamps.element} --> ${subcamps.score}")

            } else if (conexion.type(claves) == "set") {
                val listaSet = conexion.smembers(claves)
                for (subcamps in listaSet)
                    println("$claves: $subcamps")

            } else if (conexion.type(claves) == "list") {
                val listaList = conexion.lrange(claves, 0, -1)
                for (subcamps in listaList)
                    println("$claves: $subcamps")

            }

            print("Introduce un numero (0 para salir): ")
            itemMenu = readLine()!!.toInt()

        }

    } while (itemMenu != 0)

    println("¡Gracias por usar el menu!")

    conexion.close()
}
