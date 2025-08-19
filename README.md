# ProdManager

![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)
![Java](https://img.shields.io/badge/java-21-brightgreen)
![Spring Boot](https://img.shields.io/badge/springboot-3.5.4-blue)

---

## 🔹 Sobre o Projeto
**ProdManager** é um sistema de gerenciamento de produtos e estoque desenvolvido em Java com Spring Boot.  
Ele permite:

- Cadastrar, atualizar e consultar produtos.
- Registrar movimentações de estoque (entrada e saída).
- Acompanhar o histórico de alterações de quantidade de produtos.
- Facilitar a integração com front-end via API RESTful.

O projeto é ideal para aprendizado e aplicações de controle de estoque pequenas ou médias.

---

## 🔹 Funcionalidades Principais

- Cadastro de produtos
- Atualização de estoque via movimentações
- Registro detalhado de movimentações (estoque anterior e atualizado)
- Consulta de produtos com filtros e paginação
- APIs REST para CRUD de produtos e movimentações

---

## 🔹 Tecnologias

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **PostgreSQL** (desenvolvimento)
- **Maven**
- **DTOs em Java Records**
- **Enum para tipos de movimentação** (ENTRADA, SAÍDA, AJUSTES)

---

## 🔹 Estrutura do Projeto

``` bash
└── dev/lucaslowhan/prodmanager
    ├── controller
    │   ├── categoria
    │   │   └── CategoriaController.java
    │   ├── movimentacao
    │   │   └── MovimentacaoController.java
    │   ├── produto
    │   │   └── ProdutoController.java
    ├── domain
    │   ├── categoria
    │   │   └── dto
    │   │   │   ├── CategoriaRequestDTO.java
    │   │   │   ├── CategoriaResponseDTO.java
    │   │   │   └── CategoriaUpdateDTO.java
    │   │   └── Categoria.java
    │   ├── movimentacao
    │   │   └── dto
    │   │   │   ├── MovimentacaoRequestDTO.java
    │   │   │   └── MovimentacaoResponseDTO.java
    │   │   ├── Movimentacao.java
    │   │   └── TipoMovimentacao.java
    │   ├── produto
    │   │   └── dto
    │   │   │   ├── ProdutoRequestDTO.java
    │   │   │   ├── ProdutoResponseDTO.java
    │   │   │   └── ProdutoUpdateDTO.java
    │   │   └── Produto.java
    ├── repository
    │   ├── categoria
    │   │   └── CategoriaRepository.java
    │   ├── movimentacao
    │   │   └── MovimentacaoRepository.java
    │   ├── produto
    │   │   └── ProdutoRepository.java
    ├── service
    │   ├── categoria
    │   │   └── CategoriaService.java
    │   ├── movimentacao
    │   │   └── MovimentacaoService.java
    │   ├── produto
    │   │   └── ProdutoService.java
    └── ProdManagerApplication.java
```

---

## 🔹 Instalação e Uso

1. Clone o repositório:
```bash

git clone https://github.com/lucaslowhan/ProdManager.git
```
2. Entre na pasta do projeto:
```bash

cd ProdManager
```
3. Rode o projeto
```bash

mvn spring-boot:run
```
4. Acesse a API em:
```bash

http://localhost:8080/produtos
http://localhost:8080/movimentacoes
```
---
## 🔹 Exemplos de Requisições

**Cadastrar Produto (POST /produto)**
```json
{
     "nome": "Mouse ROG",
    "descricao": "Mouse com iluminação RGB para jogos.",
    "preco": 199.90,
    "quantidadeEstoque": 4,
    "estoqueMinimo": 5,
    "sku": "ELE-003",
    "categoriaId": 8
}
```

**Registrar Movimentação (POST /movimentacoes)**
```json
{
"produtoId": 1,
"tipoMovimentacao": "ENTRADA",
"quantidade": 20,
"descricao": "Reposição de estoque"
}
```

**Consultar Produto por Nome (GET /produtos?nome=Teclado)**
```http request
GET /produto/nome/{nome}

```
---
## 🔹 Contribuição ##

Contribuições são bem-vindas!
Para contribuir:

1. Faça um fork do projeto
2. Crie uma branch: git checkout -b feature/nova-funcionalidade
3. Commit suas alterações: git commit -m "Adiciona nova funcionalidade"
4. Push para a branch: git push origin feature/nova-funcionalidade
5. Abra um Pull Request

---

## 🔹 Contato

**Lucas Lowhan**
- GitHub: [lucaslowhan](https://github.com/lucaslowhan)
- LinkedIn: [linkedin.com/in/lucaslowhan](https://www.linkedin.com/in/lucaslowhan)