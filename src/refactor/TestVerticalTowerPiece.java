package refactor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import refactor.pieces.VerticalTowerPiece;

public class TestVerticalTowerPiece {
    @Test
    public void test_instaciation(){
        VerticalTowerPiece tower = new VerticalTowerPiece("Torre Test",
                new Team(Team.WHITE_TEAM_ID, Team.WHITE_TEAM_NAME));

        Assertions.assertEquals("Torre Test", tower.getNickname());
        Assertions.assertEquals(Team.WHITE_TEAM_ID, tower.getTeamId());
        Assertions.assertEquals(true, tower.getIsDefeated());
        Assertions.assertEquals(6, tower.getId());
        Assertions.assertEquals("TorreVert", tower.getTypeName());
        Assertions.assertNull(tower.getMovementLimit());
    }
}
