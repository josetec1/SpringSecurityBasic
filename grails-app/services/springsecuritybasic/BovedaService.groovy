package springsecuritybasic

import grails.gorm.services.Service

@Service(Boveda)
interface BovedaService {

    Boveda get(Serializable id)

    List<Boveda> list(Map args)

    Long count()

    void delete(Serializable id)

    Boveda save(Boveda boveda)

}