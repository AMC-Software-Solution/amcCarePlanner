import 'package:amcCarePlanner/entities/service_user/service_user_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class ServiceUserRepository {
    ServiceUserRepository();
  
  static final String uriEndpoint = '/service-users';

  Future<List<ServiceUser>> getAllServiceUsers() async {
    final allServiceUsersRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<ServiceUser>>(allServiceUsersRequest.body);
  }

  Future<ServiceUser> getServiceUser(int id) async {
    final serviceUserRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<ServiceUser>(serviceUserRequest.body);
  }

  Future<ServiceUser> create(ServiceUser serviceUser) async {
    final serviceUserRequest = await HttpUtils.postRequest('$uriEndpoint', serviceUser);
    return JsonMapper.deserialize<ServiceUser>(serviceUserRequest.body);
  }

  Future<ServiceUser> update(ServiceUser serviceUser) async {
    final serviceUserRequest = await HttpUtils.putRequest('$uriEndpoint', serviceUser);
    return JsonMapper.deserialize<ServiceUser>(serviceUserRequest.body);
  }

  Future<void> delete(int id) async {
    final serviceUserRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
