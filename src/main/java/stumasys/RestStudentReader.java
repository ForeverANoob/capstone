/*
package stumasys;

import org.springframework.batch.item.ItemReader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

class RestStudentReader implements ItemReader<Courses> {


    private final String apiUrl;
    private final RestTemplate restTemplate;

    private int nextStudentIndex;
    private List<Courses> CoursesData;

    RestStudentReader(String apiUrl, RestTemplate restTemplate) {
        this.apiUrl = apiUrl;
        this.restTemplate = restTemplate;
        nextStudentIndex = 0;
    }

    @Override
    public Courses read() throws Exception {
        if (studentDataIsNotInitialized()) {
            CoursesData = fetchStudentDataFromAPI();
        }

        StudentDTO nextStudent = null;

        if (nextStudentIndex < CoursesData.size()) {
            nextStudent = CoursesData.get(nextStudentIndex);
            nextStudentIndex++;
        }

        return nextStudent;
    }

    private boolean studentDataIsNotInitialized() {
        return this.CoursesData == null;
    }

    private List<CoursesData> fetchStudentDataFromAPI() {
        ResponseEntity<Courses[]> response = restTemplate.getForEntity(
            apiUrl,
            Courses[].class
        );
        StudentDTO[] CoursesData = response.getBody();
        return Arrays.asList(CoursesData);
    }
}
*/
