package redes.JogoDePerguntas.Game.Questions

class Questions {

    def rightResponse
    def question
    Answers answers

    /*
        Uma questão é composta pela questão, a alternativa correta e uma lista com as alternativas
    */

    Questions(String Question, String response){
        question = Question
        answers = new HashMap<>()
        rightResponse = response
    }

}
