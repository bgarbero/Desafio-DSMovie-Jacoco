# Desafio DSMovie Jacoco

Projeto de testes unitários para a camada de **service** da aplicação DSMovie, desenvolvido como parte da trilha de testes automatizados do curso **DevSuperior** (instrutor Nélio Alves). O objetivo do desafio é escrever testes com **JUnit 5** e **Mockito**, atingindo alta cobertura de código validada pelo **JaCoCo**.

## Sobre o projeto DSMovie

O DSMovie é uma API de filmes e avaliações de filmes, com as seguintes regras de negócio:

- A **visualização** dos dados dos filmes é pública e não exige login.
- As **alterações** de filmes (inserir, atualizar, deletar) são permitidas apenas para usuários com papel **ADMIN**.
- As **avaliações** (scores) podem ser registradas por qualquer usuário autenticado, seja **CLIENT** ou **ADMIN**.
- A entidade `Score` armazena uma nota de 0 a 5 dada por um usuário a um filme.
- Sempre que uma nova nota é registrada, o sistema recalcula a **média das notas** de todos os usuários para aquele filme e atualiza a entidade `Movie` com o novo `score` (média) e o `count` (quantidade de votos).

## Objetivo do desafio

Implementar todos os testes unitários de service do projeto, cobrindo `MovieService`, `ScoreService` e `UserService`. Com todos os testes implementados, o JaCoCo deve reportar **100% de cobertura**, sendo o critério mínimo de aprovação **12 dos 15 testes**.

## Testes implementados

### `MovieServiceTests`
- `findAllShouldReturnPagedMovieDTO`
- `findByIdShouldReturnMovieDTOWhenIdExists`
- `findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist`
- `insertShouldReturnMovieDTO`
- `updateShouldReturnMovieDTOWhenIdExists`
- `updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist`
- `deleteShouldDoNothingWhenIdExists`
- `deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist`
- `deleteShouldThrowDatabaseExceptionWhenDependentId`

### `ScoreServiceTests`
- `saveScoreShouldReturnMovieDTO`
- `saveScoreShouldThrowResourceNotFoundExceptionWhenNonExistingMovieId`

### `UserServiceTests`
- `authenticatedShouldReturnUserEntityWhenUserExists`
- `authenticatedShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists`
- `loadUserByUsernameShouldReturnUserDetailsWhenUserExists`
- `loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists`

**Total: 15/15 testes implementados.**

## Resultado da cobertura (JaCoCo)

| Classe | Missed Instructions | Cov. | Missed Branches | Cov. | Missed Cxty | Lines | Missed Methods | Methods | Missed Classes | Classes |
|---|---|---|---|---|---|---|---|---|---|---|
| MovieService | 0 | 100% | 0 | 100% | 0 | 28 | 0 | 9 | 0 | 1 |
| ScoreService | 0 | 100% | 0 | 100% | 0 | 18 | 0 | 3 | 0 | 1 |
| UserService | 0 | 100% | 0 | 100% | 0 | 15 | 0 | 3 | 0 | 1 |
| **Total** | **0 de 283** | **100%** | **0 de 8** | **100%** | **0 de 19** | **61** | **0 de 15** | **15** | **0** | **3** |

> Pacote `com.devsuperior.dsmovie.services` — 100% de cobertura de instruções e de branches, sem nenhuma linha, método ou classe não testada.

## Tecnologias utilizadas

- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **Spring Security** (OAuth2 / JWT)
- **H2 Database** (banco em memória para testes)
- **JUnit 5**
- **Mockito**
- **JaCoCo** (plugin Maven, versão 0.8.14)
- **Maven**

## Estrutura de testes

Os testes seguem o padrão **AAA (Arrange, Act, Assert)** e utilizam:

- `@ExtendWith(SpringExtension.class)` para integração com o contexto de testes do Spring.
- `@Mock` e `@InjectMocks` para isolar a camada de service das dependências (repositories, outros services e utilitários).
- Classes *Factory* (`MovieFactory`, `ScoreFactory`, `UserFactory`, `UserDetailsFactory`) para centralizar a criação de objetos de teste.
- Simulação de cenários de sucesso e de exceção (`ResourceNotFoundException`, `DatabaseException`, `UsernameNotFoundException`) através de `Mockito.when(...)` e `Mockito.doThrow(...)`.

## Como executar os testes

```bash
# Executa todos os testes e gera o relatório de cobertura
./mvnw clean test

# O relatório HTML do JaCoCo fica disponível em:
# target/jacoco-report/index.html
```

## Observações

- O projeto foi ajustado da versão original em **JDK 25** para **Java 21**, garantindo compatibilidade com ambientes corporativos legados.
- Os testes de `UserServiceTests` cobrem tanto o método `authenticated()` (usado para identificar o usuário logado a partir do JWT) quanto o `loadUserByUsername()` (usado pelo Spring Security no processo de autenticação).
