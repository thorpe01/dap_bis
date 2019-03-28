/**
 *
 */
package fr.hoc.dap.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author house
 *
 */
@SpringBootApplication
public class Application {
    /**
     * @param args .
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * @return conf = envoie la conf au container.
     */
    @Bean
    public Config createConf() {
        Config conf = new Config();
        conf.setClientSecretFile("/credentials_web.json");

        return conf;
    }

    /**
     * @see
     * @return .
     * @param ctx . // @Bean // public CommandLineRunner commandLineRunner(final
     *            ApplicationContext ctx) { // return args -> {
     * 
     *            // /** Instance of Config
     */
    // Config maConf = new Config();
    // maConf.setApplicationName("Appli Mail et Calendrier");
    // LOG.info("Configuration créée");
    //
    // System.out.println("Bienvenue sur : " + maConf.getApplicationName());
    // System.out.println("ma Config = " + "repertoire client secret file : " +
    // maConf.getClientSecretFile()
    // + " et repertoire du jeton : " + maConf.getCredentialFolder());
    //
    //
    // GMailService mesmails;
    // mesmails.setLaConf(maConf); // donne à GMailService accès à la Conf
    // mesmails.unreadMail("me", "is:unread");
    //
    //
    // CalendarService moncalendrier = CalendarService.getInstance();
    // moncalendrier.setLaConf(maConf); // donne à CalendarService accès à la Conf
    // moncalendrier.nextEvents();
    // };

    // }

}
