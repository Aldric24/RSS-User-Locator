package edu.ktu.lab1_rajesh.SQLConnector

import android.util.Log
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object SqlConnection {

    object ExampleTable : IntIdTable() {
        val exampleColumn = varchar("example_column", 255)
    }

    fun connectToDatabase(): Connection? {
        val url = "jdbc:mysql://seklys.ila.lt:5070/LDB"
        val user = "stud"
        val password = "vLXCDmSG6EpEnhXX"

        return try {
            Database.connect(url, user, password) // Establish the database connection
            println("Database connection established")
            DriverManager.getConnection(url, user, password)
        } catch (e: SQLException) {
            Log.e("Sql Connection", "Failed to connect to database: ${e.message}")
            null
        }
    }

    fun main(args: Array<String>) {
        val url = "jdbc:mysql://seklys.ila.lt:3306/LDB"
        val username = "stud"
        val password = "vLXCDmSG6EpEnhXX"

        var conn: Connection? = null
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance()
            conn = DriverManager.getConnection(url, username, password)
            println("Database connection established")
        } catch (ex: SQLException) {
            ex.printStackTrace()
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            if (conn != null) {
                try {
                    conn.close()
                } catch (sqlEx: SQLException) {
                    sqlEx.printStackTrace()
                }
            }
        }
    }
}
