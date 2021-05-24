#language: es

Característica: Administrar información de pacientes

  Escenario: Creacion de paciente exitosa
    Cuando un "usuario" se autentica correctamente en la api
    Y se tiene la informacion de un paciente
    Entonces se puede crear el paciente en la api

  Escenario: Creacion de paciente sin token de autenticacion valido
    Cuando un "usuario" quiere guardar la informacion de un paciente con un token invalido
    Entonces la api contesta con codigo "403"

  Escenario: Creacion de un paciente con campos vacios
    Cuando un "usuario" se autentica correctamente
    Y quiere guardar la informacion de un paciente dejando campos vacios
    Entonces la api contesta con codigo de status "500"
