package ex3;

public class Player {
    private int sofifaId;
    private String shortName;
    private int heightCm;
    private String nationality;
    private String club;
    private int overall;
    private String[] playerPositions;

    public Player(String shortName, int heightCm, String nationality, String club, int overall, String[] playerPositions) {
        this.shortName = shortName;
        this.heightCm = heightCm;
        this.nationality = nationality;
        this.club = club;
        this.overall = overall;
        this.playerPositions = playerPositions;
    }

    public int getSofifaId() {
        return sofifaId;
    }

    public void setSofifaId(int sofifaId) {
        this.sofifaId = sofifaId;
    }

    public int getOverall() {
        return overall;
    }

    @Override
    public String toString() {
        return sofifaId + "," + shortName + "," + heightCm + "," + nationality + "," + club + "," + overall + "," + String.join(",", playerPositions);
    }

    public static Player fromString(String playerData) {
        String[] data = playerData.split(",");
        // Skip if it's the header
        if (data[0].equalsIgnoreCase("sofifa_id")) { 
            return null;
        }
        int sofifaId = Integer.parseInt(data[0]);
        String shortName = data[1];
        int heightCm = Integer.parseInt(data[2]);
        String nationality = data[3];
        String club = data[4];
        int overall = Integer.parseInt(data[5]);
        String[] playerPositions = data[6].replace("\"", "").split(",");

        Player player = new Player(shortName, heightCm, nationality, club, overall, playerPositions);
        // Set ID from file data
        player.setSofifaId(sofifaId); 
        return player;
    }
}


