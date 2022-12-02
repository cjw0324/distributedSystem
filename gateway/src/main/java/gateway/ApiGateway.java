package gateway;

import gateway.request.RequestDto;
import gateway.response.ResponseDto;
import java.util.Scanner;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class ApiGateway {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final RestTemplate rt = new RestTemplate();
    private static final int GET = 1;
    private static final int POST = 2;
    private static final int PUT = 3;
    private static final int DELETE = 4;

    public static void main(String[] args) {
        while (true) {
            System.out.println("연결할 서버를 입력해주세요. (students, employees) (종료는 quit)");
            final String server = SCANNER.nextLine();

            if (server.equals("quit")) {
                break;
            }

            System.out.println("1. 조회, 2. 생성, 3. 수정, 4. 삭제");
            final int option = SCANNER.nextInt();
            SCANNER.nextLine();

            if (option == GET) {
                getRequest(server);
            } else if (option == POST) {
                postRequest(server);
            } else if (option == PUT) {
                putRequest(server);
            } else if (option == DELETE) {
                deleteRequest(server);
            } else {
                System.out.println("입력을 확인해주세요.");
            }
        }
    }

    private static void deleteRequest(final String server) {
        System.out.println("삭제할 id를 입력해주세요.");
        final long id = SCANNER.nextLong();
        SCANNER.nextLine();

        try {
            apiDeleteRequest(server, id);
            System.out.println("삭제 되었습니다.");
        } catch (RuntimeException e) {
            System.out.println("요청을 처리하지 못하였습니다.");
        }
    }

    private static void putRequest(final String server) {
        System.out.println("변경할 id를 입력해주세요.");
        final long id = SCANNER.nextLong();
        SCANNER.nextLine();

        System.out.println("이메일을 입력해주세요.");
        final String email = SCANNER.nextLine();

        System.out.println("이름을 입력해주세요.");
        final String name = SCANNER.nextLine();

        final RequestDto request = new RequestDto(email, name);

        try {
            apiPutRequest(server, id, request);
            System.out.println("수정 되었습니다.");
        } catch (RuntimeException e) {
            System.out.println("요청을 처리하지 못하였습니다.");
        }
    }

    private static void postRequest(final String server) {
        System.out.println("이메일을 입력해주세요.");
        final String email = SCANNER.nextLine();

        System.out.println("이름을 입력해주세요.");
        final String name = SCANNER.nextLine();

        final RequestDto request = new RequestDto(email, name);

        try {
            final ResponseEntity<ResponseDto> response = apiPostRequest(server, request);
            System.out.println(response.getBody());
        } catch (RuntimeException e) {
            System.out.println("요청을 처리하지 못하였습니다.");
        }
    }

    private static void getRequest(final String server) {
        System.out.println("조회할 id를 입력해주세요.");
        final long id = SCANNER.nextLong();
        SCANNER.nextLine();

        try {
            final ResponseEntity<String> response = apiGetRequest(server, id);
            System.out.println(response.getBody());
        } catch (RuntimeException e) {
            System.out.println("요청을 처리하지 못하였습니다.");
        }
    }

    private static ResponseEntity<String> apiGetRequest(final String server, final Long id) {
        if (server.equals("students")) {
            return rt.exchange(
                    "http://localhost:8081/api/" + server + "/" + id,
                    HttpMethod.GET, null, String.class
            );
        }
        if (server.equals("employees")) {
            return rt.exchange(
                    "http://localhost:8082/api/" + server + "/" + id,
                    HttpMethod.GET, null, String.class
            );
        }
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }

    private static ResponseEntity<ResponseDto> apiPostRequest(final String server, final RequestDto request) {
        if (server.equals("students")) {
            return rt.postForEntity(
                    "http://localhost:8081/api/" + server, request, ResponseDto.class
            );
        }
        if (server.equals("employees")) {
            return rt.postForEntity(
                    "http://localhost:8082/api/" + server, request, ResponseDto.class
            );
        }
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }

    private static void apiPutRequest(final String server, final Long id, final RequestDto request) {
        if (server.equals("students")) {
            rt.put("http://localhost:8081/api/" + server + "/" + id, request);
        }
        if (server.equals("employees")) {
            rt.put("http://localhost:8082/api/" + server + "/" + id, request);
        }
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }

    private static void apiDeleteRequest(final String server, final long id) {
        if (server.equals("students")) {
            rt.delete("http://localhost:8081/api/" + server + "/" + id);
        }
        if (server.equals("employees")) {
            rt.delete("http://localhost:8082/api/" + server + "/" + id);
        }
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }
}
