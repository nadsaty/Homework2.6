package com.nadsatyyy.nadsatycollections.service.Impl;

import com.nadsatyyy.nadsatycollections.exception.EmployeeAlreadyAddedException;
import com.nadsatyyy.nadsatycollections.exception.EmployeeNotFoundExcerption;
import com.nadsatyyy.nadsatycollections.exception.EmployeeStorageIsFullException;
import com.nadsatyyy.nadsatycollections.model.Employee;
import com.nadsatyyy.nadsatycollections.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service

public class EmployeeServiceImpl implements EmployeeService {

    private final List<Employee> employees = new ArrayList<>();
    private final int STORAGE_SIZE = 5;

    @Override
    public Employee add(String firstName, String lastName) {
        if (employees.size() >= STORAGE_SIZE) {
            throw new EmployeeStorageIsFullException("Хранилище сотрудников заполнено!");
        }

        Employee employee = new Employee(firstName, lastName);
        if (employees.contains(employee)) {
            throw new EmployeeAlreadyAddedException("Сотрудник " + firstName + " " + lastName + " уже добавлен в хранилище!");
        }

        employees.add(employee);
        return employee;
    }

    @Override
    public Employee remove(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        if (!employees.contains(employee)) {
            throw new EmployeeNotFoundExcerption("Сотрудник " + firstName + " " + lastName + " не найден!");
        }
        employees.remove(employee);
        return employee;
    }

    @Override
    public Employee find(String firstName, String lastName) {
        Employee requestedEmployee = new Employee(firstName, lastName);
        for (Employee employee : employees) {
            if (employee.equals(requestedEmployee)) {
                return requestedEmployee;
            }
        }
        throw new EmployeeNotFoundExcerption("Сотрудник " + firstName + " " + lastName + " не найден!");
    }

    @Override
    public List<Employee> getAll() {
        return new ArrayList<>(employees);
    }
}
