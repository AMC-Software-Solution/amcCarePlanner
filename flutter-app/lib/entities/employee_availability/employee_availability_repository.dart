import 'package:amcCarePlanner/entities/employee_availability/employee_availability_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class EmployeeAvailabilityRepository {
    EmployeeAvailabilityRepository();
  
  static final String uriEndpoint = '/employee-availabilities';

  Future<List<EmployeeAvailability>> getAllEmployeeAvailabilities() async {
    final allEmployeeAvailabilitiesRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<EmployeeAvailability>>(allEmployeeAvailabilitiesRequest.body);
  }

  Future<EmployeeAvailability> getEmployeeAvailability(int id) async {
    final employeeAvailabilityRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<EmployeeAvailability>(employeeAvailabilityRequest.body);
  }

  Future<EmployeeAvailability> create(EmployeeAvailability employeeAvailability) async {
    final employeeAvailabilityRequest = await HttpUtils.postRequest('$uriEndpoint', employeeAvailability);
    return JsonMapper.deserialize<EmployeeAvailability>(employeeAvailabilityRequest.body);
  }

  Future<EmployeeAvailability> update(EmployeeAvailability employeeAvailability) async {
    final employeeAvailabilityRequest = await HttpUtils.putRequest('$uriEndpoint', employeeAvailability);
    return JsonMapper.deserialize<EmployeeAvailability>(employeeAvailabilityRequest.body);
  }

  Future<void> delete(int id) async {
    final employeeAvailabilityRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
