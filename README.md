<!--TÍTULO-->
# Jogo Sistema Digestório


<!--DESCRIÇÃO-->
> Jogo desenvolvido para o colégio Piaget. <br/>
> O jogo consiste em um quiz com perguntas aleatórias onde os jogadores fazem pontos ao responder corretamente, competindo para subir no ranking e superar seus recordes.


<!--STATUS-->
## Status
> ✔ Concluído.


<!--FUNCIONALIDADES-->
## Funcionalidades
````
Aluno:
   . Jogar
   . Ver Ranking
   . Ver Configurações

Professor:
   . Administrar Perguntas
   . Ver Ranking
   . Ver Configurações

Administrador:
   . Cadastrar Usuários
   . Administrar Usuários
   . Ver Configurações
````


<!--TECNOLOGIAS-->
## Tecnologias
| <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" width="40"/> | <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mysql/mysql-original.svg" width="40"/> | <img src="https://astah.net/wp-content/uploads/2019/07/Astah_blue.svg" width="40"/> | <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/figma/figma-original.svg" width="40"/> |
|--------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------|
| Java                                                                                            | MySQL Workbench                                                                                 | Astah                                                                               | Figma                                                                                      |


<!--PROTÓTIPO-->
## Protótipo
![Protótipos](https://github.com/user-attachments/assets/a9a11550-a11c-46ad-aa05-d608af20d510)


<!--PARTICIPANTES-->
## Participantes
| Nome                            |
|---------------------------------|
| Alvaro Nogueira Junqueira Souza |
| Eike Marchiori Ulinski          |
| Gabriel Cezar Ferrassini        |
| Lucas de Mattia Peres           |
| Victor Hugo Pinho               |


<!--DEPENDÊNCIAS-->
## Dependências
```
JUnit             | 4.11    | Para testes unitários.
MySQL Connector/J | 8.0.19  | Driver JDBC para conexão com MySQL.
JLayer            | 1.0.1   | Para manipulação de arquivos MP3.
MP3SPI            | 1.9.5.4 | Suporte para leitura e escrita de arquivos MP3.
JavaMail API      | 1.6.2   | Para envio e manipulação de e-mails.
```


<!--COMO UTILIZAR-->
## Como Utilizar
```
Requisitos:
   . Java 17 ou superior (JDK)
   . Maven 3.6+ para gerenciamento de dependências
   . MySQL 8.0 ou superior para banco de dados
   . IDE Java (IntelliJ IDEA, Eclipse, VS Code)

Execução:
   1. Clone o repositório                | git clone https://github.com/alvarojnq1/projeto-integrador-2024
   2. Navegue até o diretório do projeto | cd projeto-integrador-2024/sid
   3. Configure o banco MySQL            | Execute os scripts da pasta /db/ na ordem (01 a 06)
   4. Configure credenciais do banco     | Edite ConnectionFactory.java com suas credenciais MySQL
   5. Instale dependências               | mvn clean install
   6. Execute o projeto                  | Compile e execute pela sua IDE
```


<!--CONTRIBUIÇÃO-->
## Contribuição
````
1. Fork               | Crie uma cópia do repositório no seu perfil

2. Clone              | git clone https://github.com/alvarojnq1/projeto-integrador-2024

3. Crie uma Branch    | git checkout -b minha-branch

4. Faça as Alterações | Edite os arquivos e teste.

5. Commit e Push      | git add . 
                      |	git commit -m "Descrição das alterações" 
                      |	git push origin minha-branch

6. Pull Request       | Solicite a inclusão de suas mudanças no repositório original.
````


<!--ESTRUTURA DE PASTAS-->
## Estrutura de Pastas
````
├── db/
│   ├── 01_config.sql
│   ├── 02_create_tables.sql
│   ├── 03_triggers.sql
│   ├── 04_dados_perguntas.sql
│   ├── 05_dados_teste.sql
│   └── 06_finalizacao.sql
├── modelagem/
│   ├── Diagrama_Caso_De_Uso.asta
│   ├── Diagrama_Classe.asta
│   └── Diagrma_Sequência.asta
├── sid/
│ 	├── pom.xml
│	└── src/
│		├── main/
│		│   └── java/
│		│       └── com/
│		│           └── sid/
│		│               ├── Administrar.java
│		│               ├── AdministrarAluno.java
│		│               ├── AdministrarProfessor.java
│		│               ├── AdminPerguntas.java
│		│               ├── AlterarPerguntas.java
│		│               ├── App.java
│		│               ├── Cadastrar.java
│		│               ├── CadastrarAluno.java
│		│               ├── CadastrarProfessor.java
│		│               ├── Configuracoes.java
│		│               ├── ConnectionFactory.java
│		│               ├── CriarPerguntas.java
│		│               ├── EsqueceuSenha.java
│		│               ├── Jogo.java
│		│               ├── MenuAdministrador.java
│		│               ├── MenuAluno.java
│		│               ├── MenuProfessor.java
│		│               ├── Ranking.java
│		│               ├── RedefinirSenha.java
│		│               └── TelaLogin.java
│		└── test/
│			└── java/
│				└── com/
│					└── sid/
│						└── AppTest.java
├── LICENSE
├── README.md
````


<!--ESTATÍSTICAS-->
## Estatísticas 
![](https://visitor-badge.laobi.icu/badge?page_id=alvarojnq1.projeto-integrador-2024)
![Tamanho do Repositório](https://img.shields.io/github/repo-size/alvarojnq1/projeto-integrador-2024)
![Linguagens](https://img.shields.io/github/languages/top/alvarojnq1/projeto-integrador-2024)


<!--LICENÇA-->
## Licença 
[Veja a licença](https://github.com/alvarojnq1/projeto-integrador-2024/blob/main/LICENSE)
