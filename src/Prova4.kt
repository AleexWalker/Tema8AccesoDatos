import redis.clients.jedis.Jedis

fun main() {
    val con = Jedis("localhost")
    con.connect()

    val c = con.keys("*")  //c és un MutableSet
    for (s in c)
        println(s)

    con.close()
}

