package com.varukha.webproject.command.impl.base;

import com.varukha.webproject.command.PagePath;
import com.varukha.webproject.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


class DefaultCommandTest {

    @Test
    void testDefaultCommandExecute() {
        // Create a mock HttpServletRequest and HttpServletResponse
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // Create a new instance of the DefaultCommand
        DefaultCommand defaultCommand = new DefaultCommand();

        // Call the execute method and get the returned Router object
        Router router = defaultCommand.execute(request, response);

        // Verify that the Router object is not null
        assertNotNull(router);

        // Verify that the Router object's page path is set to the main page
        assertEquals(PagePath.MAIN_PAGE, router.getPagePath());

    }
}
