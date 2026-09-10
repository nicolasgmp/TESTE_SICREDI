
# Teste Sicredi

API REST para gerenciamento de pautas e sessões de votação, desenvolvida em Java com Spring
Boot e PostgreSQL, incluindo integração com aplicativo mobile via respostas JSON dinâmicas.

## Decisões de Desenvolvimento

- **Separação por responsabilidades:** controllers, use cases, services, repositories, entities e DTOs foram separados para manter o código organizado e facilitar sua manutenção, sem adicionar camadas desnecessárias.

- **DTOs:** utilizados para separar os objetos recebidos e retornados pela API das entidades persistidas no banco.

- **Versionamento pela URL:** foi utilizado o prefixo `/v1` por ser uma abordagem simples e explícita. Futuras alterações incompatíveis podem ser disponibilizadas em `/v2`.

- **Configuração por variáveis de ambiente:** informações que podem variar entre ambientes, como a URL base da aplicação, podem ser configuradas externamente sem alterar o código.

- **Testes automatizados:** foram utilizados testes unitários com JUnit e Mockito para validar principalmente as regras de negócio dos use cases.

- **Teste de performance:** foi utilizado k6 para simular múltiplos usuários realizando votos simultaneamente e observar o comportamento da API sob carga.

- **Integração com o aplicativo mobile:** as respostas de tela seguem o formato especificado no desafio, permitindo que o cliente monte as interfaces a partir dos JSONs retornados pela API.

Tecnologias Utilizadas: 
- Java 25
- Spring Boot 4.1.0
- PostgreSQL - RDS
- EC2

Endpoint Base AWS: http://ec2-3-142-120-177.us-east-2.compute.amazonaws.com:8080

## Rotas da aplicação

/v1/agenda - POST

Request Body
```
{
  "agenda_title": "Aumento do valor da contribuição"
}
```
Response Body
```
{
  "id": 6,
  "title": "Aumento do valor da contribuição"
}
```
##
/v1/voting-session - POST

Request Body
```
{
  "agendaId": 6,
  "durationInMinutes": 50
}
```
Response Body
```
{
  "endTime": "2026-09-10T09:55:01.780678556"
}
```
##
/v1/vote - POST

Request Body

```
{
  "agendaId": 6,
  "associateId": 10,
  "type": "YES" | "NO"
}
```
##
/v1/voting-sessions/result?agendaId=6 - GET

Response Body
```
{
  "agendaId": 6,
  "yesVotes": 0,
  "noVotes": 0
}
```

## Telas
Formulário de Pauta

/v1/screens/create-agenda
```
{
  "tipo": "FORMULARIO",
  "titulo": "Criação de Pauta",
  "itens": [
    {
      "tipo": "INPUT_TEXTO",
      "id": "title",
      "titulo": "Título da pauta",
      "valor": ""
    }
  ],
  "botaoOk": {
    "texto": "Criar Pauta",
    "url": "http://ec2-3-142-120-177.us-east-2.compute.amazonaws.com:8080/v1/agenda",
    "body": {}
  },
  "botaoCancelar": {
    "texto": "Cancelar",
    "url": "http://ec2-3-142-120-177.us-east-2.compute.amazonaws.com:8080"
  }
}
```
##
Formulário para Abrir Votação

/v1/screens/open-voting-session?agendaId=6
```
{
  "tipo": "FORMULARIO",
  "titulo": "Abertura de Sessão de Votação",
  "itens": [
    {
      "tipo": "INPUT_NUMERO",
      "id": "durationInMinutes",
      "titulo": "Duração da sessão (minutos)",
      "valor": "1"
    }
  ],
  "botaoOk": {
    "texto": "Abrir sessão",
    "url": "http://ec2-3-142-120-177.us-east-2.compute.amazonaws.com:8080/v1/voting-session",
    "body": {
      "idAgenda": 6
    }
  },
  "botaoCancelar": {
    "texto": "Cancelar",
    "url": "http://ec2-3-142-120-177.us-east-2.compute.amazonaws.com:8080"
  }
}
```
##
Seleção de Voto

/v1/screens/create-vote?agendaId=6&associateId=10
```
{
  "tipo": "SELECAO",
  "titulo": "Votação",
  "itens": [
    {
      "texto": "SIM",
      "url": "http://ec2-3-142-120-177.us-east-2.compute.amazonaws.com:8080/v1/vote",
      "body": {
        "agendaId": 6,
        "associateId": 10,
        "type": "YES"
      }
    },
    {
      "texto": "NÃO",
      "url": "http://ec2-3-142-120-177.us-east-2.compute.amazonaws.com:8080/v1/vote",
      "body": {
        "agendaId": 6,
        "associateId": 10,
        "type": "NO"
      }
    }
  ]
}
```

## Teste de Perfomance
Foi utilizado o k6 para realizar um teste de carga sobre o endpoint de votação.

O teste simula 100 usuários virtuais realizando votos durante 2 minutos.

Antes de executar o teste, é necessário criar uma pauta e abrir uma sessão de votação.

```
k6 run -e BASE_URL=(url base da API) -e AGENDA_ID=(id da pauta criada) vote-load-test.js
```

## Versionamento da API

A API utiliza versionamento através da URL, por exemplo:

`/v1/agenda`

Essa abordagem foi escolhida por ser simples e fácil de manter.

Caso uma alteração futura seja incompatível com a versão atual, uma nova versão poderá ser disponibilizada da mesma forma:

`/v2/agenda`

Dessa forma, clientes que ainda utilizam a `v1` continuam funcionando enquanto os novos clientes podem migrar para a `v2`.
