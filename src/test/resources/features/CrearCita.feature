#language: es
Característica: Crear una cita

  Escenario: Cracion de cita exitosa
    Cuando un "usuario" se autentica exitosamente
    Y se tiene la informacion de una cita
    Entonces se puede crear una cita en la api

  Escenario: Cracion de cita con token invalido
    Cuando un "usuario" con un token invalido quiere crear una cita
    Entonces la api responde con status "403"

  Escenario: Cracion de cita con campos vacios
    Cuando un "usuario" con token valido quiere crear una cita
    Y se ingresan campos vacios al crear una cita
    Entonces la api responde con status de error "500"

  Escenario: Cracion de cita con documento invalido
    Cuando un "usuario" autenticado exitosamente
    Y quiere crear una cita con un documento de un paciente que no existe
    Entonces la api contesta con un error de status "500"

