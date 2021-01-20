import 'package:amcCarePlanner/entities/employee_holiday/employee_holiday_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class EmployeeHolidayRepository {
    EmployeeHolidayRepository();
  
  static final String uriEndpoint = '/employee-holidays';

  Future<List<EmployeeHoliday>> getAllEmployeeHolidays() async {
    final allEmployeeHolidaysRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<EmployeeHoliday>>(allEmployeeHolidaysRequest.body);
  }

  Future<EmployeeHoliday> getEmployeeHoliday(int id) async {
    final employeeHolidayRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<EmployeeHoliday>(employeeHolidayRequest.body);
  }

  Future<EmployeeHoliday> create(EmployeeHoliday employeeHoliday) async {
    final employeeHolidayRequest = await HttpUtils.postRequest('$uriEndpoint', employeeHoliday);
    return JsonMapper.deserialize<EmployeeHoliday>(employeeHolidayRequest.body);
  }

  Future<EmployeeHoliday> update(EmployeeHoliday employeeHoliday) async {
    final employeeHolidayRequest = await HttpUtils.putRequest('$uriEndpoint', employeeHoliday);
    return JsonMapper.deserialize<EmployeeHoliday>(employeeHolidayRequest.body);
  }

  Future<void> delete(int id) async {
    final employeeHolidayRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
