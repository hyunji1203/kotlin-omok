package omok.domain.judgement

import omok.domain.HorizontalAxis
import omok.domain.Player
import omok.domain.Position
import omok.domain.WhiteStone
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

class LineJudgementTest {
    @Test
    fun `가로축에서 연속된 돌의 갯수가 5개이면 True를 반환한다`() {
        // given
        val player = Player()
        val position = Position(HorizontalAxis.E, 3)
        val lineJudgement = LineJudgement(player, WhiteStone(position))

        // when
        player.put(WhiteStone(Position(HorizontalAxis.B, 3)))
        player.put(WhiteStone(Position(HorizontalAxis.C, 3)))
        player.put(WhiteStone(Position(HorizontalAxis.D, 3)))
        player.put(WhiteStone(Position(HorizontalAxis.E, 3)))
        player.put(WhiteStone(Position(HorizontalAxis.F, 3)))

        // then
        assertThat(lineJudgement.check()).isTrue
    }

    @Test
    fun `주 대각선 위에서 연속된 돌의 갯수가 5개이면 True를 반환한다`() {
        // given
        val player = Player()
        val position = Position(HorizontalAxis.B, 7)
        val lineJudgement = LineJudgement(player, WhiteStone(position))

        // when
        player.put(WhiteStone(Position(HorizontalAxis.A, 6)))
        player.put(WhiteStone(Position(HorizontalAxis.B, 7)))
        player.put(WhiteStone(Position(HorizontalAxis.C, 8)))
        player.put(WhiteStone(Position(HorizontalAxis.D, 9)))
        player.put(WhiteStone(Position(HorizontalAxis.E, 10)))

        // then
        assertThat(lineJudgement.check()).isTrue
    }

    @Test
    fun `주 대각선 아래에서 연속된 돌의 갯수가 5개이면 True를 반환한다`() {
        // given
        val player = Player()
        val position = Position(HorizontalAxis.L, 5)
        val lineJudgement = LineJudgement(player, WhiteStone(position))

        // when
        player.put(WhiteStone(Position(HorizontalAxis.I, 2)))
        player.put(WhiteStone(Position(HorizontalAxis.J, 3)))
        player.put(WhiteStone(Position(HorizontalAxis.K, 4)))
        player.put(WhiteStone(Position(HorizontalAxis.L, 5)))
        player.put(WhiteStone(Position(HorizontalAxis.M, 6)))

        // then
        assertThat(lineJudgement.check()).isTrue
    }

    @Test
    fun `부 대각선 위에서 연속된 돌의 갯수가 5개이면 True를 반환한다`() {
        // given
        val player = Player()
        val position = Position(HorizontalAxis.K, 10)
        val lineJudgement = LineJudgement(player, WhiteStone(position))

        // when
        player.put(WhiteStone(Position(HorizontalAxis.M, 8)))
        player.put(WhiteStone(Position(HorizontalAxis.L, 9)))
        player.put(WhiteStone(Position(HorizontalAxis.K, 10)))
        player.put(WhiteStone(Position(HorizontalAxis.J, 11)))
        player.put(WhiteStone(Position(HorizontalAxis.I, 12)))

        // then
        assertThat(lineJudgement.check()).isTrue
    }

    @Test
    fun `부 대각선 아래에서 연속된 돌의 갯수가 5개이면 True를 반환한다`() {
        // given
        val player = Player()
        val position = Position(HorizontalAxis.G, 4)
        val lineJudgement = LineJudgement(player, WhiteStone(position))

        // when
        player.put(WhiteStone(Position(HorizontalAxis.H, 3)))
        player.put(WhiteStone(Position(HorizontalAxis.G, 4)))
        player.put(WhiteStone(Position(HorizontalAxis.F, 5)))
        player.put(WhiteStone(Position(HorizontalAxis.E, 6)))
        player.put(WhiteStone(Position(HorizontalAxis.D, 7)))

        // then
        assertThat(lineJudgement.check()).isTrue
    }
}
