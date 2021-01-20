import 'package:amcCarePlanner/entities/timesheet/timesheet_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class TimesheetRepository {
    TimesheetRepository();
  
  static final String uriEndpoint = '/timesheets';

  Future<List<Timesheet>> getAllTimesheets() async {
    final allTimesheetsRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<Timesheet>>(allTimesheetsRequest.body);
  }

  Future<Timesheet> getTimesheet(int id) async {
    final timesheetRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<Timesheet>(timesheetRequest.body);
  }

  Future<Timesheet> create(Timesheet timesheet) async {
    final timesheetRequest = await HttpUtils.postRequest('$uriEndpoint', timesheet);
    return JsonMapper.deserialize<Timesheet>(timesheetRequest.body);
  }

  Future<Timesheet> update(Timesheet timesheet) async {
    final timesheetRequest = await HttpUtils.putRequest('$uriEndpoint', timesheet);
    return JsonMapper.deserialize<Timesheet>(timesheetRequest.body);
  }

  Future<void> delete(int id) async {
    final timesheetRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
