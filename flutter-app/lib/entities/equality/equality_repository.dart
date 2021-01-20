import 'package:amcCarePlanner/entities/equality/equality_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class EqualityRepository {
    EqualityRepository();
  
  static final String uriEndpoint = '/equalities';

  Future<List<Equality>> getAllEqualities() async {
    final allEqualitiesRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<Equality>>(allEqualitiesRequest.body);
  }

  Future<Equality> getEquality(int id) async {
    final equalityRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<Equality>(equalityRequest.body);
  }

  Future<Equality> create(Equality equality) async {
    final equalityRequest = await HttpUtils.postRequest('$uriEndpoint', equality);
    return JsonMapper.deserialize<Equality>(equalityRequest.body);
  }

  Future<Equality> update(Equality equality) async {
    final equalityRequest = await HttpUtils.putRequest('$uriEndpoint', equality);
    return JsonMapper.deserialize<Equality>(equalityRequest.body);
  }

  Future<void> delete(int id) async {
    final equalityRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
