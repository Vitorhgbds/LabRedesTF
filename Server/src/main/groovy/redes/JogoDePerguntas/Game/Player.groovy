package redes.JogoDePerguntas.Game

import redes.JogoDePerguntas.Game.Questions.Questions

class Player {
    def ponto
    GameMode mode
    Questions question
    boolean finalizado
    LinkedList currentQuestions
     Player(){
        ponto=0;
        currentQuestions = new LinkedList()
        mode = GameMode.FACIL
        question = null
        finalizado=false
    }
}
