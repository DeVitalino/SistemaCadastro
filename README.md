# Sistema de Cadastro de Usuários (Spring Boot)

Este é um projeto simples de cadastro de usuários desenvolvido com Spring Boot, seguindo uma arquitetura MVC com persistência em memória (In-Memory).

## Arquitetura
O projeto está dividido nas seguintes camadas:
- **Controller**: Gerencia os endpoints REST.
- **Service**: Contém a lógica de negócio (geração de ID).
- **Repository**: Responsável pela persistência dos dados em uma lista.
- **Model**: Representa a entidade `Usuario`.

## Pré-requisitos
- Java 17 ou superior
- Maven 3.6 ou superior

## Como Rodar a Aplicação

1. Clone o repositório ou baixe os arquivos.
2. Navegue até a pasta raiz do projeto via terminal.
3. Execute o comando para compilar e rodar a aplicação:
   ```bash
   mvn spring-boot:run
   ```
A aplicação estará disponível em `http://localhost:8080`.

## Endpoints e Testes (cURL)

Abaixo estão os comandos cURL que podem ser utilizados para testar a aplicação ou importados no Postman.

### 1. Listar Usuários
Retorna a lista de todos os usuários cadastrados.
```bash
curl --location --request GET 'http://localhost:8080/usuarios'
```

### 2. Cadastrar Usuário
Adiciona um novo usuário ao sistema. O ID é gerado automaticamente pelo serviço.
```bash
curl --location --request POST 'http://localhost:8080/usuarios' \
--header 'Content-Type: application/json' \
--data-raw '{
    "nome": "João Silva",
    "telefone": "11999999999",
    "email": "joao.silva@email.com"
}'
```

### 3. Remover Usuário
Remove um usuário existente pelo seu ID.
```bash
curl --location --request DELETE 'http://localhost:8080/usuarios/1'
```

## Importando no Postman
Para importar os comandos acima no Postman:
1. Abra o Postman.
2. Clique no botão **Import** no canto superior esquerdo.
3. Cole o comando cURL na aba **Raw text**.
4. Clique em **Continue** e depois em **Import**.
