package springsecuritybasic

import grails.gorm.transactions.Transactional
import security.*

class BootStrap {

    def init = { servletContext ->
     addTestUser()

    }

    @Transactional
    void addTestUser() {
        def adminRol = new Rol(authority: 'ROLE_ADMIN').save(failOnError: true)

        def testUser = new Usuario (username: 'seminario', password: '1234').save(failOnError: true)

        UsuarioRol.create testUser, adminRol

        UsuarioRol.withSession {
            it.flush()
            it.clear()
        }

        assert Usuario.count() == 1
        assert Rol.count() == 1
        assert UsuarioRol.count() == 1
    }



    def destroy = {
    }


}
