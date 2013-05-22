import org.grails.User

println 'DB INIT: started'

User user1 = new User(name: "Feleti John")
user1.save(failOnError: true)

User user2 = new User(name: "John Feleti")
user2.save(failOnError: true)

println 'DB INIT: ended'
