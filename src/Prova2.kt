import redis.clients.jedis.Jedis

fun main() {
    val con = Jedis("localhost")
    con.connect()

    val valor = "Aquesta clau és una clau creada des de Kotlin"
    con.set("clau_Java", valor)

    println(con.get("clau_Java"))
    con.close()
}

