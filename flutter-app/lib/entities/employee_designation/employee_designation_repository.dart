import 'package:amcCarePlanner/entities/employee_designation/employee_designation_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class EmployeeDesignationRepository {
    EmployeeDesignationRepository();
  
  static final String uriEndpoint = '/employee-designations';

  Future<List<EmployeeDesignation>> getAllEmployeeDesignations() async {
    final allEmployeeDesignationsRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<EmployeeDesignation>>(allEmployeeDesignationsRequest.body);
  }

  Future<EmployeeDesignation> getEmployeeDesignation(int id) async {
    final employeeDesignationRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<EmployeeDesignation>(employeeDesignationRequest.body);
  }

  Future<EmployeeDesignation> create(EmployeeDesignation employeeDesignation) async {
    final employeeDesignationRequest = await HttpUtils.postRequest('$uriEndpoint', employeeDesignation);
    return JsonMapper.deserialize<EmployeeDesignation>(employeeDesignationRequest.body);
  }

  Future<EmployeeDesignation> update(EmployeeDesignation employeeDesignation) async {
    final employeeDesignationRequest = await HttpUtils.putRequest('$uriEndpoint', employeeDesignation);
    return JsonMapper.deserialize<EmployeeDesignation>(employeeDesignationRequest.body);
  }

  Future<void> delete(int id) async {
    final employeeDesignationRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
