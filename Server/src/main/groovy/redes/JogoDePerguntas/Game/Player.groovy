package redes.JogoDePerguntas.Game

import redes.JogoDePerguntas.Game.Questions.Questions
/*
    Essa classe representa o jogador, a pontuaçao, a lista de perguntas, o modo de jogo que ele
     escolheu, a questão atual e se ele já finalizou o jogo
*/

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
