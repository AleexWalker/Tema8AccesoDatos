import redis.clients.jedis.Jedis

fun main() {
    val con = Jedis("localhost")
    con.connect()
    println(con.get("v3"))
    con.close()
}

