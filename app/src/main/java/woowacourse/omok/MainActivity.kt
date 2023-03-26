package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.domain.BlackStone
import omok.domain.Board
import omok.domain.HorizontalAxis
import omok.domain.Player
import omok.domain.Position
import omok.domain.Stone
import omok.domain.WhiteStone
import omok.domain.judgement.LineJudgement
import omok.domain.state.State
import omok.domain.state.Turn
import omok.domain.state.Win
import woowacourse.omok.controller.InitController
import woowacourse.omok.database.DBController
import woowacourse.omok.database.OmokConstract
import woowacourse.omok.database.OmokDBHelper

class MainActivity : AppCompatActivity() {
    private val omokBoard = Board(Player(), Player())
    private lateinit var dBController: DBController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val board = findViewById<TableLayout>(R.id.board)
        dBController = DBController(OmokDBHelper(this).writableDatabase)

        InitController(omokBoard, board, dBController).initBoard()
        turn(Turn.Black, board)
    }

    private fun turn(state: State, board: TableLayout) {
        when (state) {
            Turn.Black -> putBoard(state, board)
            Turn.White -> putBoard(state, board)
            Win.Black -> goWinActivity(state as Win)
            Win.White -> goWinActivity(state as Win)
        }
    }

    private fun putBoard(turn: State, board: TableLayout) {
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                setOnClickListener(view, index, turn, board)
            }
    }

    private fun setOnClickListener(view: ImageView, index: Int, turn: State, board: TableLayout) {
        view.setOnClickListener {
            when (turn as Turn) {
                Turn.Black -> blackTurn(index, view, board)
                Turn.White -> whiteTurn(index, view, board)
            }
        }
    }

    private fun blackTurn(index: Int, view: ImageView, board: TableLayout) {
        val position = positionFind(index)
        if (!omokBoard.isBlackPlaceable(position)) {
            Toast.makeText(applicationContext, BOARD_PUT_ERROR_MESSAGE, Toast.LENGTH_SHORT).show()
            turn(Turn.Black, board)
        }
        if (omokBoard.isBlackPlaceable(position)) {
            dBController.insertDB(Turn.Black.color, index)
            view.setImageResource(R.drawable.black_stone)
            omokBoard.blackPlayer.put(BlackStone(position))
            omokBoard.occupyPosition(position)
            nextStepBlack(position, board)
        }
    }

    private fun nextStepBlack(position: Position, board: TableLayout) {
        if (lineJudge(omokBoard.blackPlayer, BlackStone(position))) {
            turn(Win.Black, board)
        }
        turn(Turn.White, board)
    }

    private fun whiteTurn(index: Int, view: ImageView, board: TableLayout) {
        val position = positionFind(index)
        if (!omokBoard.isWhitePlaceable(position)) {
            Toast.makeText(applicationContext, BOARD_PUT_ERROR_MESSAGE, Toast.LENGTH_SHORT).show()
            turn(Turn.White, board)
        }
        if (omokBoard.isWhitePlaceable(position)) {
            dBController.insertDB(Turn.White.color, index)
            view.setImageResource(R.drawable.white_stone)
            omokBoard.whitePlayer.put(WhiteStone(position))
            omokBoard.occupyPosition(position)
            nextStepWhite(position, board)
        }
    }

    private fun nextStepWhite(position: Position, board: TableLayout) {
        if (lineJudge(omokBoard.whitePlayer, WhiteStone(position))) {
            turn(Win.White, board)
        }
        turn(Turn.Black, board)
    }

    private fun lineJudge(player: Player, stone: Stone) = LineJudgement(player, stone).check()

    private fun goWinActivity(state: Win) {
        val color = when (state) {
            Win.Black -> Turn.Black.color
            Win.White -> Turn.White.color
        }
        val intent = Intent(this, WinActivity::class.java)
        intent.putExtra(OmokConstract.TABLE_COLUMN_COLOR, color)
        startActivity(intent)
    }

    override fun onDestroy() {
        dBController.closeDB()
        super.onDestroy()
    }

    companion object {
        private const val POSITION_CALCULATE_PLUS_NUMBER = 1
        private const val BOARD_PUT_ERROR_MESSAGE = "해당 자리에 둘 수 없습니다."
        fun positionFind(index: Int): Position {
            val x = (index % Position.MAX_VERTICAL_AXIS) + POSITION_CALCULATE_PLUS_NUMBER
            val y = Position.MAX_VERTICAL_AXIS - index / Position.MAX_VERTICAL_AXIS
            return Position(HorizontalAxis.getHorizontalAxis(x), y)
        }
    }
}
