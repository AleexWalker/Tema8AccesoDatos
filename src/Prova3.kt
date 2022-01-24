import redis.clients.jedis.Jedis

fun main() {
    val con = Jedis("localhost")
    con.connect()

    val c = con.mget("mes1", "mes2", "mes3")  // c serà un MutableList
    for (s in c)
        println(s)

    con.close()
}

