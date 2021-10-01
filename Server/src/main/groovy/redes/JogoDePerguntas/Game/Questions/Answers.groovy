package redes.JogoDePerguntas.Game.Questions

class Answers {

    HashMap<String, String> answers = new HashMap<>();

    Answers(){
        answers = new HashMap<>();

    }
    def add(String option, String answer){
        answers.put(option,answer)
    }
}
