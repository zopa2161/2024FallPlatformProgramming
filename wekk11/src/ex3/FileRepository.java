package ex3;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileRepository implements IRepository {
    private final String fileName = "players.csv";
    private final Map<Integer, Player> players = new HashMap<>();//id, 선수?
    private String header;
    private int nextId;

    public FileRepository() {
        load();
        // Set next ID based on existing data
        nextId = calculateNextId(); 
    }

    private void load() {
        // Implement your coce
        //여기서 csv파일을 읽어서 맵에 데이터 등록하기
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(fileName));

            header =br.readLine();
            String l;
            while((l=br.readLine())!=null){
                Player temp = Player.fromString(l);
                players.put(temp.getSofifaId(),temp);
            }

        }
        catch (FileNotFoundException e) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void save() {
        // Implement your coce
        //filename 을 싹 지우고, 새로 쓰기
        try{
            BufferedWriter bf = new BufferedWriter(new FileWriter(fileName));

            bf.write(header);
            for(Player p : players.values()) {
                bf.write(p.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int calculateNextId() {
        int maxId = 0;
        for (Player player : players.values()) {
            if (player.getSofifaId() > maxId) {
                maxId = player.getSofifaId();
            }
        }
        return maxId + 1;
    }
    
    @Override
    public void addPlayer(Player player) {
        // Implement your coce
        players.put(player.getSofifaId(), player);
    }

    @Override
    public boolean deletePlayer(int playerId) {
        // Implement your coce
        if(players.containsKey(playerId)){ players.remove(playerId); return true; }
        else { return false; }
    }

    @Override
    public Player getPlayer(int playerId) {
        // Implement your coce
        return players.get(playerId);
    }

    @Override
    public List<Player> getAllPlayers() {
        // Implement your coce
        return new ArrayList<>(players.values());
    }
}

