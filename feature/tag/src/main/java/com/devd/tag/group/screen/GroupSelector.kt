package com.devd.tag.group.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devd.common.theme.ColorDeepDisable
import com.devd.common.theme.ColorMainText
import com.devd.common.theme.ColorSecondaryText
import com.devd.common.theme.ColorWhite
import com.devd.domain.model.group.GroupType
import timber.log.Timber

@Preview
@Composable
fun GroupSelectorPreview() {
    var index by remember { mutableStateOf(GroupType.MART) }
    Column() {
        Spacer(Modifier.height(100.dp))
        GroupSelector(
            selectedIndex = index,
            changeGroup = { index = GroupType.entries[it] }
        )
    }
}

@Composable
fun GroupSelector(
    selectedIndex: GroupType,
    changeGroup: (Int) -> Unit
) {
    Timber.d("CheckIndex selectedIndex=> ${selectedIndex}")

    SingleChoiceSegmentedButtonRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(ColorDeepDisable, RoundedCornerShape(15.dp))
            .padding(5.dp)
    ) {
        listOf("마트별", "태그별").forEachIndexed { index, label ->
            SegmentedButton(
                shape = RoundedCornerShape(15.dp),
                onClick = {
                    Timber.d("CheckIndex changeGroup=> ${index}")
                    changeGroup(index)
                },
                selected = index == selectedIndex.index,
                border = BorderStroke(0.dp, ColorDeepDisable),
                contentPadding = PaddingValues(vertical = 15.dp),
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = ColorWhite,
                    activeContentColor = ColorMainText,
                    inactiveContainerColor = ColorDeepDisable,
                    inactiveContentColor = ColorSecondaryText
                ),
                icon = {}
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium.copy()
                )
            }
        }
    }
}