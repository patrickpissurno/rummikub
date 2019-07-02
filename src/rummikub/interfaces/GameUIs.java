package rummikub.interfaces;

import rummikub.Pedra;

/**
 * Interface que representa todos os botões públicos que o jogo oferece como forma de interface para os jogadores acionarem
 * suas ações. Principal utilidade é criar abstração que facilite implementar a IA
 */
public interface GameUIs {
    void passarAVezButtonPressed();
    void rummikubButtonPressed();
    void finalizarJogadaButtonPressed();
    void onMovimentoExecutado(Pedra pedra);
}
