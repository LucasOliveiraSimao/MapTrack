# MapTrack (em desenvolvimento)

Este é um aplicativo Android em desenvolvimento que permitirá ao usuário registrar seu deslocamento em tempo real e visualizar rotas anteriores em um mapa interativo.

## Tecnologias Utilizadas:
O aplicativo está sendo desenvolvido utilizando as seguintes tecnologias:

**Kotlin** como linguagem de programação principal.

**LiveData** e **ViewModel** para gerenciamento de dados e comunicação entre componentes.

**Service** para execução em segundo plano do registro de localização do usuário.

**Room Persistence Library** para armazenar as informações de localização do usuário em um banco de dados SQLite.

**MPAndroidChart** para apresentação de gráficos com informações de deslocamento.

**Dagger Hilt** como framework para injeção de dependências.

**Google Maps** para desenhar o mapa e as rotas do usuário.

**Glide** para carregar imagens de forma eficiente.

**Navigation** para navegação entre telas do aplicativo.

**Coroutines** para execução de tarefas assíncronas de forma simplificada.

## Arquitetura Utilizada:
O aplicativo segue o padrão de arquitetura MVVM (Model-View-ViewModel), que separa a lógica de negócios da interface do usuário. A arquitetura MVVM é recomendada para aplicativos Android devido à sua facilidade de teste, modularidade e escalabilidade.

## Recursos de Funcionalidade (planejados):
O aplicativo terá os seguintes recursos de funcionalidade:

Registro de deslocamento em tempo real, que poderá ser feito em background.

Visualização de rotas anteriores do usuário em um mapa interativo.

Estatísticas sobre as atividades de deslocamento do usuário, incluindo distância percorrida e tempo gasto em movimento.

Apresentação de gráficos com informações de deslocamento.

## Como Contribuir
Se você quiser contribuir para o desenvolvimento deste aplicativo, sinta-se à vontade para enviar pull requests ou issues. Todas as contribuições são bem-vindas! Por favor, esteja ciente de que este aplicativo está em desenvolvimento e pode estar sujeito a mudanças significativas no futuro.
