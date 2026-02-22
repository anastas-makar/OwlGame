package pro.progr.owlgame.data.repository.impl

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test
import pro.progr.owlgame.data.preferences.OwlPreferences
import pro.progr.owlgame.data.model.GrowthState
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

private const val GROW_DURATION_MILLIS_TEST = 2L * 24 * 60 * 60 * 1000 // 2 дня
private const val COOLING_PERIOD_TEST = 30L * 60 * 1000 // полчаса
private const val FUTURE_TOLERANCE_TEST = 1L * 60 * 1000 // 1 минута

class GrowthRepositoryImplTest {

    @Test
    fun `returns NeedsStart when no last update`() {
        val now = 1_700_000_000_000L
        val prefs = mockk<OwlPreferences>()
        every { prefs.getGrowthUpdate() } returns 0L

        val repo = GrowthRepositoryImpl(
            preferences = prefs,
            clock = fixedClock(now)
        )

        val state = repo.getGrowthState()

        assertEquals(GrowthState.NeedsStart(now), state)
    }

    @Test
    fun `returns NeedsStart when last update is too far in future`() {
        val now = 1_700_000_000_000L
        val prefs = mockk<OwlPreferences>()
        every { prefs.getGrowthUpdate() } returns (now + FUTURE_TOLERANCE_TEST + 1L)

        val repo = GrowthRepositoryImpl(
            preferences = prefs,
            clock = fixedClock(now)
        )

        val state = repo.getGrowthState()

        assertEquals(GrowthState.NeedsStart(now), state)
    }

    @Test
    fun `returns Suspended when last update is within future tolerance`() {
        val now = 1_700_000_000_000L
        val prefs = mockk<OwlPreferences>()
        every { prefs.getGrowthUpdate() } returns (now + FUTURE_TOLERANCE_TEST)

        val repo = GrowthRepositoryImpl(
            preferences = prefs,
            clock = fixedClock(now)
        )

        val state = repo.getGrowthState()

        assertEquals(GrowthState.Suspended, state)
    }

    @Test
    fun `returns Suspended when cooling period not passed`() {
        val now = 1_700_000_000_000L
        val prefs = mockk<OwlPreferences>()
        every { prefs.getGrowthUpdate() } returns (now - COOLING_PERIOD_TEST) // граница, а у тебя строго >

        val repo = GrowthRepositoryImpl(
            preferences = prefs,
            clock = fixedClock(now)
        )

        val state = repo.getGrowthState()

        assertEquals(GrowthState.Suspended, state)
    }

    @Test
    fun `returns Growing when cooling period passed`() {
        val now = 1_700_000_000_000L
        val elapsed = COOLING_PERIOD_TEST + 1L
        val prefs = mockk<OwlPreferences>()
        every { prefs.getGrowthUpdate() } returns (now - elapsed)

        val repo = GrowthRepositoryImpl(
            preferences = prefs,
            clock = fixedClock(now)
        )

        val state = repo.getGrowthState()

        val expectedDelta = elapsed.toFloat() / GROW_DURATION_MILLIS_TEST.toFloat()

        assertEquals(GrowthState.Growing(expectedDelta, now), state)

    }

    @Test
    fun `setGrowthUpdate delegates to preferences`() {
        val prefs = mockk<OwlPreferences>(relaxed = true)
        val repo = GrowthRepositoryImpl(
            preferences = prefs,
            clock = fixedClock(1_700_000_000_000L)
        )

        repo.setGrowthUpdate(123456L)

        verify(exactly = 1) { prefs.setGrowthUpdate(123456L) }
    }

    private fun fixedClock(epochMillis: Long): Clock {
        return Clock.fixed(
            Instant.ofEpochMilli(epochMillis),
            ZoneOffset.UTC
        )
    }
}

private fun fixedClock(epochMillis: Long): Clock =
    Clock.fixed(Instant.ofEpochMilli(epochMillis), ZoneOffset.UTC)

private fun repoWithLast(last: Long, now: Long): GrowthRepositoryImpl {
    val prefs = mockk<OwlPreferences>()
    every { prefs.getGrowthUpdate() } returns last
    return GrowthRepositoryImpl(
        preferences = prefs,
        clock = fixedClock(now)
    )
}

@RunWith(Parameterized::class)
class GrowthRepositoryFutureToleranceBoundaryTest(
    private val futureOffsetMs: Long,
    private val expectedNeedsStart: Boolean
) {

    companion object {
        private const val NOW = 1_700_000_000_000L

        @JvmStatic
        @Parameterized.Parameters(name = "offset={0}, needsStart={1}")
        fun data(): List<Array<Any>> = listOf(
            arrayOf(FUTURE_TOLERANCE_TEST - 1L, false), // в пределах допуска
            arrayOf(FUTURE_TOLERANCE_TEST, false),      // ровно граница допуска
            arrayOf(FUTURE_TOLERANCE_TEST + 1L, true)   // уже слишком далеко в будущем
        )
    }

    @Test
    fun `future tolerance boundary works correctly`() {
        val last = NOW + futureOffsetMs
        val repo = repoWithLast(last = last, now = NOW)

        val state = repo.getGrowthState()

        if (expectedNeedsStart) {
            assertTrue(state is GrowthState.NeedsStart)
            state as GrowthState.NeedsStart
            assertEquals(NOW, state.updateTime)
        } else {
            assertTrue(state is GrowthState.Suspended)
        }
    }
}

@RunWith(Parameterized::class)
class GrowthRepositoryCoolingBoundaryTest(
    private val elapsedMs: Long,
    private val expectedGrowing: Boolean
) {

    companion object {
        private const val NOW = 1_700_000_100_000L

        @JvmStatic
        @Parameterized.Parameters(name = "elapsed={0}, growing={1}")
        fun data(): List<Array<Any>> = listOf(
            arrayOf(COOLING_PERIOD_TEST - 1L, false), // ещё рано
            arrayOf(COOLING_PERIOD_TEST, false),      // ровно граница, у тебя условие строго >
            arrayOf(COOLING_PERIOD_TEST + 1L, true)   // уже можно расти
        )
    }

    @Test
    fun `cooling boundary works correctly`() {
        val last = NOW - elapsedMs
        val repo = repoWithLast(last = last, now = NOW)

        val state = repo.getGrowthState()

        if (expectedGrowing) {
            assertTrue(state is GrowthState.Growing)
            state as GrowthState.Growing

            val expectedDelta = elapsedMs.toFloat() / GROW_DURATION_MILLIS_TEST.toFloat()
            assertEquals(expectedDelta, state.delta, 0.000001f)
            assertEquals(NOW, state.updateTime)
        } else {
            assertTrue(state is GrowthState.Suspended)
        }
    }
}