import 'package:amcCarePlanner/entities/service_user_event/service_user_event_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class ServiceUserEventRepository {
    ServiceUserEventRepository();
  
  static final String uriEndpoint = '/service-user-events';

  Future<List<ServiceUserEvent>> getAllServiceUserEvents() async {
    final allServiceUserEventsRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<ServiceUserEvent>>(allServiceUserEventsRequest.body);
  }

  Future<ServiceUserEvent> getServiceUserEvent(int id) async {
    final serviceUserEventRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<ServiceUserEvent>(serviceUserEventRequest.body);
  }

  Future<ServiceUserEvent> create(ServiceUserEvent serviceUserEvent) async {
    final serviceUserEventRequest = await HttpUtils.postRequest('$uriEndpoint', serviceUserEvent);
    return JsonMapper.deserialize<ServiceUserEvent>(serviceUserEventRequest.body);
  }

  Future<ServiceUserEvent> update(ServiceUserEvent serviceUserEvent) async {
    final serviceUserEventRequest = await HttpUtils.putRequest('$uriEndpoint', serviceUserEvent);
    return JsonMapper.deserialize<ServiceUserEvent>(serviceUserEventRequest.body);
  }

  Future<void> delete(int id) async {
    final serviceUserEventRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
