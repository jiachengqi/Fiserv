package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FiservApplicationTests {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    RestService restService;

    @Autowired
    MyRunner myRunner;

    @Test
    public void testRun() {
        assertThat(applicationContext).isNotNull();
        assertThat(myRunner).isNotNull();
        assertThat(restService).isNotNull();
    }

    @Test
    public void it_should_return_right_response_for_invalid_input() {
        String invalidInput = "eqweqweqweqw";
        String validInput = "pizza";

        assertThat(myRunner.processInput(invalidInput)).isEqualTo("Invalid input! It should be an active topic on Wikipedia");
        assertThat(myRunner.processInput(validInput)).contains("The occurrence of the word");
    }

    @Test
    public void it_should_count_right_occurrences_with_case_sensitive() {
        String testString1 = "pizza pizz pizzzzza Pizza";
        String testString2 = "mw-collapsible-content hlist\\\">\\n<ul><li><a href=\\\"/wiki/History_of_pizza\\\"";
        String testString3 = "Bootstrapping Spring Data JPA repositories in DEFAULT mode";
        String testString4 = "pizza and pizza margherita";
        String targetString = "pizza";

        assertThat(restService.count(testString1, targetString)).isEqualTo(1);
        assertThat(restService.count(testString2, targetString)).isEqualTo(1);
        assertThat(restService.count(testString3, targetString)).isEqualTo(0);
        assertThat(restService.count(testString4, targetString)).isEqualTo(2);
    }

    @Test
    public void it_should_return_not_empty_string_base_on_input() {
        String input = "pizza";

        assertThat(restService.getPostsAsString(input)).isNotEmpty();
    }

    @Test
    public void it_should_be_case_sensitive_base_on_input() {
        String input = "pizza";
        String inputUpperCase = "Pizza";

        assertThat(restService.getPostsAsString(input)).isNotEqualTo(restService.getPostsAsString(inputUpperCase));
    }

    @Test
    public void input_with_white_spaces_will_be_trimmed() {
        String input = "  pizza   ";

        assertThat(restService.getPostsAsString(input)).isNotEmpty();
    }

    @Test
    public void it_should_return_null_for_invalid_input() {
        String invalidInput = "dasfsdfasdf";

        assertThat(restService.getPostsAsString(invalidInput)).isNull();
    }
}
