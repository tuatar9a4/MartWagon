package com.devd.domain.model.database

import com.devd.domain.R

enum class PriceUnit(val display: Int, val step: Int) {
    Gram(
        display = R.string.gram_unit,
        step = 100
    ),
    Milliliter(
        display = R.string.milliliter_unit,
        step = 100
    ),
    Piece(
        display = R.string.piece_unit,
        step = 1
    );

}