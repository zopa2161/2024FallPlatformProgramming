import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class ManagerMode {

    public java.sql.Date today;
    public ManagerMode(java.sql.Date day) {
        today = day;
    }

    public void ShowMenu() throws Exception {
        System.out.println("관리자 메뉴 선택");
        System.out.println("관리자메뉴 선택\n" +
                "1.현재 사용중인 방 확인\n" +
                "2.하우스 키핑 나열\n" +
                "3.체크인\n" +
                "4.체크아웃\n" +
                "5.서비스 확인\n");
        Scanner sc = new Scanner(System.in);
        switch (sc.nextInt()) {
            case 1:
                Services.ShowCurrentService();

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:
                break;




        }
    }


}
