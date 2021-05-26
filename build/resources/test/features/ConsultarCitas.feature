#language: es
Característica: Consultar citas

  Escenario: Consultar citas de un paciente
    Cuando un "usuario" genera un token valido
    Y quiere consultar todas las citas de un paciente por documento
    Entonces puede consultar las citas de un paciente correctamente

  Escenario: Consultar citas con token invalido
    Cuando un "usuario" con token invalido quiere consultar las citas de un paciente
    Entonces la api no permite hacer la consulta respondiendo con codigo "403"
