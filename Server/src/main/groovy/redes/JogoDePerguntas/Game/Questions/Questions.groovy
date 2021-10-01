package redes.JogoDePerguntas.Game.Questions

class Questions {

    def rightResponse
    def question
    Answers answers

    Questions(String Question, String response){
        question = Question
        answers = new HashMap<>()
        rightResponse = response
    }

}
