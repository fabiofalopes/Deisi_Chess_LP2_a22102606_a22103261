# Notas(mover isto e imagens para /notas/) do [video](https://www.youtube.com/watch?v=EMKTKl4lZXI)

Run/Debug Configurations -> Add new -> Applications 
	- Name: AppLauncher
	- Main Class: pt.ulusofona.lp2.guiSimulator.AppLauncher

- Só usamos 1 tipo de peça na primeira parte: **Rei**
	![](Pasted image 20231021181530.png?raw=true "img1")
	Move-se para os lados e na diagonal 1 quadrado. 

- A 2ª parte vai ter mais tipos 
- A peça preta é sempre a primeira a mover-se
- Deve prevenir moves inválidos:
	- Em cada turno temos que alternar o jogador
- Quando uma peça se move para cima da outra, 'come' ou captura a outra peça 

![[Pasted image 20231021182008.png]]

#### Classes:

GameManager

ChessPiece
Square
Board



![[Pasted image 20231021180307.png]]

Load do 4x4.txt
![[Pasted image 20231021183840.png]]
##### API do GameManager

| Assinatura  | 
|-------------|
| boolean loadGame(File file) | 
| int getBoardSize() | 
| boolean move(int x0, int y0, int x1, int y1) |  
| String[] getSquareInfo(int x, int y) |
| String[] getPieceInfo(int ID) | 
| String getPieceInfoAsString(int ID) | 
| int getCurrentTeamID() | 
| boolean gameOver() | 
| JPanel getAuthorsPanel() |  
| ArrayList<[[String]]> getGameResults() | 

### boolean loadGame(File file)

O jogo quando inicia vai ler de um ficheiro .txt que nos vai indicar:
- o tamanho do tabuleiro 
- quais as peças e as posições delas 

Tem que inicializar o tabuleiro. 

Toda a informação do ficheiro tem que ser processada para então se inicializar as classes do jogo. Preenche informação dentro do GameManager para podermos interagir com os seguintes métodos 

### int getBoardSize()

Devolve a informação que está no ficheiro sobre o tamanho do tabuleiro.

### String[] getSquareInfo(int x, int y);

x, y -> getSquareInfo() -> [id , tipo , equipa , alcunha, png]

Ficheiro PNG (opcional)
- png com 50x50 
- fundo transparente
- src/images

Em cada jogada a aplicação percorre cada square e chamado este método para para obter as peças.
### getCurrentTeamID()

Controla a secção da interface gráfica que contem:
	Equipa a jogar <[PRETA, BRANCA]>

0 -> Preta a Jogar
1 -> Branca a Jogar

### String[] getPieceInfo(int ID)

ID -> getPieceInfo() -> [id , tipo , equipa , alcunha , estado , x , y ]

Estado é uma string que pode conter os valores:
- em jogo
- capturado

### String getPieceInfoAsString(int ID)

Recebe ID da peça e retorna uma string com o seguinte formato: 

id | tipo | equipa | alcunha @(x,y)

Exemplo: 1 | 0 | 0 | Chefe @(1,0)

### boolean move(int x0, int y0, int x1, int y1)

A interface gráfica carrega contem elementos:
- De: (a,b)
- Para: (c,d)
onde quando clicamos o botão **mover**, o programa chama a nossa classe ``GameManager.move(a,b,c,d)``



### ArrayList<[String](app://obsidian.md/String)> getGameResults()

Após o gameOver() devolver true, é chamado este método para a janela de estatísticas do jogo.

Vai retornar um array de strings em que cada string representa 1 linha do array.  

A ArrayList de Strings contem 
[0] - "JOGO DE CRAZY CHESS"
[1] - "Resultado: < RES >"
...

Vareaveís para acessar:
- RES
	- "VENCERAM AS BRANCAS"
	- "VENCERAM AS PRETAS"
	- "EMPATE"
- NR CAPTURAS (de cada)
- NR JOGADAS VALIDAS (de cada)
- NR TENTATIVAS INVALIDAS (de cada)

```
JOGO DE CRAZY CHESS
Resultado: <RES>
---
Equipa das Pretas
<NR CAPTURAS>
<NR JOGADAS VALIDAS>
<NR TENTATIVAS INVALIDAS>
Equipa das Brancas
<NR CAPTURAS>
<NR JOGADAS VALIDAS>
<NR TENTATIVAS INVALIDAS>
```


### JPanel getAuthorsPanel() 

Metodo chamado quando se click no botão créditos da interface gráfica e retorna uma JPanel 

JPanel é uma classe gráfica costumisavel.

