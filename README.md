## Registro de Paciente
-> Endpoint: POST localhost:8080/api/auth/register

```json
{
    "login": "paciente.exemplo@email.com",
    "password": "senhaSegura123",
    "name": "João da Silva",
    "gender": "Masculino",
    "dateBirth":"2004-09-30",
    "role": "PATIENT",
    "address": {
        "street": "rua 123",
        "number": 50,
        "city": "SP",
        "state": "SP"
    }
}
```

## Registro de Profissional de saúde
```json
{
    "login": "emailmedico@email.com",
    "password": "senha123",
    "name": "Joãozin Medico",
    "role": "PROFESSIONAL_HEALTH",
    "crm": "122345",
    "professionalRole": "DOCTOR"
}
```

## Registro de Admin
```json
{
    "login": "amin123",
    "password": "senha123",
    "name": "amin",
    "role": "ADMIN"
}
```



## Login
-> Endpoint: POST localhost:8080/api/auth/login

```json
{
  "login": "paciente.exemplo@email.com",
  "password": "senhaSegura123"
}
```

## Agendar Consulta
-> Endpoint: localhost:8080/api/consultations/schedule <br>
Header: Auhtorization | Bearer <SEU_TOKEN_JWT_AQUI>

```json
{
 "dateTime": "2025-12-31T10:00:00"
}
```


