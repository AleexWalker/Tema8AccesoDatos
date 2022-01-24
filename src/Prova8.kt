import redis.clients.jedis.Jedis

fun main() {
    val con = Jedis("localhost")
    con.connect()

    val conjOrd = con.zrangeWithScores("puntuaciones", 0, -1)  // conjOrd és un MutableSet<Tuple>
    for (t in conjOrd)
        println(t.getElement() + " ---> " + t.getScore())

    con.close()
}

