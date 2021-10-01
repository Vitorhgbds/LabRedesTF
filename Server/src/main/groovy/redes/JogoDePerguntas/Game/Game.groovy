package redes.JogoDePerguntas.Game

import redes.JogoDePerguntas.Game.Questions.QuestionRepository
import redes.JogoDePerguntas.Game.Questions.Questions

class Game {
    def static menu
    def static melhorPontuacao = 0
    def static ponto
    static LinkedList repositoryQuestion
    static GameMode gameMode
    static Questions question
    static boolean finalizado
    def static Start(){
        repositoryQuestion = null;
        finalizado =false
        if (gameMode == GameMode.FACIL){
            repositoryQuestion = QuestionRepository.CreateQuestionsFacil()
        }
        if (gameMode == GameMode.MEDIO){
            repositoryQuestion = QuestionRepository.CreateQuestionsMedio()
        }
        if (gameMode == GameMode.DIFICIL){
            repositoryQuestion = QuestionRepository.CreateQuestionsDificil()
        }
        ponto=0
    }
    def static Menu(){
         menu = "  _______ _             _____                      \n" +
                " |__   __| |           / ____|                     \n" +
                "    | |  | |__   ___  | |  __  __ _ _ __ ___   ___ \n" +
                "    | |  | '_ \\ / _ \\ | | |_ |/ _` | '_ ` _ \\ / _ \\\n" +
                "    | |  | | | |  __/ | |__| | (_| | | | | | |  __/\n" +
                "    |_|  |_| |_|\\___|  \\_____|\\__,_|_| |_| |_|\\___|\n" +
                "                                                   \n" +
                "==========================================================\n" +
                "                   1. Jogo Facil\n" +
                "                   2. Jogo Medio\n" +
                "                   3. Jogo Dificil\n" +
                "                   Maior pontuacao:"+melhorPontuacao+"                                    \n"
                "                   -1. Exit\n"
                "=========================================================="
    }

    def static updateMelhorPontuacao(){
        if (melhorPontuacao<ponto) melhorPontuacao=ponto
    }

    def static getQuestion(){
        if (repositoryQuestion.isEmpty()){
            updateMelhorPontuacao()
            finalizado = true
            return "O JOGO TERMINOU \n" +
                    "SUA PONTUACAO: "+ponto+"\n" +
                    "MELHOR PONTUACAO: "+melhorPontuacao+
                    "\n\n" +
                    "Pressione 0 para voltar ao menu"
        }else {
            question = repositoryQuestion.removeFirst()
            return question.question + "\n"+ "A. "+ question.answers.answers.get("A") +" \n" +
                    "B. "+question.answers.answers.get("B") + "\n"+ "C. "+ question.answers.answers.get("C") + "\n"+
                    "D. "+ question.answers.answers.get("D")
        }
    }
    def static VerifyAnswer(String response){
        if (!finalizado){
            if (response.equals(question.rightResponse)) {
                if (gameMode == GameMode.FACIL){
                    ponto +=2
                    return "\nVoce recebeu 2 pontos \n" +
                            "SUA PONTUACAO: " + ponto+"\n" +
                            "MELHOR PONTUACAO: "+ melhorPontuacao
                }
                if (gameMode == GameMode.MEDIO){
                    ponto +=5
                    return "\nVoce recebeu 5 pontos \n" +
                            "SUA PONTUACAO: "+ponto+"\n" +
                            "MELHOR PONTUACAO: "+melhorPontuacao
                }
                if (gameMode == GameMode.DIFICIL){
                    ponto +=10
                    return "\nVoce recebeu 10 pontos \n" +
                            "SUA PONTUACAO: "+ponto+"\n" +
                            "MELHOR PONTUACAO: "+melhorPontuacao
                }
            } else return "A resposta esta errada"
        } else {
            getQuestion()
        }
    }
}
