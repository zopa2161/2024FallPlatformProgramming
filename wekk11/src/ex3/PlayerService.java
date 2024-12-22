package ex3;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class PlayerService {
    private static final int DEFAULT_PAGE_SIZE = 3;
    private int nextId = 1;
    private final FileRepository fileRepository;

    public PlayerService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
        this.nextId = calculateNextId();
    }

    public void addPlayer(Player player) {
        player.setSofifaId(generateNextId());
        fileRepository.addPlayer(player);
    }

    public boolean deletePlayer(int playerId) {
        return fileRepository.deletePlayer(playerId);
    }

    public Player getPlayer(int playerId) {
        return fileRepository.getPlayer(playerId);
    }

    public List<Player> getAllPlayers(int page, int pageSize, String sortBy) {
        List<Player> players = new ArrayList<>(fileRepository.getAllPlayers());

        // Sort players
        if ("overall".equalsIgnoreCase(sortBy)) {
            players.sort(Comparator.comparingInt(Player::getOverall).reversed());
        } else {
            players.sort(Comparator.comparingInt(Player::getSofifaId));
        }

        // Pagination logic
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, players.size());

        if (fromIndex > players.size()) {
            return Collections.emptyList(); // Return an empty list if the page is out of range
        }
        return players.subList(fromIndex, toIndex);
    }

    private int generateNextId() {
        return nextId++;
    }

    private int calculateNextId() {
        return fileRepository.getAllPlayers().stream()
                .mapToInt(Player::getSofifaId)
                .max()
                .orElse(0) + 1;
    }
}
