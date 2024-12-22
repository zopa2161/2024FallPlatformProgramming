import java.text.SimpleDateFormat;
import java.util.Scanner;

public class CustomerMode {
    public java.sql.Date date;
    public CustomerMode(java.sql.Date date) {
        this.date = date;
    }

    public void ShowMenu()throws Exception{
        System.out.println("일반 사용자 메뉴\n" +
                "1. 사용 가능한 방 확인\n" +
                "2. 예약현황 확인\n"+
                "3. 체크아웃 시 예상 금액\n");

        Scanner sc = new Scanner(System.in);
        switch(sc.nextInt()){
            case 1:
                break;
            case 2:

                break;
            case 3:
                break;

        }
    }

    private void FindRoomsForCustomer() throws Exception{
        System.out.println("예약일을 선택해 주세요" +
                "yyyyMMdd 형식으로 입력해주세요" +
                "체크인 날짜 : ");
        Scanner sc = new Scanner(System.in);
        String inDay = sc.nextLine();
        SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");

        System.out.println("체크아웃 날짜 : ");
        String outDay = sc.nextLine();

        java.sql.Date inDate = new java.sql.Date(simple.parse(inDay).getTime());
        java.sql.Date outDate = new java.sql.Date(simple.parse(outDay).getTime());


    }


}
