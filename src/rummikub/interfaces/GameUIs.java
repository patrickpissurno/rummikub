package rummikub.interfaces;

import rummikub.Pedra;

/**
 * Interface que representa todos os botões públicos que o jogo oferece como forma de interface para os jogadores acionarem
 * suas ações. Principal utilidade é criar abstração que facilite implementar a IA
 */
public interface GameUIs {
    boolean passarAVezButtonPressed();
    boolean rummikubButtonPressed();
    boolean finalizarJogadaButtonPressed();
    boolean onMovimentoExecutado(Pedra pedra);
}
