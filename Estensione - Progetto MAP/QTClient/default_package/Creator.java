package default_package;

public class Creator {

    private final String nome;
    private final String github;

    public Creator(String nome, String github){
        this.nome = nome;
        this.github = github;
    }

    public String getNome(){ return this.nome; }

    public String getGithub(){ return this.github; }

}
