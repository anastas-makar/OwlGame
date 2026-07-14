package pro.progr.owlgame.presentation.ui.quest

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.R
import pro.progr.owlgame.presentation.ui.map.LootReceivedDialog
import pro.progr.owlgame.presentation.viewmodel.QuestViewModel

@Composable
fun QuestScreen(
    navController: NavHostController,
    questViewModel: QuestViewModel,
    diamondDao: PurchaseInterface
) {
    val state by questViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            questViewModel.clearError()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                snackbarHostState,
                modifier = Modifier.navigationBarsPadding()
            )
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(state.questTitle ?: stringResource(R.string.quest))
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        }
    ) { innerPadding ->
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.isQuestCompleted -> {
                QuestCompletedContent(
                    rewardPrompt = state.rewardPrompt,
                    isClaimingLoot = state.isClaimingLoot,
                    onClaimLoot = {
                        questViewModel.claimQuestLoot(diamondDao)
                    },
                    onBackToLocation = {
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                )
            }

            state.currentPage == null -> {
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(stringResource(R.string.quest_page_not_found))
                }
            }

            else -> {
                QuestPageContent(
                    page = state.currentPage!!,
                    isCompleting = state.isCompleting,
                    onOptionClick = questViewModel::chooseOption,
                    onCompleteClick = questViewModel::completeQuest,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                )
            }
        }
    }

    state.claimedLoot?.let { loot ->
        LootReceivedDialog(
            loot = loot,
            onDismiss = {
                questViewModel.closeClaimedLootDialog()
                navController.popBackStack()
            }
        )
    }
}