import 'package:amcCarePlanner/entities/employee_location/employee_location_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class EmployeeLocationRepository {
    EmployeeLocationRepository();
  
  static final String uriEndpoint = '/employee-locations';

  Future<List<EmployeeLocation>> getAllEmployeeLocations() async {
    final allEmployeeLocationsRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<EmployeeLocation>>(allEmployeeLocationsRequest.body);
  }

  Future<EmployeeLocation> getEmployeeLocation(int id) async {
    final employeeLocationRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<EmployeeLocation>(employeeLocationRequest.body);
  }

  Future<EmployeeLocation> create(EmployeeLocation employeeLocation) async {
    final employeeLocationRequest = await HttpUtils.postRequest('$uriEndpoint', employeeLocation);
    return JsonMapper.deserialize<EmployeeLocation>(employeeLocationRequest.body);
  }

  Future<EmployeeLocation> update(EmployeeLocation employeeLocation) async {
    final employeeLocationRequest = await HttpUtils.putRequest('$uriEndpoint', employeeLocation);
    return JsonMapper.deserialize<EmployeeLocation>(employeeLocationRequest.body);
  }

  Future<void> delete(int id) async {
    final employeeLocationRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
