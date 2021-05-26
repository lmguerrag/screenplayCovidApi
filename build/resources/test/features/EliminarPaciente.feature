#language: es
Característica: Eliminar un paciente

  Antecedentes:
    Dado que un "usuario" guarda un paciente

  Escenario: Eliminacion de paciente exitosa
    Cuando un "usuario" autenticado quiere eliminar un paciente registrado por numero de documento
    Entonces puede eliminar un paciente correctamente

  Escenario: Eliminacion de paciente con documento invalido
    Cuando un "usuario" autenticado quiere eliminar un paciente con numero de documento invalido
    Entonces la api arroja codigo "404"

  Escenario: Eliminacion de paciente con token invalido
    Cuando un "usuario" con token invalido quiere eliminar un paciente registrado
    Entonces la api no permitira eliminar el paciente y respondera con codigo "403"