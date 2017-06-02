package ua.travel.command.utils;

import ua.travel.command.*;
import ua.travel.command.page.ErrorCommand;
import ua.travel.command.page.HomeCommand;
import ua.travel.command.page.TourCommand;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuuto on 5/29/17.
 */
public class CommandHelper {

    private static CommandHelper commandHelper;
    private Map<String, PageCommand> pageCommands;
    private Map<String, ExecuteCommand> executeCommands;

    public static CommandHelper getInstance() {
        CommandHelper localInstance = commandHelper;
        if (localInstance == null) {
            synchronized (CommandHelper.class) {
                localInstance = commandHelper;
                if (localInstance == null) {
                    commandHelper = localInstance = new CommandHelper();
                }
            }
        }
        return localInstance;
    }

    private CommandHelper() {
        pageCommands = new HashMap<>();
        pageCommands.put("/login", new AuthCommand());
        pageCommands.put("/register", new RegisterCommand());
        pageCommands.put("/", new HomeCommand());
        pageCommands.put("/error", new ErrorCommand());
        pageCommands.put("/tours", new TourCommand());
        pageCommands.put("/order", new OrderCommand());

        executeCommands = new HashMap<>();
        executeCommands.put("login", new AuthCommand());
        executeCommands.put("register", new RegisterCommand());
    }

    public PageCommand getPageCommand(HttpServletRequest request) {
        String url = PathUtils.getContextPath(request);
        PageCommand commandInstance = pageCommands.get(url);
        if (commandInstance == null) {
            return new DefaultCommand(url);
        }
        return commandInstance;
    }

    public ExecuteCommand getExecuteCommand(HttpServletRequest request) {
        String command = request.getParameter("command");
        ExecuteCommand commandInstance = executeCommands.get(command);
        if (commandInstance == null) {
            return new DefaultCommand(command);
        }
        return commandInstance;
    }
}
