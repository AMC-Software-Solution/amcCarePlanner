import 'package:amcCarePlanner/entities/system_events_history/system_events_history_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class SystemEventsHistoryRepository {
    SystemEventsHistoryRepository();
  
  static final String uriEndpoint = '/system-events-histories';

  Future<List<SystemEventsHistory>> getAllSystemEventsHistories() async {
    final allSystemEventsHistoriesRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<SystemEventsHistory>>(allSystemEventsHistoriesRequest.body);
  }

  Future<SystemEventsHistory> getSystemEventsHistory(int id) async {
    final systemEventsHistoryRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<SystemEventsHistory>(systemEventsHistoryRequest.body);
  }

  Future<SystemEventsHistory> create(SystemEventsHistory systemEventsHistory) async {
    final systemEventsHistoryRequest = await HttpUtils.postRequest('$uriEndpoint', systemEventsHistory);
    return JsonMapper.deserialize<SystemEventsHistory>(systemEventsHistoryRequest.body);
  }

  Future<SystemEventsHistory> update(SystemEventsHistory systemEventsHistory) async {
    final systemEventsHistoryRequest = await HttpUtils.putRequest('$uriEndpoint', systemEventsHistory);
    return JsonMapper.deserialize<SystemEventsHistory>(systemEventsHistoryRequest.body);
  }

  Future<void> delete(int id) async {
    final systemEventsHistoryRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
