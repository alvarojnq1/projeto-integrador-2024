<!--TÍTULO-->
# Jogo Sistema Digestório⠀<img src="https://cdn-icons-png.flaticon.com/128/5601/5601041.png" height="30px" alt="Corpo">


<!--DESCRIÇÃO-->
> Este projeto combina aprendizado e diversão por meio de um jogo interativo e educativo. O objetivo principal é ensinar conteúdos relacionados ao sistema digestório humano, utilizando um ambiente gamificado que incentiva o engajamento do usuário. <br/><br/>
> O jogo consiste em um quiz com perguntas geradas aleatoriamente, no qual os jogadores devem responder corretamente o maior número possível de questões para superar seus próprios recordes e competir com outros usuários.


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
1. Clone o repositório                     | git clone https://github.com/alvarojnq1/projeto-integrador-2024

2. Navegue até o diretório do projeto      | cd projeto-integrador-2024

3. Configure o banco de dados MySQL        | Configure as tabelas e dados necessários usando os scripts fornecidos.

4. Altere credenciais de conexão no código | Edite o arquivo responsável pela conexão ao banco e insira suas credenciais.

5. Instale dependências                    | mvn install

6. Compile o projeto na sua IDE            | Utilize sua IDE para compilar o projeto.

7. Execute o aplicativo                    | Use a opção de execução na sua IDE.
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

<!--LICENÇA-->
## Licença 
[Veja a licença](https://github.com/alvarojnq1/projeto-integrador-2024/blob/main/LICENSE)


<!--ESTRUTURA DE PASTAS-->
## Estrutura de Pastas
````
├── Documentos/
│   ├── 1. Oficial/
│   │   └── PI Documentação SID.docx
│   ├── 2. Apresentação/
│   │   └── SID - Apresentação - PowerPoint.pptx
│   ├── 3. Telas/
│   │   ├── 0. Login/
│   │   ├── 1. Aluno/
│   │   ├── 2. Professor/
│   │   ├── 3. Adm/
│   │   │   └── adm.txt
│   │   └── 4. Ranking e Configurações/
│   └── 4. Modelagem/
│       ├── Diagrama de Caso de Uso.asta
│       ├── Diagrama de Classe.asta
│       └── Diagrma de Sequência.asta
├── sid/
│	├── pom.xml
│	├── src/
│	│   ├── main/
│	│   │   ├── java/
│	│   │   │   └── com/
│	│   │   │       └── sid/
│	│   │   │           ├── AdminPerguntas.java
│	│   │   │           ├── Administrar.java
│	│   │   │           ├── AdministrarAluno.java
│	│   │   │           ├── AdministrarProfessor.java
│	│   │   │           ├── AlterarPerguntas.java
│	│   │   │           ├── App.java
│	│   │   │           ├── Cadastrar.java
│	│   │   │           ├── CadastrarAluno.java
│	│   │   │           ├── CadastrarProfessor.java
│	│   │   │           ├── Configuracoes.java
│	│   │   │           ├── ConnectionFactory.java
│	│   │   │           ├── CriarPerguntas.java
│	│   │   │           ├── EsqueceuSenha.java
│	│   │   │           ├── Jogo.java
│	│   │   │           ├── MenuAdministrador.java
│	│   │   │           ├── MenuAluno.java
│	│   │   │           ├── MenuProfessor.java
│	│   │   │           ├── Ranking.java
│	│   │   │           ├── RedefinirSenha.java
│	│   │   │           └── TelaLogin.java
│	│   │   └── resources/
│	│   │       └── images/
│	│   └── test/
│	│       └── java/
│	│           └── com/
│	│               └── sid/
│	│                   └── AppTest.java
│	└── target/
│		├── classes/
│		│   ├── com/
│		│   │   └── sid/
│		│   └── images/
│		├── maven-status/
│		│   └── maven-compiler-plugin/
│		│       └── compile/
│		│           └── default-compile/
│		│               ├── createdFiles.lst
│		│               └── inputFiles.lst
│		└── test-classes/
│			└── com/
│				└── sid/
├── LICENSE
├── PI.sql
├── README.md
````

<!--ESTATÍSTICAS-->
## Estatísticas 
![](https://visitor-badge.laobi.icu/badge?page_id=alvarojnq1.projeto-integrador-2024)
![Tamanho do Repositório](https://img.shields.io/github/repo-size/alvarojnq1/projeto-integrador-2024)
![Linguagens](https://img.shields.io/github/languages/top/alvarojnq1/projeto-integrador-2024)
