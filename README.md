# AJuDE API
> Projeto backend de PSOFT

## Links
- Swagger: https://ajudeapi.herokuapp.com/api/v1/swagger-ui.html#/
- API: https://ajudeapi.herokuapp.com/api/v1/
- Frontend: https://thayannevls.github.io/psoft-frontend/

## Conta pré criada

**Usuário:**
**Senha:**

## Como rodar

`./mvnw spring-boot:run`

## Sobre

Implementamos uma API para o sistema AJuDE seguindo o padrão REST e tentando aplicar as melhores práticas de Spring+JPA+Java. Para isso, dividimos nossa arquitetura entre `controllers`, `repositories`, `models`, `DTO`s e `services`. Cada package corresponde a um recurso, onde contém seu controller, repostiry, model, DTOs e service. Podendo também conter sub-packages de sub-recursos.

Usamos exceções para retornar erros HTTP nas situações que se faziam necessários, e DTOs para proteger nossa API e também para dar uma melhor resposta para nosso usuário.

Também usamos JWT Token para realizar a autenticação.

## Grupo

- [Thayanne Sousa](https://github.com/thayannevls)
- [Vitor Diniz](https://github.com/vitorbdiniz)
