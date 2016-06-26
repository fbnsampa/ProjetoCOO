# ProjetoCOO
Exercício Programa da disciplina Computação Orientada a Objetos.

  Este projeto consiste em realizar a refatoração do código de um jogo, disponibilizado pelo professor,
de modo a aplicar diversos conceitos estudados na disciplina Computação Orientada
a Objetos. O jogo, trata-se de um shoot 'em up (http://en.wikipedia.org/wiki/Shoot_'em_up) vertical
bastante simples, sem acabamento (não possui tela de título, placar, vidas, fases, chefes, power-ups,
etc) e que roda de forma indefinida (até que o jogador feche a janela do jogo).

  Embora funcione, seu código não foi elaborado seguindo bons princípios de orientação a objetos.
  Apesar de escrito em Java, o código foi elaborado seguindo um estilo de programação estruturada e,
mesmo considerando este estilo, não muito bem feito, com muito código redundante. Existem portanto
inúmeras oportunidades de melhoria do código. Há três principais aspectos que devem ser trabalhados
durante o desenvolvimento deste projeto:

• Aplicação de princípios de orientação a objetos, através da definição de uma boa estrutura de
classes, interfaces e hierarquia de classes/interfaces.
• Uso da API de coleções do Java ao invés de arrays para manter/gerenciar o conjunto de
informações relativas às entidades do jogo (inimigos, projéteis, etc).
• Aplicação de padrões de projeto, com o objetivo de tornar a extensão/manutenção do código
mais fácil e flexível. Devem ser aplicados pelo menos 2 padrões de projeto (observação: o
uso de alguma biblioteca/API do Java que implementa um determinado padrão não conta como
aplicação de um padrão).

  O código do jogo é composto por dois arquivos fonte: Main.java e GameLib.java. No primeiro
arquivo está implementada toda a lógica do jogo, enquanto o segundo implementa uma mini biblioteca
com recursos úteis no desenvolvimento de jogos: inicialização da interface gráfica, desenho de figuras
geométricas e verificação de entrada através do teclado.

  O foco da refatoração do código deve ser a classe Main. Pode-se assumir que a classe GameLib é uma
caixa-preta à qual não se tem acesso ao código-fonte (como se realmente fosse uma biblioteca feita por
terceiros) e portanto ela não precisa ser trabalhada na refatoração, apenas utilizada. Contudo, se houver
vontade ou alguma razão especial para modificá-la, melhorando-a ou aplicando algum padrão de
projeto, ela também pode ser modificada.

  Além da refatoração do código, também deverão ser implementadas algumas funcionalidades extras no
jogo. Descritas abaixo:

  PONTOS DE VIDA DO JOGADOR
  
  Na versão original do jogo, a nave do jogador explode imediatamente ao entrar em contato com um
projétil inimigo ou diretamente com o inimigo. Na versão refatorada deve ser implementado um
esquema de pontos de vida para o jogador. O jogador deve possuir uma certa quantidade de pontos de
vida (definida em arquivo de configuração do jogo – ver mais detalhes adiante) e ao ser atingido por
um projétil (ou inimigo) a quantidade de pontos de vida deve ser reduzida. Pode-se assumir que todos
os projéteis inimigos subtraem 1 ponto de vida do jogador. A nave do jogador só deve explodir quando
a quantidade de pontos de vida chegar a zero. Deve-se implementar também a exibição de uma barra de
vida, ilustrando graficamente quantos pontos de vida o jogador ainda possui.

  CHEFES DE FASE

  Devem ser implementados 2 chefes de fase distintos. Os chefes de fase podem ser considerados
inimigos especiais, que devem possuir as seguintes características particulares:

- Os chefes não podem sair da tela uma vez que tenham entrado na área de jogo (diferentemente dos
inimigos comuns que em algum momento acabam saindo da tela caso não sejam abatidos pelo
jogador).
- Os chefes deve apresentar comportamentos de ataque e movimento diferentes daqueles apresentados
pelos inimigos comuns (e diferentes entre si também). Devem apresentar visual próprio também.
- Os chefes também devem possuir pontos de vida. Quando entrar na área de jogo uma barra de vida
também deve ser exibida para o chefe.
- Deve-se assumir que um chefe aparece uma única vez durante uma fase. Além disso, a derrota de um
chefe implica no avanço para a próxima fase (ver mais detalhes sobre as fases adiante).
Fases (e arquivos de configuração do jogo/fases)

  A última funcionalidade a ser implementada consiste na criação de um mecanismo para a configuração
do jogo e definição de fases. Uma fase nada mais é do que um conjunto de entradas que definem
quando e onde os inimigos devem aparecer, além do tipo. Na definição de cada fase deve-se
obrigatoriamente incluir uma entrada que irá definir a aparição de um chefe. Uma fase é considerada
finalizada quando o chefe é abatido pelo jogador, e deve-se passar para a próxima fase (caso não seja a
última).

  O jogo deve ser configurado através de um arquivo (formato texto) estruturado da seguinte forma:
  
<PONTOS DE VIDA DO JOGADOR>
<NUMERO DE FASES>
<ARQUIVO DE CONFIGURAÇÃO DA FASE 1>
<ARQUIVO DE CONFIGURAÇÃO DA FASE 2>
… <ARQUIVO DE CONFIGURAÇÃO DA FASE N>
onde: PONTOS DE VIDA DO JOGADOR é um valor inteiro que define a quantidade de pontos de vida
com o qual o jogador começa o jogo; NUMERO DE FASES um valor inteiro que define quantas fases o
jogo terá; e as demais linhas definem os nomes de arquivos de configuração para cada uma das fases.
Cada arquivo configuração de fase deve estar estruturado da seguinte forma:

INIMIGO <TIPO> <QUANDO> <POSIÇÃO INICIAL X> <POSIÇÃO INICIAL Y>
INIMIGO <TIPO> <QUANDO> <POSIÇÃO INICIAL X> <POSIÇÃO INICIAL Y>
… INIMIGO <TIPO> <QUANDO> <POSIÇÃO INICIAL X> <POSIÇÃO INICIAL Y>
CHEFE <TIPO> <PONTOS DE VIDA> <QUANDO> <POSIÇÃO INICIAL X> <POSIÇÃO INICIAL Y>
onde: cada linha determina a aparição de um inimigo simples (definida pela palavra-chave INIMIGO)
ou chefe (definida pela palavra-chave CHEFE. Note também que a linha que define a aparição do
chefe deve ocorrer apenas uma vez no arquivo, mas esta linha não precisa ser, obrigatoriamente, a
última); TIPO é um valor inteiro que define o tipo do inimigo ou chefe, e este deve assumir valor 1 ou 2
(afinal, tem-se dois inimigos comuns e deverão ser implementados dois chefes); QUANDO é um valor
inteiro que define o instante em que um inimigo ou chefe deve aparecer na fase (este valor deve ser
definido em milissegundos e relativo ao instante de início da fase); POSIÇÃO X e POSIÇÃO Y são
valores inteiros que definem as coordenadas do ponto no qual o inimigo ou chefe deve aparecer; e
finalmente PONTOS DE VIDA é um valor inteiro que define a quantidade de pontos de vida que o chefe
da fase deve possuir.
