import org.springframework.context.ApplicationContext

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
