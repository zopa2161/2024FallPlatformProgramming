import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("오늘의 날짜를 입력해 주세요");
        String today = sc.nextLine();
        SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");

        java.sql.Date inDate= null;
        try{
            inDate = new java.sql.Date(simple.parse(today).getTime());
        }catch(Exception e){
            e.printStackTrace();
        }

        while(true){
            System.out.println("사용자 유형을 선택해 주세요\n" +
                    "1. 관리자 모드\n" +
                    "2. 고객 모드");
            int mode = sc.nextInt();
            switch(mode){

            }
        }
    }

}
