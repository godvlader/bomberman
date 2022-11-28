package bomberman.model;

public enum GameStatus {
    GAME_NOT_LAUNCH("Partie pas lancée"),
    GAME_IN_PROGRESS("En cours"),
    PLAYER_WON("Le joueur a gagné"),
    PLAYER_LOSE("Le joueur a perdu");

    public String statusString;

     GameStatus(String statusString){
        this.statusString = statusString;
    }

}
