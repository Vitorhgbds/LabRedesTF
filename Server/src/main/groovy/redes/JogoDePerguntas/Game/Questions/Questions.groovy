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
    public String toString(){
        return question + "\n"+ "A. "+ answers.answers.get("A") +" \n" +
                "B. "+answers.answers.get("B") + "\n"+ "C. "+ answers.answers.get("C")
    }
}
