#language: es
  Característica: Consultar un paciente

    Escenario: Consulta de paciente exitosa
      Cuando un "usuario" tiene un token valido
      Y quiere consultar un paciente registrado por numero de documento
      Entonces puede consultar un paciente correctamente

    Escenario: Consulta de paciente con token invalido
      Cuando un "usuario" tiene con token invalido quiere consultar un paciente registrado por numero de documento
      Entonces la api responde con codigo "403"

    Escenario: Consulta de paciente no registrado
      Cuando un "usuario" con token valido quiere consultar un paciente cuyo numero de documento no existe
      Entonces la api responde con codigo de status "404"





