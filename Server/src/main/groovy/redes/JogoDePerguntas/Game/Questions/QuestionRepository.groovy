package redes.JogoDePerguntas.Game.Questions

class QuestionRepository {

    static LinkedList<Questions> questionsFacil
    static LinkedList<Questions> questionsMedio
    static LinkedList<Questions> questionsDificil

    def static CreateQuestionsFacil(){
        questionsFacil = new LinkedList<>();
        Questions questions1 = new Questions("Em qual nivel de camada o protocolo TCP/IP trabalha?","D");
        Answers answers = new Answers()
        answers.add("A","Aplicacao")
        answers.add("B","Enlace")
        answers.add("C","Transporte")
        answers.add("D","Rede")

        questions1.answers = answers

        questionsFacil.add(questions1)

        Questions questions2 = new Questions("Qual a funcao de um roteador em uma rede de computadores?","C");
        Answers answers2 = new Answers()
        answers2.add("A","Ligar computadores em uma rede local ")
        answers2.add("B","Espalhar pacotes para todos hosts de uma LAN ")
        answers2.add("C","Interligar redes de computadores, com diferentes rotas ")
        answers2.add("D","Interagir diretamente com aplicacao de redes, tais como uma web browser")

        questions2.answers = answers2

        questionsFacil.add(questions2)

        Questions questions3 = new Questions("Das alternativas abaixo, qual a que lista tecnologias de enlace fisicos sem fio?","A");
        Answers answers3 = new Answers()
        answers3.add("A","IEEE 802.11 (WiFi)")
        answers3.add("B","Fibra optica ")
        answers3.add("C","IEEE 802.3 (par trancado)")
        answers3.add("D","IEEE 802.3 (coaxial)")

        questions3.answers = answers3

        questionsFacil.add(questions3)

        Questions questions4 = new Questions("Qual das opcões nao é uma camada do modelo OSI?","C");
        Answers answers4 = new Answers()
        answers4.add("A","Enlace de dados")
        answers4.add("B","Rede")
        answers4.add("C","Protocolo")
        answers4.add("D","Fisico")

        questions4.answers = answers4

        questionsFacil.add(questions4)

        Questions questions5 = new Questions("O que significa endereco MAC?","C");
        Answers answers5 = new Answers()
        answers5.add("A","Master Address Compatibility ")
        answers5.add("B","Media Assynchronous Connection ")
        answers5.add("C","Media Access Control ")
        answers5.add("D","Master Access Address ")

        questions5.answers = answers5

        questionsFacil.add(questions5)

        Questions questions6 = new Questions("Qual é a utilidade de um servidor DHCP? ","B");
        Answers answers6 = new Answers()
        answers6.add("A","É um protocolo para configuracao de roteadores.")
        answers6.add("B","Fornecer números IP automaticamente para novos membros da rede ")
        answers6.add("C","Monitorar a rede e informar ao root cada vez que um novo membro se conecta ")
        answers6.add("D","Compartilhar de forma dinâmica e equitativa os recursos da rede")

        questions6.answers = answers6

        questionsFacil.add(questions6)


        return questionsFacil
    }

    def static CreateQuestionsMedio(){
        questionsMedio = new LinkedList<>();
        Questions questions1 = new Questions("Sao mascaras padrao de redes, respectivamente de classe A, B e C: ","D");
        Answers answers = new Answers()
        answers.add("A","255.255.255.0, 255.255.0.0 255.0.0.0 ")
        answers.add("B","255.255.0.0, 255.0.0.0, 255.255.255.0 ")
        answers.add("C","0.0.0.0, 255.0.0.0, 255.255.0.0 ")
        answers.add("D","255.0.0.0, 255.255.0.0, 255.255.255.0 ")

        questions1.answers = answers

        questionsMedio.add(questions1)

        Questions questions2 = new Questions("O endereco IP (Ipv4) está dividido em: ","D");
        Answers answers2 = new Answers()
        answers2.add("A","Endereco de rede + codigo de acesso")
        answers2.add("B","Host + rede")
        answers2.add("C","Rede + host + número de hosts")
        answers2.add("D","Rede + host")

        questions2.answers = answers2

        questionsMedio.add(questions2)

        Questions questions3 = new Questions("Qual alternativa indica corretamente o nome do equipamento de rede capaz de realizar uma conexao entre diferentes redes de modo a permitir a troca de informacões entre elas, mas que seja capaz tambem de controlar o fluxo da informacao, possibilitando, por exemplo, a criacao de rotas mais curtas e rapidas? ","C");
        Answers answers3 = new Answers()
        answers3.add("A","Modem")
        answers3.add("B","Repetidor ")
        answers3.add("C","Roteador")
        answers3.add("D","Bridges")

        questions3.answers = answers3

        questionsMedio.add(questions3)

        Questions questions4 = new Questions("Sobre os elementos de interconexao que compõem uma rede, escolha a alternativa correta.","B");
        Answers answers4 = new Answers()
        answers4.add("A","Hubs e switches têm o mesmo principio de funcionamento, diferenciando-se apenas na quantidade de portas, que e maior em switches.")
        answers4.add("B","Um switch e capaz de atuar como bridge.")
        answers4.add("C","A principal funcao do roteador e determinar o melhor caminho para encaminhar quadros Ethernet entre a origem e o destino.")
        answers4.add("D","Hubs e repetidores sao equipamentos de camadas diferentes.")

        questions4.answers = answers4

        questionsMedio.add(questions4)

        Questions questions5 = new Questions("Estabelecer a distincao entre roteadores e switches se faz necessario para o correto funcionamento da rede. Portanto, e correto afirmar que esses dois equipamentos citados no texto trabalham, respectivamente, em que camadas do modelo TCP/IP?","D");
        Answers answers5 = new Answers()
        answers5.add("A","Fisica e Enlace. ")
        answers5.add("B","Aplicacao e Transporte. ")
        answers5.add("C","Fisica e Transporte.")
        answers5.add("D","Rede e Enlace. ")

        questions5.answers = answers5

        questionsMedio.add(questions5)

        Questions questions6 = new Questions("Analise as seguintes afirmativas e diga quais estao corretas:\n" +
                "I – O repetidor e um dispositivo da camada de transporte. Ele recebe, amplifica (regenera) e retransmite sinais em ambos os sentidos. \n" +
                "II – Os hubs sao dispositivos da camada fisica e, normalmente, nao amplificam os sinais de entrada. I\n" +
                "II – Os switches e as pontes (bridges) sao dispositivos da camada de enlace e o roteamento desses dispositivos e baseado em enderecos de quadro. \n","B");
        Answers answers6 = new Answers()
        answers6.add("A","I, II e III.")
        answers6.add("B","II e III, apenas.")
        answers6.add("C","I e II, apenas.")
        answers6.add("D","I e III, apenas.")

        questions6.answers = answers6

        questionsMedio.add(questions6)


        return questionsMedio
    }

    def static CreateQuestionsDificil(){
        questionsDificil = new LinkedList<>();
        Questions questions1 = new Questions("No TCP/IP, o endereco IP 172.20.35.36 enquadra-se na classe: ","B");
        Answers answers = new Answers()
        answers.add("A","A")
        answers.add("B","B")
        answers.add("C","C")
        answers.add("D","D")

        questions1.answers = answers

        questionsDificil.add(questions1)

        Questions questions2 = new Questions("Nao e um servico provido pelos servidores DNS: ","D");
        Answers answers2 = new Answers()
        answers2.add("A","realizar a distribuicao de carga entre servidores Web replicados; ")
        answers2.add("B","traduzir nomes de hospedeiros da Internet para o endereco IP e subjacente; ")
        answers2.add("C","obter o nome canônico de um servidor de correio a partir de um apelido correspondente; ")
        answers2.add("D","transferir arquivos entre hospedeiros da Internet e estacões-clientes.")

        questions2.answers = answers2

        questionsDificil.add(questions2)

        Questions questions3 = new Questions("(FCC/CEAL/2005) Na arquitetura TCP/IP:","C");
        Answers answers3 = new Answers()
        answers3.add("A","o IP 127.0.0.1 e utilizado para representar maquinas de toda a rede; ")
        answers3.add("B","o IP 10.0.0.1 enquadra-se no padrao classe B; ")
        answers3.add("C","a mascara de rede FFFFFF00 e tipica do padrao classe C; ")
        answers3.add("D","o servico UDP e orientado à conexao; ")

        questions3.answers = answers3

        questionsDificil.add(questions3)

        Questions questions4 = new Questions("Acerca das topologias de rede de computadores, assinale a opcao correta.","A");
        Answers answers4 = new Answers()
        answers4.add("A","A topologia em estrela e uma evolucao da topologia em barramento: possui um aparelho concentrador (hub ou switch) que interconecta todos os cabos que vêm dos computadores (nos) e pode, ainda, interconectar outras redes facilmente, sendo a topologia mais utilizada para redes locais.")
        answers4.add("B","Em todos os tipos de topologias para redes do tipo LAN, so se permite atingir taxas de ate 10 Mbps.")
        answers4.add("C","Na topologia em barramento, quando um no da rede esta danificado, a comunicacao continua a ser efetuada na rede normalmente.")
        answers4.add("D","As topologias das redes estao fundamentadas nas tecnologias LAN, MAN e WAN, como as topologias em anel, as em estrela e as em barramento, com grande predominância da topologia em anel.")

        questions4.answers = answers4

        questionsDificil.add(questions4)

        Questions questions5 = new Questions("(UFMT - 2021) Em uma rede TCP/IP, o endereco IPV4 tem 32 bits, que, separados em conjuntos de 8 bits, formam os octetos. No entanto, a notacao binaria nao e amigavel para o ser humano e utiliza a notacao dos octetos convertidos para decimal separados por ponto. A configuracao TCP/IP utiliza um endereco IP, uma mascara de rede e um gateway padrao. Os enderecos de rede sao definidos por classe de rede e identificam um host na rede pelo uso da mascara de rede. Dado o endereco IP em binario 1100.0000 1010.1000 0000 1111 0111 0000, assinale a alternativa que identifica a classe de rede, a mascara, a rede e o host em decimal.","D");
        Answers answers5 = new Answers()
        answers5.add("A","Classe de rede C, mascara 255.255.255.0, rede 192.168.16 e host 112.")
        answers5.add("B","Classe de rede A, mascara 255.0.0.0, rede 192.168.15 e host 112.")
        answers5.add("C","Classe de rede B, mascara 255.255.0.0, rede 192.168.15 e host 112.")
        answers5.add("D","Classe de rede C, mascara 255.255.255.0, rede 192.168.15 e host 112.")

        questions5.answers = answers5

        questionsDificil.add(questions5)

        Questions questions6 = new Questions("(FCC/2008) A configuracao de rede mais adequada para conectar computadores de – um pavimento – um estado – uma nacao e, respectivamente: ","A");
        Answers answers6 = new Answers()
        answers6.add("A","a)\tLAN, WAN, WAN; ")
        answers6.add("B","b)\tLAN, LAN, WAN; ")
        answers6.add("C","c)\tLAN, LAN, LAN; ")
        answers6.add("D","d)\tWAN, WAN, LAN; ")

        questions6.answers = answers6

        questionsDificil.add(questions6)


        return questionsDificil
    }

}
