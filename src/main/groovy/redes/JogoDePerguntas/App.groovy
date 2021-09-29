package redes.jumakowski

class App {

    @Lazy
    private static Properties properties
    
    public static void main(String[] args) {
        new Object() {}
            .getClass()
            .getResource( "/application.properties" )
            .withInputStream {
                properties.load(it)
            }

        // CPU.run(args)
    }


}