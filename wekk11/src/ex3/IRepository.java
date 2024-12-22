package ex3;

import java.util.List;

public interface IRepository {
    void addPlayer(Player player);
    boolean deletePlayer(int playerId);
    Player getPlayer(int playerId);
    List<Player> getAllPlayers();
}