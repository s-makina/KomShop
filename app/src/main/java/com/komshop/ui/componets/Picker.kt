package com.komshop.ui.componets

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.maxkeppeker.sheets.core.models.base.SheetState
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerUi(dateState: SheetState = rememberSheetState()) {
    val picker = rememberDatePickerState()
    Card() {
//        Date
//        DatePicker(datePickerState = picker, title = { Text(text = "SELE")}, se)
    }

    val selectedDates = remember { mutableStateOf<List<LocalDate>>(listOf()) }


    CalendarDialog(
        state = dateState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH,
//            disabledDates = disabledDates
        ),
        selection = CalendarSelection.Dates { newDates ->
            selectedDates.value = newDates
        }
    )


//    picker.yearsRange
}