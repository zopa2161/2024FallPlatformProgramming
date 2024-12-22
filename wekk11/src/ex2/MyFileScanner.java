package ex2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyFileScanner {
    private String csvFilePath;
    private String jsonFilePath;
    private CSVToJsonConverter converter;


    public MyFileScanner(String csvFilePath, String jsonFilePath, CSVToJsonConverter converter) {
        this.csvFilePath = csvFilePath;
        this.jsonFilePath = jsonFilePath;
        this.converter = converter;
    }

    public void process() {

        //먼저 head를 따야한다.
        BufferedReader br = null;
        List<String> header = new ArrayList<>();
        List<String> lines = new ArrayList<>();
        try{
            br = new BufferedReader(new FileReader(csvFilePath));

            String l;
            l = br.readLine();
            Scanner sc = new Scanner(l);
            sc.useDelimiter(",");
            while(sc.hasNext()) {
                header.add(sc.next());

            }
            while((l=br.readLine())!=null){
                lines.add(l);
            }// 요까지 하면 헤더랑 라인들 완성
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String out = converter.convertToJson(lines, header);
        //Implement your code
        writeJsonToFile(out);
    }

    private void writeJsonToFile(String jsonOutput) {
        //Implement your code
        try{
            FileOutputStream fos = new FileOutputStream(jsonFilePath);
            fos.write(jsonOutput.getBytes());
        }
        catch(FileNotFoundException e) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}