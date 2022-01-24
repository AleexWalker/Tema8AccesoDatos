import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.JTextArea
import javax.swing.DefaultListModel
import javax.swing.JList
import javax.swing.JScrollPane
import java.awt.FlowLayout
import java.awt.Color
import redis.clients.jedis.Jedis

import java.awt.EventQueue

class EstadisticaRD : JFrame() {

    val etTipClau= JLabel("Tipus:")
    val tipClau= JTextField(8)
    val contClau = JTextArea(8,15)
    val conexion = Jedis("89.36.214.106")
    val listModel = DefaultListModel<String>()
    val llClaus = JList(listModel)

    init {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setBounds(100, 100, 450, 450)
        setLayout(FlowLayout())


        llClaus.setForeground(Color.blue)
        val scroll = JScrollPane(llClaus)
        llClaus.setVisibleRowCount(20)

        val scroll2 = JScrollPane(contClau)

        add(scroll)
        add(etTipClau)
        add(tipClau)
        add(scroll2)

        setSize(600, 400)
        setVisible(true)

        val conexion = Jedis("89.36.214.106")
        conexion.connect()
        conexion.auth("ieselcaminas.ad")

        inicialitzar()

        llClaus.addListSelectionListener{valorCanviat()}

    }

    fun inicialitzar(){
        conexion.connect()
        conexion.auth("ieselcaminas.ad")

        val listadoJedis = conexion.keys("*").sorted()  //c és un MutableSet

        for (claves in listadoJedis) {
            listModel.addElement(claves)
        }

    }

    fun valorCanviat() {
        conexion.connect()
        conexion.auth("ieselcaminas.ad")

        val listadoJedis = conexion.keys("*")  //c és un MutableSet

        llClaus.addListSelectionListener {
            val seleccionado = llClaus.selectedValue.toString()
            val valor = conexion.type(seleccionado)
            contClau.text = ""
            when (valor) {
                "string" -> {
                    tipClau.text = "String"
                    contClau.append(conexion.get(seleccionado))
                }
                "hash" -> {
                    tipClau.text = "Hash"
                    val listado = conexion.hkeys(seleccionado)
                    for (items in listado)
                        contClau.append("$items: ${conexion.hget(seleccionado, items)}\n")
                }
                "list" -> {
                    tipClau.text = "List"
                    val listado = conexion.lrange(seleccionado, 0, -1)
                    for (items in listado)
                        contClau.append("$seleccionado: $items\n")
                }
                "set" -> {
                    tipClau.text = "Set"
                    val listado = conexion.smembers(seleccionado)
                    for (items in listado)
                        contClau.append("$seleccionado: $items\n")
                }
                "zset" -> {
                    tipClau.text = "ZSet"
                    val listado = conexion.zrangeWithScores(seleccionado, 0, -1)
                    for (items in listado)
                        contClau.append("${items.element} --> ${items.score}\n")
                }
                else -> {
                    contClau.text = "¡Ningun tipo de dato accesible!"
                }
            }
        }
    }
}

fun main(args: Array<String>) {
    EventQueue.invokeLater {
        EstadisticaRD().isVisible = true
    }
}

