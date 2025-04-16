# ⚡ Desafio Back-End Bolt

Este projeto consiste em uma API desenvolvida em Java com Spring Boot para importar e consultar dados de usinas geradoras de energia do Brasil, disponibilizados em formato CSV pela ANEEL.

## 📁 Funcionalidades

- ⬇️ Download automático do CSV de usinas da ANEEL.
- 📥 Importação manual do CSV via endpoint.
- 🏆 Listagem das top 5 empresas com maior capacidade de geração (potência MW).

## 🔧 Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Web
- Lombok
- MySQL Driver
- Validação com Hibernate Validator

## 🚀 Endpoints

### `GET /usina/importaManualmente`

Importa manualmente o arquivo CSV da ANEEL e persiste os dados no banco de dados.

**Exemplo de uso:**

```bash
curl -X GET http://localhost:8080/usina/importaManualmente
curl -X GET http://localhost:8080/usina/lista/top5
