/**
 * Representa um console de videogame com funcionalidades básicas
 * como ligar, desligar, inserir e iniciar jogos.
 */
public class Console {
    private String nome;
    private String fabricante;
    private int anoFrabicacao;
    private boolean ligado = false;
    private String jogoAtual;

    /**
     * Constrói um console com os dados informados.
     *
     * @param nome           Nome do console
     * @param fabricante     Fabricante do console
     * @param anoFrabicacao  Ano de fabricação do console
     */
    public Console(String nome, String fabricante, int anoFrabicacao) {
        this.nome = nome;
        this.fabricante = fabricante;
        this.anoFrabicacao = anoFrabicacao;
    }

    /**
     * Liga o console, caso esteja desligado.
     * Exibe uma mensagem informando o estado.
     */
    public void ligar() {
        if (!isLigado()) {
            setLigado(true);
            System.out.println(getNome() + " está ligado.");
        } else {
            System.out.println(nome + " já está ligado.");
        }
    }

    /**
     * Desliga o console, caso esteja ligado.
     * Remove o jogo atual e exibe uma mensagem informando o estado.
     */
    public void desligar() {
        if (isLigado()) {
            setLigado(false);
            setJogoAtual(null);
            System.out.println(getNome() + " foi desligado.");
        } else {
            System.out.println(getNome() + " já está desligado.");
        }
    }

    /**
     * Insere um jogo no console, caso esteja ligado.
     *
     * @param jogo Nome do jogo a ser inserido
     */
    public void inserirJogo(String jogo) {
        if (isLigado()) {
            setJogoAtual(jogo);
            System.out.println("Jogo \"" + getJogoAtual() + "\" inserido.");
        } else {
            System.out.println("Não é possível inserir jogo. O console está desligado.");
        }
    }

    /**
     * Inicia o jogo atual, caso exista e o console esteja ligado.
     * Exibe mensagens apropriadas se o console estiver desligado
     * ou se não houver jogo inserido.
     */
    public void iniciarJogo() {
        if (isLigado() && getJogoAtual() != null) {
            System.out.println("Iniciando o jogo \"" + getJogoAtual() + "\"...");
        } else if (!isLigado()) {
            System.out.println("O console está desligado.");
        } else {
            System.out.println("Nenhum jogo inserido.");
        }
    }

    /**
     * Remove o jogo atual do console, caso exista.
     * Exibe uma mensagem informando o resultado.
     */
    public void removerJogo() {
        if (getJogoAtual() != null) {
            System.out.println("Jogo \"" + getJogoAtual() + "\" removido.");
            setJogoAtual(null);
        } else {
            System.out.println("Nenhum jogo para remover.");
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public int getAnoFrabicacao() {
        return anoFrabicacao;
    }

    public void setAnoFrabicacao(int anoFrabicacao) {
        this.anoFrabicacao = anoFrabicacao;
    }

    public boolean isLigado() {
        return ligado;
    }

    public void setLigado(boolean ligado) {
        this.ligado = ligado;
    }

    public String getJogoAtual() {
        return jogoAtual;
    }

    public void setJogoAtual(String jogoAtual) {
        this.jogoAtual = jogoAtual;
    }


}
