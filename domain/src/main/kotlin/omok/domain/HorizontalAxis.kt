package omok.domain

enum class HorizontalAxis(val axis: Int) {
    A(1),
    B(2),
    C(3),
    D(4),
    E(5),
    F(6),
    G(7),
    H(8),
    I(9),
    J(10),
    K(11),
    L(12),
    M(13),
    N(14),
    O(15);

    companion object {
        private const val HORIZONTAL_AXIS_ERROR_MSG = "일치하는 값이 없습니다."

        fun getHorizontalAxis(other: Int): HorizontalAxis {
            return HorizontalAxis.values().find { horizontalAxis ->
                horizontalAxis.axis == other
            } ?: throw IllegalStateException(HORIZONTAL_AXIS_ERROR_MSG)
        }
    }
}
