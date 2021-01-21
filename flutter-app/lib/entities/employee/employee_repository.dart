import 'package:amcCarePlanner/entities/employee/employee_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class EmployeeRepository {
    EmployeeRepository();

  static final String uriEndpoint = '/v1';

  Future<List<Employee>> getAllEmployees() async {
    final allEmployeesRequest = await HttpUtils.getRequest(uriEndpoint + '/get-all-employees-by-client-id');
    String data = allEmployeesRequest.body;
    print(data);
    return JsonMapper.deserialize<List<Employee>>(data);
  }

  Future<Employee> getEmployee(int id) async {
    final employeeRequest = await HttpUtils.getRequest('$uriEndpoint/get-employee-by-client-id/$id');
    return JsonMapper.deserialize<Employee>(employeeRequest.body);
  }

  Future<Employee> create(Employee employee) async {
    final employeeRequest = await HttpUtils.postRequest('$uriEndpoint', employee);
    return JsonMapper.deserialize<Employee>(employeeRequest.body);
  }

  Future<Employee> update(Employee employee) async {
    final employeeRequest = await HttpUtils.putRequest('$uriEndpoint', employee);
    return JsonMapper.deserialize<Employee>(employeeRequest.body);
  }

  Future<void> delete(int id) async {
    final employeeRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
