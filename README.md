How to externalize database initialization
==========================================

Place database init code into a .groovy script, for example DbInitConfig.groovy

```groovy
    import org.grails.User

    User user1 = new User(name: "Feleti John")
    user1.save(failOnError: true)

    User user2 = new User(name: "John Feleti")
    user2.save(failOnError: true)
```

We shall call in order to run Grails application

```groovy
    grails run-app -Dgrails.db.init.config.location=DbInitConfig.groovy
```

Now we need to catch the system property in Config.groovy

```groovy
    if (System.properties['grails.db.init.config.location']) {
        String config = System.properties['grails.db.init.config.location']
        grails.db.init.config.location = config
    }
```

The last step is to run the Groovy script inside the Bootstrap.groovy init method

```groovy
    class BootStrap {

        def grailsApplication

        def init = { servletContext ->
            String scriptFile = grailsApplication.config.grails.db.init.config.location
            File script = new File(scriptFile)
            if (script.exists()) {
                ApplicationContext ctx = grailsApplication.mainContext
                Binding binding = new Binding(ctx: ctx, grailsApplication: grailsApplication)
                ClassLoader loader = this.class.classLoader
                GroovyShell shell = new GroovyShell(loader, binding)
                shell.evaluate(script.text)
            } else {
                log.error "Script doesn't exist: $scriptFile"
            }
        }

        def destroy = {
        }
    }
```