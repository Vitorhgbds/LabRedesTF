package redes.JogoDePerguntas.Game.Questions

class Answers {

    /*
        As alternativas são compostas por uma opçao e um enunciado
    */

    HashMap<String, String> answers = new HashMap<>();

    Answers(){
        answers = new HashMap<>();
    }


    def add(String option, String answer){
        answers.put(option,answer)
    }
}
