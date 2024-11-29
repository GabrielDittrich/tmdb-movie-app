# Aplicativo Compose Kotlin
<strong>Repositório:</strong> aplicativo-compose-kotlin
<strong>Tecnologias:</strong> Jetpack Compose, Firebase, TMDB API, Kotlin

Este é um projeto desenvolvido como parte da avaliação final da disciplina de Aplicativos Android na Universidade Positivo. O aplicativo foi criado utilizando Jetpack Compose e Kotlin, com integração ao Firebase para funcionalidades de autenticação e à API TMDB para exibição de filmes e séries.

## Descrição
O aplicativo consiste em um catálogo de filmes e séries, onde os usuários podem realizar o cadastro e login utilizando o Firebase. Após o login, é possível acessar uma tela inicial com recomendações de filmes e séries, realizar buscas e navegar entre diferentes seções do aplicativo.

### Funcionalidades principais:
- Cadastro e login: Implementado com Firebase Authentication, permitindo que os usuários se cadastrem e façam login no aplicativo.
- Integração com a API TMDB: O aplicativo exibe uma lista de filmes e séries com base nas informações obtidas da TMDB API.
- Pesquisa: Implementada a funcionalidade de busca de filmes e séries, porém, a busca ainda não está totalmente funcional.
- Navegação: Utiliza o Jetpack Navigation para gerenciar as diferentes telas e rotas do aplicativo.
- BottomNavigationBar: Permite a navegação entre as principais telas do aplicativo: HomeScreen, SearchScreen, SeriesScreen, e ProfileScreen.

### Estrutura de Navegação
A navegação no aplicativo é estruturada da seguinte forma:

- MainActivity: A atividade principal do aplicativo.
- FilmeNavigation: A primeira navegação onde, se o usuário não estiver logado, ele é redirecionado para a tela de login/cadastro. Caso contrário, ele é direcionado para a HomeScreen.
- HomeNavigation: Após o login, a navegação dentro da tela inicial acontece aqui, onde o usuário pode explorar filmes e séries.

### As telas são organizadas da seguinte forma:

1. MainActivity -> FilmeNavigation (verifica se está logado, redireciona para a tela de login ou para a HomeScreen)
2. HomeScreen: Tela principal do aplicativo com o catálogo de filmes e séries.
3. SearchScreen: Tela de pesquisa (ainda em desenvolvimento).
4. SeriesScreen: Tela dedicada a séries.
5. ProfileScreen: Tela de perfil do usuário.

### Tecnologias Utilizadas
- Jetpack Compose: Framework moderno para criação de interfaces no Android, utilizado para todas as telas e componentes de UI.
- Firebase: Utilizado para autenticação de usuários (login e cadastro).
- TMDB API: API externa utilizada para obter informações sobre filmes e séries.
- Navigation Component: Para gerenciamento das rotas e navegação entre as telas do aplicativo.

### Instruções de Uso
- Pré-requisitos
- Android Studio instalado.
- JDK 11 ou superior.
- Conta do Firebase configurada.

### Passos para rodar o projeto:
1. Clone este repositório para o seu ambiente local:
    ```
    git clone https://github.com/seu-usuario/aplicativo-compose-kotlin.git

2. Abra o projeto no Android Studio.

3. Configure o Firebase no seu projeto:
- Crie um projeto no Firebase Console.
- Adicione o seu app Android no Firebase e baixe o arquivo google-services.json.
- Coloque o arquivo google-services.json na pasta app/ do projeto.

4. Sincronize o projeto com o Gradle.
5. Execute o projeto em um emulador ou dispositivo Android.

### Melhorias Futuras
Finalizar a implementação da funcionalidade de pesquisa de filmes e séries.
Melhorar o design e a experiência do usuário nas telas.
Adicionar mais funcionalidades de perfil, como edição de dados do usuário.
