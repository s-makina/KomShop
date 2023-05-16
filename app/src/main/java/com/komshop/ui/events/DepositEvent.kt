package com.komshop.ui.events

sealed class DepositEvent {
    object OnLoadDeposits: DepositEvent()
    class OnChangeDeposit(val deposit: String): DepositEvent()
    class OnSelectAuctionType(val auctionType: String): DepositEvent()
    class OnDeleteDeposit(val deposit: String): DepositEvent()
    object OnAddAnother: DepositEvent()
    object OnSubmit: DepositEvent()
    object OnCompleted: DepositEvent()
    object Reset: DepositEvent()
}