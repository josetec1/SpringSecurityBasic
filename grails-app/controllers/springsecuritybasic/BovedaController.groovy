package springsecuritybasic

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class BovedaController {

    BovedaService bovedaService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond bovedaService.list(params), model:[bovedaCount: bovedaService.count()]
    }

    def show(Long id) {
        respond bovedaService.get(id)
    }

    def create() {
        respond new Boveda(params)
    }

    def save(Boveda boveda) {
        if (boveda == null) {
            notFound()
            return
        }

        try {
            bovedaService.save(boveda)
        } catch (ValidationException e) {
            respond boveda.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'boveda.label', default: 'Boveda'), boveda.id])
                redirect boveda
            }
            '*' { respond boveda, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond bovedaService.get(id)
    }

    def update(Boveda boveda) {
        if (boveda == null) {
            notFound()
            return
        }

        try {
            bovedaService.save(boveda)
        } catch (ValidationException e) {
            respond boveda.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'boveda.label', default: 'Boveda'), boveda.id])
                redirect boveda
            }
            '*'{ respond boveda, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        bovedaService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'boveda.label', default: 'Boveda'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'boveda.label', default: 'Boveda'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
