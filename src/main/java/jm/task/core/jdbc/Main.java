package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Катя", "Андреева", (byte) 20);
        userService.saveUser("Павел", "Сидоров", (byte) 22);
        userService.saveUser("Олег", "Петров", (byte) 54);
        userService.saveUser("Рита", "Иванова", (byte) 35);

        userService.removeUserById(1);
        userService.getAllUsers();
        //userService.cleanUsersTable();
        //userService.dropUsersTable();
        // реализуйте алгоритм здесь
    }
}
