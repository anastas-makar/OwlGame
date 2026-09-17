package pro.progr.owlgame.presentation.ui.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.EnemyModel
import pro.progr.owlgame.domain.model.ExpeditionWithDataModel
import pro.progr.owlgame.R

@Composable
fun BattleHeaderRow(
    animal: AnimalModel?,
    expedition: ExpeditionWithDataModel?,
    activeEnemy: EnemyModel?,
    onAnimalClick: () -> Unit,
    onActiveEnemyClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        BattleParticipantCard(
            modifier = Modifier.weight(1f),
            title = animal?.let { "${it.kind} ${it.name}" }
                ?: stringResource(R.string.animal),
            imageUrl = animal?.imagePath,
            heal = expedition?.healAmount,
            maxHeal = expedition?.maxHealAmount,
            damage = expedition?.damageAmount,
            maxDamage = expedition?.maxDamageAmount,
            subtitle = stringResource(R.string.your_fighter),
            dimmed = false,
            onClick = onAnimalClick
        )

        activeEnemy?.let { enemy ->
            BattleParticipantCard(
                modifier = Modifier.weight(1f),
                title = enemy.name,
                imageUrl = enemy.imageUrl,
                heal = enemy.healAmount,
                maxHeal = enemy.maxHealAmount,
                damage = enemy.damageAmount,
                maxDamage = enemy.maxDamageAmount,
                subtitle = stringResource(R.string.active_enemy),
                dimmed = false,
                onClick = onActiveEnemyClick
            )
        } ?: Spacer(modifier = Modifier.weight(1f))
    }
}
