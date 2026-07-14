package pro.progr.owlgame.presentation.ui.quest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.R
import pro.progr.owlgame.presentation.ui.model.QuestRewardPrompt

@Composable
fun QuestCompletedContent(
    rewardPrompt: QuestRewardPrompt?,
    isClaimingLoot: Boolean,
    onClaimLoot: () -> Unit,
    onBackToLocation: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.quest_completed_title),
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = if (rewardPrompt != null) {
                stringResource(R.string.quest_completed_with_loot_message)
            } else {
                stringResource(R.string.quest_completed_without_loot_message)
            },
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )

        Spacer(Modifier.height(20.dp))

        if (rewardPrompt != null) {
            Button(
                onClick = onClaimLoot,
                enabled = !isClaimingLoot,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isClaimingLoot) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colors.onPrimary
                    )
                } else {
                    Text(
                        text = rewardPrompt.buttonText
                            ?: stringResource(R.string.claim_quest_loot)
                    )
                }
            }

            Spacer(Modifier.height(8.dp))
        }

        TextButton(
            onClick = onBackToLocation,
            enabled = !isClaimingLoot
        ) {
            Text(stringResource(R.string.back_to_location))
        }
    }
}