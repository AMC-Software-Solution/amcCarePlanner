import 'package:amcCarePlanner/entities/disability/disability_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class DisabilityRepository {
    DisabilityRepository();
  
  static final String uriEndpoint = '/disabilities';

  Future<List<Disability>> getAllDisabilities() async {
    final allDisabilitiesRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<Disability>>(allDisabilitiesRequest.body);
  }

  Future<Disability> getDisability(int id) async {
    final disabilityRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<Disability>(disabilityRequest.body);
  }

  Future<Disability> create(Disability disability) async {
    final disabilityRequest = await HttpUtils.postRequest('$uriEndpoint', disability);
    return JsonMapper.deserialize<Disability>(disabilityRequest.body);
  }

  Future<Disability> update(Disability disability) async {
    final disabilityRequest = await HttpUtils.putRequest('$uriEndpoint', disability);
    return JsonMapper.deserialize<Disability>(disabilityRequest.body);
  }

  Future<void> delete(int id) async {
    final disabilityRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
