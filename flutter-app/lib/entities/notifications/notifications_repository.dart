import 'package:amcCarePlanner/entities/notifications/notifications_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class NotificationsRepository {
    NotificationsRepository();
  
  static final String uriEndpoint = '/notifications';

  Future<List<Notifications>> getAllNotifications() async {
    final allNotificationsRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<Notifications>>(allNotificationsRequest.body);
  }

  Future<Notifications> getNotifications(int id) async {
    final notificationsRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<Notifications>(notificationsRequest.body);
  }

  Future<Notifications> create(Notifications notifications) async {
    final notificationsRequest = await HttpUtils.postRequest('$uriEndpoint', notifications);
    return JsonMapper.deserialize<Notifications>(notificationsRequest.body);
  }

  Future<Notifications> update(Notifications notifications) async {
    final notificationsRequest = await HttpUtils.putRequest('$uriEndpoint', notifications);
    return JsonMapper.deserialize<Notifications>(notificationsRequest.body);
  }

  Future<void> delete(int id) async {
    final notificationsRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
