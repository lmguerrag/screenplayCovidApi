#language: es

Característica: Autenticación de usuario en la api

  Escenario: Autenticacion exitosa
    Cuando un "usuario" tiene credenciales validas
    Entonces se puede autenticar en la api correctamente

  Escenario: Autenticacion con password incorrecto
    Cuando un "usuario" tiene password incorrecto
    Entonces la api contesta con codigo de error "403"

  Escenario: Autenticacion con password Covid
    Cuando un "usuario" tiene username invalido y password "Covid"
    Entonces la api contesta con status "403"


