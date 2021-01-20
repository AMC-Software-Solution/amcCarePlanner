import 'package:amcCarePlanner/entities/access/access_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class AccessRepository {
    AccessRepository();
  
  static final String uriEndpoint = '/accesses';

  Future<List<Access>> getAllAccesses() async {
    final allAccessesRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<Access>>(allAccessesRequest.body);
  }

  Future<Access> getAccess(int id) async {
    final accessRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<Access>(accessRequest.body);
  }

  Future<Access> create(Access access) async {
    final accessRequest = await HttpUtils.postRequest('$uriEndpoint', access);
    return JsonMapper.deserialize<Access>(accessRequest.body);
  }

  Future<Access> update(Access access) async {
    final accessRequest = await HttpUtils.putRequest('$uriEndpoint', access);
    return JsonMapper.deserialize<Access>(accessRequest.body);
  }

  Future<void> delete(int id) async {
    final accessRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
