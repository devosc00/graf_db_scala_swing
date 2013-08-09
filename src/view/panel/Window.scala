package view.panel

import java.awt.Dimension
import java.sql.DriverManager

import scala.swing.BorderPanel
import scala.swing.BorderPanel.Position.Center
import scala.swing.BorderPanel.Position.North
import scala.swing.BorderPanel.Position.South
import scala.swing.Button
import scala.swing.FlowPanel
import scala.swing.Label
import scala.swing.MainFrame
import scala.swing.SimpleSwingApplication
import scala.swing.Table
import scala.swing.TextField
import scala.swing.event.ButtonClicked
import scala.swing.event.Key
import scala.swing.event.KeyPressed
import scala.util.Random

import org.postgresql.Driver

import javax.swing.table.DefaultTableModel

object Window extends SimpleSwingApplication {

  val button = new Button("Enter")
  val iteracje = new TextField { text = ""; columns = 10 }
  val wynik = new TextField { text = ""; columns = 50 }

  val tableModel = new DefaultTableModel(new Array[Array[AnyRef]](0, 0), Array[AnyRef]())
  val table = new Table() { model = tableModel }

  def act = {
    val r = new Random
    val row = Integer.parseInt(iteracje.text)
    for (i <- 0 to row - 1) {
      tableModel.addColumn(tableModel.addRow(Array[AnyRef]()))
    }
    val tabs = new Array[Array[Int]](tableModel.getColumnCount(), tableModel.getRowCount())
    for (i <- 0 to row - 1; j <- 0 to i) {
      if (i.equals(j)) {
        tabs(i)(j) = 0
        tableModel.setValueAt(0.asInstanceOf[AnyRef], i, j)
      } else
        tableModel.setValueAt(r.nextInt(2).asInstanceOf[AnyRef], i, j)
      tableModel.setValueAt(tableModel.getValueAt(i, j), j, i)
      tabs(i)(j) = tableModel.getValueAt(i, j).##
      wynik.text += tableModel.getValueAt(i, j)
      //tabs(j)(i) += tabs(i)(j)
      //  println(tabs(i)(j))
    }

    connectDB(tabs)
  }

  def connectDB(tab: Array[Array[Int]]) {
    import java.sql._
    classOf[org.postgresql.Driver]
    val db = DriverManager.getConnection("jdbc:postgresql://localhost/graf_db", "postuser1", "postpass")
    val st = db.createStatement()
    println("db con")
    try {
      val clean = "delete from graf"
      st.executeUpdate(clean)
      for (i <- 0 to tab.length - 1; j <- 0 to i) {
        val sql = "insert into graf values (" + i + "," + j + "," + tab(i)(j) + ")"
        st.executeUpdate(sql)
      }
    } catch {
      case e => e.printStackTrace
    }
    db.close
    println("db close")
  }

  def update() {
    import java.sql._
    classOf[org.postgresql.Driver]
    val db = DriverManager.getConnection("jdbc:postgresql://localhost/graf_db", "postuser1", "postpass")
    val st = db.createStatement()

    try {
      val up = "update graf set sum = 0 where graf.ro = 0"
      st.executeUpdate(up)
      println("update open")
    } catch {
      case e => e.printStackTrace
    }
    db.close
    println("update close")
  }

  val ui = new BorderPanel {
    import BorderPanel.Position._
    add(new FlowPanel(new Label("Podaj liczbę iteracji"), iteracje, button), North)
    layout(new FlowPanel(table)) = Center
    add(new FlowPanel(new Label("Wynik"), wynik), South)

    listenTo(button, button.keys)

    reactions += {
      case KeyPressed(_, Key.Enter, _, _) =>
        act
        iteracje.text = ""
        println("key pressed")

      case ButtonClicked(b) =>
        act
        iteracje.text = ""
        println("button clicked")

      case KeyPressed(_, Key.U, _, _) =>
        update
        println("update pressed")

    }

  }

  def top = new MainFrame {
    title = "Graf DB"
    preferredSize = new Dimension(1000, 600)
    contents = ui
  }
}



  
  
  
  
