package fr.hoc.dap.server;

/**
 * Configuration des dossiers de stockage. Application ZeroConf
 */
public class Config {

    //TODO brs by Djer |JavaDoc| "default xxxxx" serait plus claire pour "justifier" les constantes.
    /** Nom de l'appli. */
    private static final String APPLICATION_NAME = "Hoc Dap";

    /** dossier du token. for use authentication permission. */
    /**
     * Dossier dans lequel les autorisations accordées par l'utilisateur seront
     * sauvegardées.
     */
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /** dossier credential. path to the json file. */
    private static final String CREDENTIALS_FILE_PATH = System.getProperty("user home") + "/dap/credential_web_Json";

    //TODO brs by Djer |JavaDoc| Non cela n'initialise pas (c'est le role du construcuteur", il s'agit du nom de l'application (même que commentaire que la constante sans le "defaut ")
    /** Initialize variable viApplicationName. */
    private String applicationName;
    /** Initialize variable tokendirectorypath. */
    private String clientSecretFile;
    /** Initialize variable credentialfilepath. */
    private String credentialFolder;

    /** Initialize les attributs. */
    public Config() {
        applicationName = APPLICATION_NAME;
        credentialFolder = TOKENS_DIRECTORY_PATH;
        clientSecretFile = CREDENTIALS_FILE_PATH;
    }

    // **************** ACCESSEURS
    /** @return the applicationName */
    public String getApplicationName() {
        return applicationName;
    }

    /** @return the tokendirectorypath */
    public String getClientSecretFile() {
        return clientSecretFile;
    }

    /** @return the credentialFolder */
    public String getCredentialFolder() {
        return credentialFolder;
    }

    // **************** MUTATEURS
    /**
     * @param appName the viApplicationName to set / appName in the actual config
     */
    public void setApplicationName(final String appName) {
        this.applicationName = appName;
    }

    /**
     * @param cliFile the viClientSecretFile to set / cliFile in the actual config
     */
    public void setClientSecretFile(final String cliFile) {
        this.clientSecretFile = cliFile;
    }

    /**
     * @param crefolder the viCredentialFolder to set / cliFile in the actual config
     */
    public void setCredentialFolder(final String crefolder) {
        this.credentialFolder = crefolder;
    }

    public String getoAuth2CallbackUrl() {
      //TODO brs by Djer |Design Patern| Devrait être ne mode "Zero Conf" comme les autres
        return "/oAuth2Callback";
    }

}
