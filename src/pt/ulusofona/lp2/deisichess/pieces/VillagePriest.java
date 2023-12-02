package pt.ulusofona.lp2.deisichess.pieces;

import pt.ulusofona.lp2.deisichess.Board;
import pt.ulusofona.lp2.deisichess.ChessPiece;
import pt.ulusofona.lp2.deisichess.GameStaticData;

public class VillagePriest extends ChessPiece {

    public VillagePriest(int teamID){ super(teamID); }

    public VillagePriest(String nickame, int teamID) {
        super(nickame, teamID);
        this.id = GameStaticData.VILLAGE_PRIEST_PIECE_ID;
        this.typeName = GameStaticData.VILLAGE_PRIEST_NAME;
        this.value = GameStaticData.VILLAGE_PRIEST_PIECE_VALUE;
    }

    @Override
    public boolean tryMove(Board board, int x, int y) {
        if(!board.isValidPosition(x, y)){
            return false;
        }

        // invalid move: move VillagePriest restriction
        //se (3,3) para a frente direita até (6,6): true
        //se (3,3) para a frente e esquerda até (6,0): true
        //se (3,3) para trás e direita até (0,6): true
        //se (3,3) para trás e esqquerda até (0,0): true

        /*
         public static boolean validarMovimento(int origemX, int origemY, int destinoX, int destinoY) {
            int diferencaX = Math.abs(destinoX - origemX);
            int diferencaY = Math.abs(destinoY - origemY);

            return diferencaX == diferencaY && diferencaX <= 3;
        }

        public static void main(String[] args) {
            // Testando os movimentos
            System.out.println(validarMovimento(3, 3, 6, 6)); // Para frente direita: true
            System.out.println(validarMovimento(3, 3, 6, 0)); // Para frente esquerda: true
            System.out.println(validarMovimento(3, 3, 0, 6)); // Para trás direita: true
            System.out.println(validarMovimento(3, 3, 0, 0)); // Para trás esquerda: true
        }
             */
        return false;
    }
}
