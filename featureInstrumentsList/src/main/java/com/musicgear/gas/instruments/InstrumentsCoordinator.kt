package com.musicgear.gas.instruments

import android.view.View
import com.musicgear.gas.instruments.master.InstrumentsView

interface InstrumentsCoordinator {
  fun goToItemDetails(
    instrument: InstrumentsView.Displayable.DisplayableInstrument,
    sharedViews: Pair<View, String>?
  )

  fun goToItemDetails(
    instrumentId: Int,
    sharedViews: Pair<View, String>?
  )
}