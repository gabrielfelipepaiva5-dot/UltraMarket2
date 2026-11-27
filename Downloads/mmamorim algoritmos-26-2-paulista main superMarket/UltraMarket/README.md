# UltraMarket

Sistema de mini mercado em Java, 100% no terminal, com dados salvos em arquivos CSV.

## Funcionalidades
- Produtos: cadastrar, listar, buscar, alterar e remover
- Clientes: cadastrar, listar, buscar, alterar e remover
- Realizar compra: cliente cadastrado ou nao, varios produtos, cupom de desconto, forma de pagamento e recibo
- Controle de estoque: listar, repor e ajustar; a compra baixa o estoque automaticamente
- Cupons de desconto: cadastrar, listar e remover

## Como executar
Requisito: Java 17 ou mais novo instalado.

Na pasta do projeto (onde fica a pasta `src`):

    javac -d out src/UltraMarket/*.java
    java -cp out UltraMarket.UltraMarket

Tambem funciona abrindo o projeto no NetBeans e executando a classe `UltraMarket`.

## Dados
Os dados ficam na pasta `UltraMarket/` (criada automaticamente): `produtos.csv`, `clientes.csv` e `cupons.csv`, separados por ponto e virgula.

## Dicas de uso
- Em qualquer pergunta, apertar Enter sem digitar nada cancela a operacao.
- Nao use ponto e virgula nos textos digitados.
- O CPF deve ter 11 numeros, sem pontos ou tracos.
