import org.jetbrains.exposed.sql.Database

fun main() {
    Database.connect("jdbc:h2:~/db:calendar", driver = "org.h2.Driver")
}