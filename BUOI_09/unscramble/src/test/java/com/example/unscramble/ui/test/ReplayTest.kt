package com.example.unscramble.ui.test
import com.example.unscramble.ui.GameViewModel
import com.example.unscramble.data.MAX_NO_OF_WORDS
import org.junit.Assert.*
import org.junit.Test
class ReplayTest {
    @Test fun skipToEndAndReplayResetsInputAndScore() {
        val model=GameViewModel()
        repeat(MAX_NO_OF_WORDS) { model.skipWord() }
        assertTrue(model.uiState.value.isGameOver)
        val finished=model.uiState.value
        model.skipWord()
        model.updateUserGuess("wrong")
        model.checkUserGuess()
        assertEquals(finished,model.uiState.value)
        model.resetGame()
        assertFalse(model.uiState.value.isGameOver)
        assertEquals(0,model.uiState.value.score)
        assertEquals(1,model.uiState.value.currentWordCount)
        assertEquals("",model.userGuess)
    }
}
